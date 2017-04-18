package com.armandgray.taap.settings;

import android.content.Intent;
import android.net.Uri;

class SettingsActivityController implements SettingsActivityViews.SettingsViewsListener {

    static final String ARMANDGRAY_COM = "http://armandgray.com";
    // TODO change to Taap URL
    static final String GOOGLE_PLAY_STORE_TAAP = "https://play.google.com/store/apps/details?id=com.armandgray.seeme&hl=en";
    static final String COPYRIGHT = "COPYRIGHT";
    static final String SELECTED_ITEM = "SELECTED_ITEM";

    SettingsActivity activity;
    SettingsActivityViews views;

    SettingsActivityController(SettingsActivity activity) {
        this.activity = activity;
        this.views = new SettingsActivityViews(activity, this);

        views.setupActivityInitialState();
    }

    @Override
    public void onTvRateThisAppClick() {
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_STORE_TAAP)));
    }

    @Override
    public void onTvSeeMoreClick() {
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ARMANDGRAY_COM)));
    }

    @Override
    public void onTvCopyrightClick() {
        Intent intent = new Intent(activity, SettingsDetailActivity.class);
        activity.startActivity(intent);
    }
}
