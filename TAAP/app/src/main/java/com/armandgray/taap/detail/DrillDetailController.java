package com.armandgray.taap.detail;

class DrillDetailController {

    DrillDetailActivity activity;
    DrillDetailViews views;

    DrillDetailController(DrillDetailActivity activity) {
        this.activity = activity;
        this.views = new DrillDetailViews(activity);
    }
}
