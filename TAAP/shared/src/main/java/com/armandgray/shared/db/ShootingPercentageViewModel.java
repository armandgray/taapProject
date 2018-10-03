package com.armandgray.shared.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.armandgray.shared.model.PerformanceRate;

public class ShootingPercentageViewModel extends ViewModel {

    @VisibleForTesting
    ShootingPerformanceRepository repository = new ShootingPerformanceRepository();

    public LiveData<PerformanceRate> getCurrentRate() {
        return repository.getCurrentRate();
    }

    public LiveData<PerformanceRate> getCompletionObserver() {
        return repository.getCompletionObserver();
    }

    public void addMake() {
        repository.addMake();
    }

    public void addMiss() {
        repository.addMiss();
    }

    @Override
    protected void onCleared() {
    }
}
