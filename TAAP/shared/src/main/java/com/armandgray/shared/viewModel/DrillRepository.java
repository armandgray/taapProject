package com.armandgray.shared.viewModel;

import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.PerformanceRate;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

@Singleton
class DrillRepository {

    private final MutableLiveData<Drill> activeDrill = new MutableLiveData<>();
    private final MutableLiveData<PerformanceRate> performance = new MutableLiveData<>();
    private final MutableLiveData<PerformanceRate> completion = new MutableLiveData<>();

    @SuppressWarnings("ConstantConditions")
    @Inject
    DrillRepository() {
        Drill defaultDrill = Drill.Defaults.getDefault();
        this.activeDrill.setValue(defaultDrill);
        this.performance.setValue(defaultDrill.getPerformance());
    }

    LiveData<Drill> getActiveDrill() {
        return activeDrill;
    }

    LiveData<PerformanceRate> getPerformance() {
        return performance;
    }

    LiveData<PerformanceRate> getCompletionObserver() {
        return completion;
    }

    @SuppressWarnings("ConstantConditions")
    void addMake() {
        PerformanceRate rate = performance.getValue();
        rate.raiseCount();
        rate.raiseTotal();
        performance.setValue(rate);
        handleSetCompletion(rate);
    }

    @SuppressWarnings("ConstantConditions")
    void addMiss() {
        PerformanceRate rate = performance.getValue();
        rate.raiseTotal();
        performance.setValue(rate);
        handleSetCompletion(rate);
    }

    private void handleSetCompletion(PerformanceRate rate) {
        if (rate.getTotal() != rate.getMax()) {
            return;
        }

        completion.setValue(new PerformanceRate(rate));
        rate.clear();
        performance.setValue(rate);
    }

    void setActiveDrill(Drill drill) {
        activeDrill.setValue(drill);
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }
}
