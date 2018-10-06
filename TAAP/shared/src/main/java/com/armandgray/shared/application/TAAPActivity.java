package com.armandgray.shared.application;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class TAAPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void onSetupContent() {
        assignGlobalFields();
        setupVisualElements();
        setupEventListeners();
        setupViewModel();
    }

    protected abstract void assignGlobalFields();

    protected abstract void setupVisualElements();

    protected abstract void setupEventListeners();

    protected abstract void setupViewModel();
}
