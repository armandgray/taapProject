package com.armandgray.shared.viewModel;

import android.annotation.SuppressLint;
import android.util.Log;

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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

@Singleton
class DrillRepository {

    private static final String TAG = "DRILL_REPOSITORY";

    @VisibleForTesting
    final MutableLiveData<Performance> performance = new MutableLiveData<>();

    @VisibleForTesting
    final MutableLiveData<Performance> completion = new MutableLiveData<>();

    @VisibleForTesting
    final MutableLiveData<Drill> activeDrill = new MutableLiveData<>();

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<List<Drill>> drills = new MutableLiveData<>();
    private final DatabaseManager databaseManager;

    @SuppressWarnings("ConstantConditions")
    @Inject
    DrillRepository(PreferencesRepository preferencesRepository, DatabaseManager databaseManager,
                    SchedulerProvider schedulers) {
        preferencesRepository.addPreferenceConsumer(this::preferenceConsumer);

        this.databaseManager = databaseManager;
        this.databaseManager.stateSubject
                .switchMap(toDrillList())
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(onDrillListRetrieved());

        // TODO Move call out of Drill Repository
        // Call used for side-effect: Triggers Database Creation/Opening
        this.databaseManager.getDrillDao().drill(Drill.Defaults.getDefault().getId()).subscribe();
    }

    private Function<DatabaseManager.State, ObservableSource<List<Drill>>> toDrillList() {
        return state -> state != DatabaseManager.State.CREATED
                ? databaseManager.getDrillDao().all().toObservable()
                : Observable.just(new ArrayList<Drill>());
    }

    private Observer<List<Drill>> onDrillListRetrieved() {
        return new Observer<List<Drill>>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(List<Drill> list) {
                if (list.size() == 0) {
                    Log.e(TAG, "Drill Population: Retrieved Empty List (Check State)");
                    return;
                }

                updateDrillSubscribers(list);
                onComplete();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, String.format("Drill Population Failed: %s",
                        e.getMessage()));
            }

            @Override
            public void onComplete() {
                if (disposable != null) {
                    disposable.dispose();
                }
            }
        };
    }

    @VisibleForTesting
    void updateDrillSubscribers(List<Drill> list) {
        @SuppressWarnings("ConstantConditions")
        Drill drill = list.stream().filter(Drill.Defaults.getDefault()::equals).findFirst().get();
        drills.setValue(list);
        activeDrill.setValue(drill);
        performance.setValue(new Performance(drill));
    }

    private void preferenceConsumer(UXPreference preference) {
        if (!preference.getCategory().isDrillCategory() || getActiveDrill().getValue() == null) {
            return;
        }

        Performance performance = getPerformance().getValue();
        Performance update = new Performance(getActiveDrill().getValue());
        if (performance != null) {
            update.setCount(performance.getCount());
            update.setTotal(performance.getTotal());
            update.setStartTime(performance.getStartTime());
        }

        setPerformanceValue(update);
    }

    public LiveData<List<Drill>> getDrills() {
        return drills;
    }

    LiveData<Drill> getActiveDrill() {
        return activeDrill;
    }

    LiveData<Performance> getPerformance() {
        return performance;
    }

    LiveData<Performance> getCompletionObserver() {
        return completion;
    }

    void addMake() {
        Performance performance = this.performance.getValue();
        if (performance == null) {
            return;
        }

        performance.raiseCount();
        performance.raiseTotal();
        setPerformanceValue(performance);
    }

    void addMiss() {
        Performance performance = this.performance.getValue();
        if (performance == null) {
            return;
        }

        performance.raiseTotal();
        setPerformanceValue(performance);
    }

    private void setPerformanceValue(Performance performance) {
        this.performance.setValue(performance);
        if (performance.getTotal() >= performance.getReps()) {
            handleSetCompletion(performance);
        }
    }

    private void handleSetCompletion(Performance performance) {
        //noinspection ConstantConditions
        this.performance.setValue(new Performance(activeDrill.getValue()));
        if (performance.getTotal() > 0) {
            completion.setValue(performance);
            storePerformance(performance);
        }
    }

    void setActiveDrill(@NonNull Drill drill) {
        activeDrill.setValue(drill);
        if (this.performance.getValue() == null) {
            this.performance.setValue(new Performance(drill));
        } else {
            handleSetCompletion(this.performance.getValue());
        }

    }

    @SuppressLint("CheckResult")
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void storePerformance(Performance rate) {
        // TODO Implement
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }
}
