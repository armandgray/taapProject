package com.armandgray.taap.log;

import android.app.Activity;
import android.support.annotation.VisibleForTesting;

class LogActivityController {

    @VisibleForTesting Activity activity;

    LogActivityController(Activity activity) {
        this.activity = activity;
    }
}
