package com.armandgray.shared.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.model.PerformanceRate;

import javax.inject.Inject;

public class ShootingPercentageViewModel extends ViewModel {

    @Inject
    ShootingPerformanceRepository repository;

    ShootingPercentageViewModel() {
        TAAPApplication.getAppComponent().inject(this);
    }

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
