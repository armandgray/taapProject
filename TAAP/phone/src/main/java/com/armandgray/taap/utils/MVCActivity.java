package com.armandgray.taap.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.armandgray.taap.R;

/**
 * Created by armandgray on 1/12/18.
 */

public abstract class MVCActivity<
        T extends ActivitySetupHelper.ActivityViewsInterface,
        U extends MVCActivityController
        > extends AppCompatActivity {
    @VisibleForTesting public T views;
    @VisibleForTesting public U controller;
    @NonNull protected View rootView;
    @Nullable protected Toolbar toolbar;
    @Nullable protected FloatingActionButton fab;
    private int menuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuId == 0) {
            return false;
        }

        getMenuInflater().inflate(menuId, menu);
        return controller.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        controller.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        controller.dispatchTouchEvent(getCurrentFocus(), ev);
        return super.dispatchTouchEvent(ev);
    }

    public void setupActivity(int layoutId) {
        setContentView(layoutId);
        this.rootView = findViewById(android.R.id.content).getRootView();
        assert this.rootView != null;
        this.toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        this.fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        setSupportActionBar(toolbar);
    }

    public void setupActivity(int layoutId, int menuId) {
        setupActivity(layoutId);
        this.menuId = menuId;
    }

    protected void setViews(T views) {
        this.views = views;
    }

    protected void setController(U controller) {
        this.controller = controller;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (controller == null) {
            throw new IllegalArgumentException(getLocalClassName()
                    + " must assign controller (ie. Call setController())");
        }

        if (views == null) {
            throw new IllegalArgumentException(getLocalClassName()
                    + " must assign views (ie. Call setViews())");
        }
    }
}