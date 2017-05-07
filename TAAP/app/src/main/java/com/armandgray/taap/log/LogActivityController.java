package com.armandgray.taap.log;

import android.support.annotation.VisibleForTesting;

import com.armandgray.taap.models.SessionLog;

import java.util.ArrayList;

import static com.armandgray.taap.db.DatabaseContentProvider.insertLogToDatabase;
import static com.armandgray.taap.log.LogActivity.SESSION_LOG;

class LogActivityController implements LogActivityViews.LogViewsListener {

    @VisibleForTesting LogActivity activity;
    @VisibleForTesting LogActivityViews views;
    @VisibleForTesting SessionLog sessionLog;
    ArrayList<SessionLog> listAllLogs;

    LogActivityController(LogActivity activity) {
        this.activity = activity;
        this.views = new LogActivityViews(activity, this);
        this.sessionLog = activity.getIntent().getParcelableExtra(SESSION_LOG);

        if (sessionLog != null) { insertLogToDatabase(sessionLog, activity); }
        views.setupActivityInitialState();
    }

}
