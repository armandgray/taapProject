package com.armandgray.taap.log;

import com.armandgray.taap.R;

class LogActivityViews {

    LogActivity activity;
    LogViewsListener listener;

    LogActivityViews(LogActivity activity, LogViewsListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    void setupActivityInitialState() {
        activity.setContentView(R.layout.activity_main);
    }

    interface LogViewsListener {
    }
}
