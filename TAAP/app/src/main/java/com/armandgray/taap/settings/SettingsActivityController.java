package com.armandgray.taap.settings;

import android.content.Intent;
import android.net.Uri;

class SettingsActivityController implements SettingsActivityViews.SettingsViewsListener {

    static final String ARMANDGRAY_COM = "http://armandgray.com";

    SettingsActivity activity;
    SettingsActivityViews views;

    SettingsActivityController(SettingsActivity activity) {
        this.activity = activity;
        this.views = new SettingsActivityViews(activity, this);

        views.setupActivityInitialState();
    }

    @Override
    public void onTvSeeMoreClick() {
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ARMANDGRAY_COM)));
    }
}
