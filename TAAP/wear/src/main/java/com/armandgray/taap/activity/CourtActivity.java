package com.armandgray.taap.activity;

import android.os.Bundle;

import com.armandgray.taap.R;
import com.armandgray.taap.navigation.WearNavigationActivity;

import dagger.Module;
import dagger.android.AndroidInjection;

public class CourtActivity extends WearNavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Dagger Injection
        AndroidInjection.inject(this);

        // Super
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.court_picker);
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
            extends WearNavigationActivity.NavigationModule<CourtActivity> {
    }
}
