package com.armandgray.taap.settings;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.armandgray.taap.R;

class SettingsActivityViews {

    public static final String SETTINGS = "Settings";
    SettingsActivity activity;
    SettingsViewsListener listener;

    SettingsActivityViews(SettingsActivity activity, SettingsViewsListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    void setupActivityInitialState() {
        activity.setContentView(R.layout.activity_settings);
        setupToolbar();
        setupOnClickListeners();
    }

    private void setupToolbar() {
        setSupportActionBarAsToolbar();
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setSupportActionBarAsToolbar() {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.tvTitle))
                .setText(SETTINGS);
        activity.setSupportActionBar(toolbar);
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

        TextView tvTermsConditions = (TextView) activity.findViewById(R.id.tvTermsConditions);
        tvTermsConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTvTermsConditionsClick();
            }
        });

        TextView tvSoftwareLicenses = (TextView) activity.findViewById(R.id.tvSoftwareLicenses);
        tvSoftwareLicenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTvSoftwareLicensesClick();
            }
        });
    }

    interface SettingsViewsListener {
        void onTvSeeMoreClick();
        void onTvRateThisAppClick();
        void onTvCopyrightClick();
        void onTvTermsConditionsClick();
        void onTvSoftwareLicensesClick();
    }
}
