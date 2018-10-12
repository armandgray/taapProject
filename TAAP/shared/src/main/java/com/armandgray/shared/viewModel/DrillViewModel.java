package com.armandgray.shared.viewModel;

import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.model.Drill;

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
