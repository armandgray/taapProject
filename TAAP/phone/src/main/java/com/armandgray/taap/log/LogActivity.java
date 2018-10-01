package com.armandgray.taap.log;

import android.os.Bundle;

import com.armandgray.taap.R;
import com.armandgray.taap.utils.MVCActivity;

public class LogActivity extends MVCActivity<LogActivityViews, LogActivityController> {

    public static final String SESSION_LOG = "SESSION_LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupActivity(R.layout.activity_log);
        setViews(new LogActivityViews(rootView, toolbar));
        setController(new LogActivityController(this, getSupportActionBar(), views));
    }
}
