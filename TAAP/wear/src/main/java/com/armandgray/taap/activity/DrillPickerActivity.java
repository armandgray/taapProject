package com.armandgray.taap.activity;

import android.os.Bundle;

import com.armandgray.shared.application.TAAPActivity;
import com.armandgray.taap.R;

import dagger.Module;
import dagger.android.AndroidInjection;

public class DrillPickerActivity extends TAAPActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Dagger Injection
        AndroidInjection.inject(this);

        // Super
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_drill_picker);
        super.onSetupContent();
    }

    @Override
    protected void assignGlobalFields() {

    }

    @Override
    protected void setupVisualElements() {

    }

    @Override
    protected void setupEventListeners() {

    }

    @Override
    protected void setupViewModel() {

    }

    @Module
    public static class ActivityModule {

    }
}
