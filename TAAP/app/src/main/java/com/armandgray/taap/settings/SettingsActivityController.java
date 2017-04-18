package com.armandgray.taap.settings;

class SettingsActivityController {

    SettingsActivity activity;
    SettingsActivityViews views;

    SettingsActivityController(SettingsActivity activity) {
        this.activity = activity;
        this.views = new SettingsActivityViews(activity);

        views.setupActivityInitialState();
    }
}
