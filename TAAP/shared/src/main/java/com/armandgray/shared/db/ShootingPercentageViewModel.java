package com.armandgray.shared.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.armandgray.shared.model.PerformanceRate;

public class ShootingPercentageViewModel extends ViewModel {

    private final ShootingPerformanceRepository repository = new ShootingPerformanceRepository();

    public LiveData<PerformanceRate> getCurrentRate() {
        return repository.getCurrentRate();
    }

    public void addMake() {
        repository.addMake();
    }

    public void addMiss() {
        repository.addMiss();
    }
}
