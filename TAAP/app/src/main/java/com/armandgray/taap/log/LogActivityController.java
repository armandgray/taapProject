package com.armandgray.taap.log;

import android.support.annotation.VisibleForTesting;

import com.armandgray.taap.models.SessionLog;

class LogActivityController implements LogActivityViews.LogViewsListener {

    @VisibleForTesting LogActivity activity;
    @VisibleForTesting LogActivityViews views;
    @VisibleForTesting SessionLog sessionLog;

    LogActivityController(LogActivity activity) {
        this.activity = activity;
        this.views = new LogActivityViews(activity, this);

        views.setupActivityInitialState();
    }
}
