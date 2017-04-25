package com.armandgray.taap.detail;

import android.content.Intent;

import com.armandgray.taap.LogActivity;

class DrillDetailController {

    DrillDetailActivity activity;
    DrillDetailViews views;

    DrillDetailController(DrillDetailActivity activity) {
        this.activity = activity;
        this.views = new DrillDetailViews(activity);
    }

    void onSummaryDialogDismiss() {
        Intent intent = new Intent(activity, LogActivity.class);
        activity.startActivity(intent);
    }
}
