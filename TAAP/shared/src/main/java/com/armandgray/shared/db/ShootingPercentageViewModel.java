package com.armandgray.shared.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.model.PerformanceRate;

import javax.inject.Inject;

public class ShootingPercentageViewModel extends ViewModel {

    public static final int ACTION_TARGETS = 1000;
    public static final int ACTION_COURT = 1001;
    public static final int ACTION_LOGS = 1002;
    public static final int ACTION_SETTINGS = 1003;

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

    public void onAction(int actionId) {
        System.out.println(actionId);
    }

    @Override
    protected void onCleared() {
    }
}
