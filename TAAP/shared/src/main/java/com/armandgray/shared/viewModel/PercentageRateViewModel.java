package com.armandgray.shared.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.model.PerformanceRate;

import javax.inject.Inject;

public class PercentageRateViewModel extends ViewModel {

    @Inject
    PerformanceRateRepository repository;

    PercentageRateViewModel() {
        TAAPApplication.getAppComponent().inject(this);
    }

    public LiveData<PerformanceRate> getCurrentRate() {
        return repository.getCurrentRate();
    }

    public LiveData<PerformanceRate> getCompletionObserver() {
        return repository.getCompletionObserver();
    }

    public void onPlusClick() {
        repository.addMake();
    }

    public void onMinusClick() {
        repository.addMiss();
    }

    public void onSingleInputClick() {
        repository.addMake();
    }

    public void onDoubleInputClick() {
        repository.addMiss();
    }

    @Override
    protected void onCleared() {
    }
}
