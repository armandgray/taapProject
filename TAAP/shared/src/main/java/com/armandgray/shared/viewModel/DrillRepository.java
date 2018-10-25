package com.armandgray.shared.viewModel;

import android.util.Log;

import com.armandgray.shared.application.TAAPRepository;
import com.armandgray.shared.db.DatabaseManager;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;
import com.armandgray.shared.model.UXPreference;
import com.armandgray.shared.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

@Singleton
class DrillRepository extends TAAPRepository {

    private static int singleSetTimeout = UXPreference.Item.TIMEOUT.getDefault(true);

    @VisibleForTesting
    final BehaviorSubject<List<Drill>> drillsSubject = BehaviorSubject.create();

    @VisibleForTesting
    final BehaviorSubject<Drill> activeDrillSubject = BehaviorSubject.create();

    @VisibleForTesting
    final BehaviorSubject<Performance> performanceSubject = BehaviorSubject.create();

    @VisibleForTesting
    final PublishSubject<Performance> completionSubject = PublishSubject.create();

    @VisibleForTesting
    final PublishSubject<UXPreference> preferenceSubject = PublishSubject.create();

    private final DatabaseManager databaseManager;
    private final SchedulerProvider schedulers;

    @Nullable
    private Disposable setTimeoutDisposable;

    @SuppressWarnings("ConstantConditions")
    @Inject
    DrillRepository(PreferencesRepository preferencesRepository,
                    DatabaseManager databaseManager, SchedulerProvider schedulers) {
        this.databaseManager = databaseManager;
        this.schedulers = schedulers;

        disposables.add(preferencesRepository.getPreferenceUpdateObservable()
                .subscribe(this::preferenceConsumer));

        this.databaseManager.stateSubject
                .switchMap(toDrillList())
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(onDrillsRetrieved());

        // TODO Move call out of Drill Repository
        // Call used for side-effect: Triggers Database Creation/Opening
        Drill drill = Drill.Defaults.getDefault();
        this.databaseManager.getDrillDao().drill(drill.getId()).onErrorReturnItem(drill).subscribe(new SingleObserver<Drill>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onSuccess(Drill drill) {
                Log.d(TAG, "Database Force Open Call Success");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Database Force Open Call Failed");
            }
        });;
    }

    private void preferenceConsumer(UXPreference preference) {
        switch (preference.getCategory()) {
            case WORKOUT:
                preferenceSubject.onNext(preference);

                singleSetTimeout = preference.getValue(UXPreference.Item.TIMEOUT, true);
                return;

            default:
                onDrillPreferenceChanged(preference);
        }
    }

    private void onDrillPreferenceChanged(UXPreference preference) {
        if (!preference.getCategory().isDrillCategory() || activeDrillSubject.getValue() == null) {
            return;
        }

        Performance performance = performanceSubject.getValue();
        Drill drill = activeDrillSubject.getValue();
        Performance update = new Performance(drill);

        databaseManager.getDrillDao().update(drill).subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onSuccess(Integer integer) {
                Log.d(TAG, "Drill Update Success: " + drill);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Drill Update Failed: " + drill);
            }
        });

        if (performance != null) {
            update.setCount(performance.getCount());
            update.setTotal(performance.getTotal());
            update.setStartTime(performance.getStartTime());
        }

        setPerformanceValue(update);
    }

    private Function<DatabaseManager.State, ObservableSource<List<Drill>>> toDrillList() {
        return state -> state == DatabaseManager.State.READY
                ? databaseManager.getDrillDao().all().toObservable()
                : Observable.just(new ArrayList<Drill>());
    }

    private RepositoryObserver<List<Drill>> onDrillsRetrieved() {
        return new RepositoryObserver<List<Drill>>() {

            @Override
            public void onNext(List<Drill> list) {
                if (list.size() == 0) {
                    Log.d(TAG, "Drill Population: Retrieved Empty List (Check State)");
                    return;
                }

                updateDrillSubscribers(list);
            }
        };
    }

    @VisibleForTesting
    void updateDrillSubscribers(List<Drill> list) {
        @SuppressWarnings("ConstantConditions")
        Drill drill = list.stream().filter(Drill.Defaults.getDefault()::equals).findFirst().get();
        drillsSubject.onNext(list);
        activeDrillSubject.onNext(drill);
        performanceSubject.onNext(new Performance(drill));
    }

    Observable<List<Drill>> getDrillsObservable() {
        return drillsSubject;
    }

    Observable<Drill> getActiveDrillObservable() {
        return activeDrillSubject;
    }

    Observable<Performance> getPerformanceObservable() {
        return performanceSubject;
    }

    Observable<Performance> getCompletionObservable() {
        return completionSubject;
    }

    Observable<UXPreference> getPreferenceObservable() {
        return preferenceSubject;
    }

    void addMake() {
        Performance performance = this.performanceSubject.getValue();
        if (performance == null) {
            return;
        }

        performance.raiseCount();
        performance.raiseTotal();

        updateSetTimeout();
        setPerformanceValue(performance);
    }

    void addMiss() {
        Performance performance = this.performanceSubject.getValue();
        if (performance == null) {
            return;
        }

        performance.raiseTotal();

        updateSetTimeout();
        setPerformanceValue(performance);
    }

    private void updateSetTimeout() {
        if (setTimeoutDisposable != null) {
            setTimeoutDisposable.dispose();
        }

        setTimeoutDisposable = Observable.timer(singleSetTimeout, TimeUnit.MILLISECONDS)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(timeout -> handleSetCompletion(performanceSubject.getValue()));
    }

    void clearPerformance() {
        Performance performance = this.performanceSubject.getValue();
        if (performance == null) {
            return;
        }

        performance.clear();
        setPerformanceValue(performance);
    }

    private void setPerformanceValue(Performance performance) {
        performanceSubject.onNext(performance);
        if (performance.getTotal() >= performance.getReps()) {
            handleSetCompletion(performance);
        }
    }

    void setActiveDrill(@NonNull Drill drill) {
        activeDrillSubject.onNext(drill);
        if (performanceSubject.getValue() == null) {
            performanceSubject.onNext(new Performance(drill));
        } else {
            handleSetCompletion(performanceSubject.getValue());
        }

    }

    private void handleSetCompletion(Performance performance) {
        //noinspection ConstantConditions
        performanceSubject.onNext(new Performance(activeDrillSubject.getValue()));
        if (performance.getTotal() > 0) {
            performance.captureEndTime();
            completionSubject.onNext(performance);
            storePerformance(performance);
        }
    }

    private void storePerformance(Performance performance) {
        databaseManager.getPerformanceDao().insert(performance).subscribe(new SingleObserver<List<Long>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onSuccess(List<Long> rowIds) {
                Log.d(TAG, "Performance Insertion Success: " + performance);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Performance Insertion Failed: " + performance);
            }
        });;
    }
}
