package com.armandgray.shared.viewModel;

import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.model.UXPreference;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class PreferencesViewModel extends ViewModel {

    @Inject
    PreferencesRepository repository;

    PreferencesViewModel() {
        TAAPApplication.getAppComponent().inject(this);
    }

    public LiveData<UXPreference> getSelectedPreference() {
        return repository.getSelectedPreference();
    }

    public void setSelectedPreference(UXPreference preference) {
        repository.setSelectedPreference(preference);
    }

    public LiveData<UXPreference.Value> getSelectedValue() {
        return repository.getSelectedValue();
    }

    public void setSelectedValue(UXPreference.Value value) {
        repository.setSelectedValue(value);
    }

    public void onPreferenceUpdated() {
        repository.onPreferenceUpdated();
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
