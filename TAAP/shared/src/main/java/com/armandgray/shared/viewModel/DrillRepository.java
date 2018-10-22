package com.armandgray.shared.viewModel;

import android.annotation.SuppressLint;
import android.util.Log;

import com.armandgray.shared.application.TAAPRepository;
import com.armandgray.shared.db.DatabaseManager;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;
import com.armandgray.shared.model.UXPreference;
import com.armandgray.shared.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

@Singleton
class DrillRepository extends TAAPRepository {

    private final DatabaseManager databaseManager;

    @VisibleForTesting
    final BehaviorSubject<List<Drill>> drillsSubject = BehaviorSubject.create();

    @VisibleForTesting
    final BehaviorSubject<Drill> activeDrillSubject = BehaviorSubject.create();

    @VisibleForTesting
    final BehaviorSubject<Performance> performanceSubject = BehaviorSubject.create();

    @VisibleForTesting
    final PublishSubject<Performance> completionSubject = PublishSubject.create();

    @SuppressWarnings("FieldCanBeLocal")
    private final CompositeDisposable disposables = new CompositeDisposable();

    @SuppressWarnings("ConstantConditions")
    @Inject
    DrillRepository(PreferencesRepository preferencesRepository,
                    DatabaseManager databaseManager, SchedulerProvider schedulers) {
        disposables.add(preferencesRepository.getPreferenceUpdateObservable()
                .subscribe(this::preferenceConsumer));

        this.databaseManager = databaseManager;
        this.databaseManager.stateSubject
                .switchMap(toDrillList())
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(onDrillListRetrieved());

        // TODO Move call out of Drill Repository
        // Call used for side-effect: Triggers Database Creation/Opening
        Drill drill = Drill.Defaults.getDefault();
        this.databaseManager.getDrillDao().drill(drill.getId()).onErrorReturnItem(drill).subscribe();
    }

    private void preferenceConsumer(UXPreference preference) {
        if (!preference.getCategory().isDrillCategory() || activeDrillSubject.getValue() == null) {
            return;
        }

        Performance performance = performanceSubject.getValue();
        Drill drill = activeDrillSubject.getValue();
        Performance update = new Performance(drill);

        databaseManager.getDrillDao().update(drill).subscribe();

        if (performance != null) {
            update.setCount(performance.getCount());
            update.setTotal(performance.getTotal());
            update.setStartTime(performance.getStartTime());
        }

        setPerformanceValue(update);
    }

    private Function<DatabaseManager.State, ObservableSource<List<Drill>>> toDrillList() {
        return state -> state != DatabaseManager.State.CREATED
                ? databaseManager.getDrillDao().all().toObservable()
                : Observable.just(new ArrayList<Drill>());
    }

    private RepositoryObserver<List<Drill>> onDrillListRetrieved() {
        return new RepositoryObserver<List<Drill>>() {

            @Override
            public void onNext(List<Drill> list) {
                if (list.size() == 0) {
                    Log.e(TAG, "Drill Population: Retrieved Empty List (Check State)");
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

    void addMake() {
        Performance performance = this.performanceSubject.getValue();
        if (performance == null) {
            return;
        }

        performance.raiseCount();
        performance.raiseTotal();
        setPerformanceValue(performance);
    }

    void addMiss() {
        Performance performance = this.performanceSubject.getValue();
        if (performance == null) {
            return;
        }

        performance.raiseTotal();
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
            performance.setEndTime(System.currentTimeMillis());
            completionSubject.onNext(performance);
            storePerformance(performance);
        }
    }

    @SuppressLint("CheckResult")
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void storePerformance(Performance performance) {
        System.out.println(performance.hashCode());
        databaseManager.getPerformanceDao().insert(performance).subscribe();
    }
}
