package com.armandgray.shared.viewModel;

import com.armandgray.shared.application.TAAPApplication;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

public class LogsViewModel extends ViewModel {

    @Inject
    DrillRepository repository;

    LogsViewModel() {
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
