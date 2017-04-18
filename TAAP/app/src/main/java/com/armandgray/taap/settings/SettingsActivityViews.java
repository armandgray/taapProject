package com.armandgray.taap.settings;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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
        setupToolbarHomeButton();
        TextView tvSeeMore = (TextView) activity.findViewById(R.id.tvSeeMore);
        tvSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }

    private void setupToolbarHomeButton() {
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
