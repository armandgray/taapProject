package com.armandgray.taap;

class MainActivityController {
    MainActivity activity;

    MainActivityController(MainActivity activity) {
        this.activity = activity;
        setupActivityInitialState();
    }

    void setupActivityInitialState() {
        activity.setContentView(R.layout.activity_main);
    }
}
