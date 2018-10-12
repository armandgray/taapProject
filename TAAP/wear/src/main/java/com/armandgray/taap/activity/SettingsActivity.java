package com.armandgray.taap.activity;

import android.os.Bundle;

import com.armandgray.shared.viewModel.SettingsViewModel;
import com.armandgray.taap.R;
import com.armandgray.taap.navigation.WearNavigationActivity;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjection;

public class SettingsActivity extends WearNavigationActivity {

    @Inject
    SettingsViewModel settingsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Dagger Injection
        AndroidInjection.inject(this);

        // Super
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_settings);
        super.onSetupContent();
    }

    @Override
    public void assignGlobalFields() {
        super.assignGlobalFields();
    }

    @Override
    public void setupVisualElements() {
        super.setupVisualElements();
    }

    @Override
    public void setupEventListeners() {
        super.setupEventListeners();
    }

    @Override
    public void setupViewModel() {
        super.setupViewModel();
    }

    @Module
    public static class ActivityModule
            extends WearNavigationActivity.NavigationModule<SettingsActivity> {

        @Provides
        @NonNull
        SettingsViewModel provideViewModel(SettingsActivity activity) {
            return ViewModelProviders.of(activity).get(SettingsViewModel.class);
        }
    }
}
