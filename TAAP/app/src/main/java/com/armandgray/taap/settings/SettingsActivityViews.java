package com.armandgray.taap.settings;

import com.armandgray.taap.R;

class SettingsActivityViews {

    SettingsActivity activity;

    SettingsActivityViews(SettingsActivity activity) {
        this.activity = activity;
    }

    void setupActivityInitialState() {
        activity.setContentView(R.layout.activity_settings);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
}
