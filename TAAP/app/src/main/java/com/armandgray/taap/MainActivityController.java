package com.armandgray.taap;

class MainActivityController {

    MainActivity activity;
    MainActivityViews views;

    MainActivityController(MainActivity activity) {
        this.activity = activity;
        this.views = new MainActivityViews(activity);
        
        views.setupActivityInitialState();
    }

}
