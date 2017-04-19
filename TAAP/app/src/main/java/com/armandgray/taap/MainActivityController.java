package com.armandgray.taap;

import android.view.View;

class MainActivityController implements MainActivityViews.MainViewsListener {

    MainActivity activity;
    MainActivityViews views;

    MainActivityController(MainActivity activity) {
        this.activity = activity;
        this.views = new MainActivityViews(activity, this);
        
        views.setupActivityInitialState();
    }

    @Override
    public void onFabClick() {
        // TODO add FAB click action
    }

    @Override
    public void onSortClick() {
        // TODO
    }

    @Override
    public void onSearchClick() {
        views.etSearch.setVisibility(View.VISIBLE);
        views.spinner.setVisibility(View.GONE);
        views.fab.setVisibility(View.GONE);

        views.etSearch.requestFocusFromTouch();
    }
}
