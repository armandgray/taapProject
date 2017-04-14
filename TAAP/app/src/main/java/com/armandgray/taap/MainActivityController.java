package com.armandgray.taap;

import android.app.Activity;

class MainActivityController {
    Activity activity;

    MainActivityController(Activity activity) {
        this.activity = activity;
        setupActivityInitialState();
    }

    void setupActivityInitialState() {
        activity.setContentView(R.layout.activity_main);
    }
}
