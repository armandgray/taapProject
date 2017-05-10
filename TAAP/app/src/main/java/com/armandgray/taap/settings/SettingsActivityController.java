package com.armandgray.taap.settings;

import android.content.Intent;
import android.net.Uri;

import com.armandgray.taap.settings.detail.SettingsDetailActivity;

import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_DELETE_ALL_DATA;

public class SettingsActivityController implements SettingsActivityViews.SettingsViewsListener {

    static final String ARMANDGRAY_COM = "http://armandgray.com";
    // TODO change to Taap URL
    static final String GOOGLE_PLAY_STORE_TAAP = "https://play.google.com/store/apps/details?id=com.armandgray.taap&hl=en";
    public static final String SELECTED_ITEM = "SELECTED_ITEM";
    public static final String TERMS_AND_CONDITIONS = "Terms & Conditions";
    public static final String SOFTWARE_LICENSES = "Software Licenses";

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
    public void onTvClearDataClick() {
        activity.getContentResolver().delete(CONTENT_URI_DELETE_ALL_DATA, null, null);
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
