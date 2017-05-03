package com.armandgray.taap.log;

import android.support.annotation.VisibleForTesting;

class LogActivityController {

    @VisibleForTesting LogActivity activity;
    @VisibleForTesting LogActivityViews views;

    LogActivityController(LogActivity activity) {
        this.activity = activity;
    }
}
