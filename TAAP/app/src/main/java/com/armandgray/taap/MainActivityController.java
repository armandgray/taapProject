package com.armandgray.taap;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

class MainActivityController {

    MainActivity activity;
    MainActivityViews views;

    MainActivityController(MainActivity activity) {
        this.activity = activity;
        this.views = new MainActivityViews(activity);
    }

}
