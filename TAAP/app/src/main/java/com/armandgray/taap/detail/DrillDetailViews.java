package com.armandgray.taap.detail;

import com.armandgray.taap.R;

class DrillDetailViews {

    public DrillDetailActivity activity;

    DrillDetailViews(DrillDetailActivity activity) {
        this.activity = activity;

        setupActivityInitialState();
    }

    private void setupActivityInitialState() {
       activity.setContentView(R.layout.activity_drill_detail);

    }
}
