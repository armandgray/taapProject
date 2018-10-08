package com.armandgray.shared.viewModel;

import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class DrillViewModel extends ViewModel {

    @Inject
    DrillRepository repository;

    DrillViewModel() {
        TAAPApplication.getAppComponent().inject(this);
    }

    public LiveData<List<Drill>> getDrills() {
        return repository.getDrills();
    }

    public LiveData<Drill> getActiveDrill() {
        return repository.getActiveDrill();
    }

    public LiveData<Performance> getPerformance() {
        return repository.getPerformance();
    }

    public LiveData<Performance> getCompletionObserver() {
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
