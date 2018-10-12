package com.armandgray.shared.viewModel;

import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.model.Drill;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    @Inject
    DrillRepository repository;

    SettingsViewModel() {
        TAAPApplication.getAppComponent().inject(this);
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
