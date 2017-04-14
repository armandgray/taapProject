package com.armandgray.taap;

import android.support.v7.widget.Toolbar;

class MainActivityController {
    MainActivity activity;

    MainActivityController(MainActivity activity) {
        this.activity = activity;
        setupActivityInitialState();
    }

    void setupActivityInitialState() {
        activity.setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
    }
}
