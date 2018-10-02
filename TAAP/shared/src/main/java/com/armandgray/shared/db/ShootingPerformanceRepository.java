package com.armandgray.shared.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.armandgray.shared.model.PerformanceRate;

class ShootingPerformanceRepository {

    private MutableLiveData<PerformanceRate> currentRate;

    ShootingPerformanceRepository() {
        this.currentRate = new MutableLiveData<>();
        this.currentRate.setValue(new PerformanceRate("Free Throws", 10)); // TODO Remove hardcoded things
    }

    LiveData<PerformanceRate> getCurrentRate() {
        return currentRate;
    }

    @SuppressWarnings("ConstantConditions")
    void addMake() {
        PerformanceRate value = currentRate.getValue();
        value.raiseCount();
        value.raiseTotal();
        currentRate.setValue(value);
    }

    @SuppressWarnings("ConstantConditions")
    void addMiss() {
        PerformanceRate value = currentRate.getValue();
        value.raiseTotal();
        currentRate.setValue(value);
    }
}
