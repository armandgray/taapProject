package com.armandgray.taap.settings;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
        setupOnClickListeners();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setHomeAsUpIndicatorColor();
        }
    }

    private void setHomeAsUpIndicatorColor() {
        final Drawable upArrow = activity.getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(activity.getResources().getColor(R.color.colorDarkGray), PorterDuff.Mode.SRC_ATOP);
        if (activity.getSupportActionBar() == null) { return; }
        activity.getSupportActionBar().setHomeAsUpIndicator(upArrow);
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

        TextView tvCopyright = (TextView) activity.findViewById(R.id.tvClearData);
        tvCopyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTvClearDataClick();
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
        void onTvClearDataClick();
        void onTvTermsConditionsClick();
        void onTvSoftwareLicensesClick();
    }
}
