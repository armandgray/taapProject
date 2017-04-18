package com.armandgray.taap.settings;

class SettingsActivityController {

    SettingsActivity activity;
    SettingsActivityViews views;

    public SettingsActivityController(SettingsActivity activity) {
        this.activity = activity;
        this.views = new SettingsActivityViews(activity);
    }
}
