package com.armandgray.shared.viewModel;

import android.annotation.SuppressLint;

import com.armandgray.shared.db.DatabaseManagerImpl;
import com.armandgray.shared.db.DrillDatabase;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;
import com.armandgray.shared.model.UXPreference;

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

    @Inject
    DatabaseManagerImpl databaseManager;

    @Inject
    DrillDatabase database;

    @VisibleForTesting
    final MutableLiveData<Performance> performance = new MutableLiveData<>();

    @VisibleForTesting
    final MutableLiveData<Performance> completion = new MutableLiveData<>();

    @VisibleForTesting
    final MutableLiveData<Drill> activeDrill = new MutableLiveData<>();

    private final MutableLiveData<List<Drill>> drills = new MutableLiveData<>();

    @SuppressWarnings("ConstantConditions")
    @Inject
    DrillRepository(PreferencesRepository preferencesRepository) {
        preferencesRepository.addPreferenceConsumer(this::preferenceConsumer);

        List<Drill> list = Drill.Defaults.getDefaults();
        Drill drill = list.get(0);

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
