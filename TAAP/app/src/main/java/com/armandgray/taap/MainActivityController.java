package com.armandgray.taap;

import android.app.Service;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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
        views.spinner.performClick();
        views.spinner.requestFocusFromTouch();
    }

    @Override
    public void onSearchClick() {
        views.etSearch.setVisibility(View.VISIBLE);
        views.spinner.setVisibility(View.GONE);
        views.fab.setVisibility(View.GONE);
        views.etSearch.requestFocusFromTouch();
        ((InputMethodManager) activity.getSystemService(Service.INPUT_METHOD_SERVICE))
                .showSoftInput(views.etSearch, 0);
    }

    @Override
    public void onEtSearchFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            views.etSearch.setVisibility(View.GONE);
            views.spinner.setVisibility(View.VISIBLE);
            views.fab.setVisibility(View.VISIBLE);
            ((InputMethodManager) activity.getSystemService(Service.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(views.etSearch.getWindowToken(), 0);
        }
    }
}
