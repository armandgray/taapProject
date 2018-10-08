package com.armandgray.shared.application;

import androidx.appcompat.app.AppCompatActivity;

public abstract class TAAPActivity extends AppCompatActivity {

    protected void onSetupContent() {
        assignGlobalFields();
        setupVisualElements(false);
        setupEventListeners();
        setupViewModel();
    }

    protected abstract void assignGlobalFields();

    protected abstract void setupVisualElements(boolean showActionDrawer);

    protected abstract void setupEventListeners();

    protected abstract void setupViewModel();
}
