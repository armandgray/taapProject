package com.armandgray.shared.viewModel;

import android.annotation.SuppressLint;

import com.armandgray.shared.db.DatabaseManager;
import com.armandgray.shared.db.DrillDatabase;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

@Singleton
class DrillRepository {

    @VisibleForTesting
    final MutableLiveData<Performance> performance = new MutableLiveData<>();

    @VisibleForTesting
    final MutableLiveData<Performance> completion = new MutableLiveData<>();

    private final MutableLiveData<List<Drill>> drills = new MutableLiveData<>();
    private final MutableLiveData<Drill> activeDrill = new MutableLiveData<>();

    @Inject
    DatabaseManager databaseManager;

    @Inject
    DrillDatabase database;

    @SuppressWarnings("ConstantConditions")
    @Inject
    DrillRepository() {
        List<Drill> list = Drill.Defaults.getDefaults();
        drills.setValue(list);
        activeDrill.setValue(list.get(0));
        performance.setValue(list.get(0).getPerformance());
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
        this.performance.setValue(performance);
        if (performance.getTotal() == performance.getMax()) {
            handleSetCompletion(performance);
        }
    }

    void addMiss() {
        Performance performance = this.performance.getValue();
        if (performance == null) {
            return;
        }

        performance.raiseTotal();
        this.performance.setValue(performance);
        if (performance.getTotal() == performance.getMax()) {
            handleSetCompletion(performance);
        }
    }

    void setActiveDrill(Drill drill) {
        Performance performance = this.performance.getValue();
        if (performance == null) {
            return;
        }

        activeDrill.setValue(drill);
        handleSetCompletion(performance);
    }

    private void handleSetCompletion(Performance performance) {
        //noinspection ConstantConditions
        this.performance.setValue(new Performance(activeDrill.getValue().getId()));
        if (performance.getTotal() > 0) {
            completion.setValue(performance);
            storePerformance(performance);
        }
    }

    @SuppressLint("CheckResult")
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void storePerformance(Performance rate) {
        Observable.just(rate)
                .doOnNext(performance -> performance.setEndTime(System.currentTimeMillis()))
                .subscribeOn(Schedulers.io())
                .subscribe(database.performanceDao()::insertPerformance);
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }
}
