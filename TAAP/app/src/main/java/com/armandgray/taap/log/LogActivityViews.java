package com.armandgray.taap.log;

import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.armandgray.taap.R;

class LogActivityViews {

    @VisibleForTesting LogActivity activity;
    private LogViewsListener listener;

    LogActivityViews(LogActivity activity, LogViewsListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    void setupActivityInitialState() {
        activity.setContentView(R.layout.activity_log);
        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        ((TextView) toolbar.findViewById(R.id.tvTitle)).setText(R.string.session_history);

        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    interface LogViewsListener {
    }
}
