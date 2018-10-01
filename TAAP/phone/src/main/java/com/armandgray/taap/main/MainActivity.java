package com.armandgray.taap.main;

import android.os.Bundle;

import com.armandgray.taap.R;
import com.armandgray.taap.utils.MVCActivity;

public class MainActivity extends MVCActivity<MainActivityViews, MainActivityController> {

    public static final String SELECTED_DRILL = "SELECTED_DRILL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupActivity(R.layout.activity_main, R.menu.menu_main);
        setViews(new MainActivityViews(rootView, fab, this));
        setController(new MainActivityController(this, getSupportActionBar(), views));
    }
}
