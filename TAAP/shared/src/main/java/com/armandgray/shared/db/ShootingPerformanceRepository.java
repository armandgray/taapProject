package com.armandgray.shared.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.armandgray.shared.model.PerformanceRate;

class ShootingPerformanceRepository {

    private final MutableLiveData<PerformanceRate> currentRate  = new MutableLiveData<>();
    private final MutableLiveData<PerformanceRate> completion = new MutableLiveData<>();

    ShootingPerformanceRepository() {
        this.currentRate.setValue(new PerformanceRate("Free Throws", 10, .80f)); // TODO Remove hardcoded things
    }

    LiveData<PerformanceRate> getCurrentRate() {
        return currentRate;
    }

    @SuppressWarnings("ConstantConditions")
    void addMake() {
        PerformanceRate rate = currentRate.getValue();
        rate.raiseCount();
        rate.raiseTotal();
        currentRate.setValue(rate);
        handleSetCompletion(rate);
    }

    @SuppressWarnings("ConstantConditions")
    void addMiss() {
        PerformanceRate rate = currentRate.getValue();
        rate.raiseTotal();
        currentRate.setValue(rate);
        handleSetCompletion(rate);
    }

    private void handleSetCompletion(PerformanceRate rate) {
        if (rate.getTotal() != rate.getMax()) {
            return;
        }

        completion.setValue(rate);
        rate.clear();
        currentRate.setValue(rate);
    }

    LiveData<PerformanceRate> getCompletionObserver() {
        return completion;
    }
}
