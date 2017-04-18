package com.armandgray.taap.settings;

import android.support.v7.widget.Toolbar;

import com.armandgray.taap.R;

class SettingsActivityViews {

    static final String ARMANDGRAY_COM = "http://armandgray.com";

    SettingsActivity activity;

    SettingsActivityViews(SettingsActivity activity) {
        this.activity = activity;
    }

    void setupActivityInitialState() {
        activity.setContentView(R.layout.activity_settings);
        setupToolbar();
        setupToolbarHomebutton();
    }

    private void setupToolbarHomebutton() {
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
    }
}
