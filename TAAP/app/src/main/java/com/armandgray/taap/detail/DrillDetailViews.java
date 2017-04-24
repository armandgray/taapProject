package com.armandgray.taap.detail;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;

import com.armandgray.taap.R;

class DrillDetailViews {

    public DrillDetailActivity activity;
    private FloatingActionButton fab;

    DrillDetailViews(DrillDetailActivity activity) {
        this.activity = activity;

        setupActivityInitialState();
    }

    private void setupActivityInitialState() {
        activity.setContentView(R.layout.activity_drill_detail);
        assignGlobalViews();
        setupToolbar();
    }

    private void assignGlobalViews() {
        fab = (FloatingActionButton) activity.findViewById(R.id.fab);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
    }

}
