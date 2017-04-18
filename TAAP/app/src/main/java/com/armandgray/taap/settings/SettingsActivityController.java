package com.armandgray.taap.settings;

import android.content.Intent;
import android.net.Uri;

class SettingsActivityController implements SettingsActivityViews.SettingsViewsListener {

    static final String ARMANDGRAY_COM = "http://armandgray.com";
    // TODO change to Taap URL
    static final String GOOGLE_PLAY_STORE_TAAP = "https://play.google.com/store/apps/details?id=com.armandgray.seeme&hl=en";
    static final String COPYRIGHT = "COPYRIGHT";
    static final String SELECTED_ITEM = "SELECTED_ITEM";
    public static final String TERMS_AND_CONDITIONS = "TERMS AND CONDITIONS";
    public static final String SOFTWARE_LICENSES = "SOFTWARE LICENSES";

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
        intent.putExtra(SELECTED_ITEM, COPYRIGHT);
        activity.startActivity(intent);
    }

    @Override
    public void onTvTermsConditionsClick() {
        Intent intent = new Intent(activity, SettingsDetailActivity.class);
        intent.putExtra(SELECTED_ITEM, TERMS_AND_CONDITIONS);
        activity.startActivity(intent);
    }

    @Override
    public void onTvSoftwareLicensesClick() {
        Intent intent = new Intent(activity, SettingsDetailActivity.class);
        intent.putExtra(SELECTED_ITEM, SOFTWARE_LICENSES);
        activity.startActivity(intent);
    }
}
