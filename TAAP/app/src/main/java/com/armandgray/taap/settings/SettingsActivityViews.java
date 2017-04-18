package com.armandgray.taap.settings;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.armandgray.taap.R;

class SettingsActivityViews {

    SettingsActivity activity;
    SettingsViewsListener listener;

    SettingsActivityViews(SettingsActivity activity, SettingsViewsListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    void setupActivityInitialState() {
        activity.setContentView(R.layout.activity_settings);
        setupToolbar();
        setupToolbarHomeButton();
        setupOnClickListeners();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
    }

    private void setupToolbarHomeButton() {
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupOnClickListeners() {
        TextView tvRateThisApp = (TextView) activity.findViewById(R.id.tvRateThisApp);
        tvRateThisApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTvRateThisAppClick();
            }
        });

        TextView tvSeeMore = (TextView) activity.findViewById(R.id.tvSeeMore);
        tvSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTvSeeMoreClick();
            }
        });

        TextView tvCopyright = (TextView) activity.findViewById(R.id.tvCopyright);
        tvCopyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTvCopyrightClick();
            }
        });
    }

    interface SettingsViewsListener {
        void onTvSeeMoreClick();
        void onTvRateThisAppClick();
        void onTvCopyrightClick();
    }
}
