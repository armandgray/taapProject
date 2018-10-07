package com.armandgray.shared.viewModel;

import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.PerformanceRate;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class DrillViewModel extends ViewModel {

    @Inject
    DrillRepository repository;

    DrillViewModel() {
        TAAPApplication.getAppComponent().inject(this);
        System.out.println(this);
    }

    public LiveData<Drill> getDrill() {
        return repository.getActiveDrill();
    }

    public LiveData<PerformanceRate> getPerformance() {
        return repository.getPerformance();
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

    public void onDrillSelected(Drill drill) {
        repository.setActiveDrill(drill);
    }

    @Override
    protected void onCleared() {
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }
}
