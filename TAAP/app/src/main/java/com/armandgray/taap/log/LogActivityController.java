package com.armandgray.taap.log;

import android.database.Cursor;
import android.support.annotation.VisibleForTesting;

import com.armandgray.taap.R;
import com.armandgray.taap.db.LogsTable;
import com.armandgray.taap.models.Drill;
import com.armandgray.taap.models.SessionLog;

import java.util.ArrayList;
import java.util.Date;

import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_LOGS;
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
        this.listAllLogs = new ArrayList<>();

        if (sessionLog != null) { insertLogToDatabase(sessionLog, activity); }
        assignListAllLogsFromDatabase();
        views.setupActivityInitialState();
    }

    private void assignListAllLogsFromDatabase() {
        Cursor cursor = activity.getContentResolver()
                .query(CONTENT_URI_LOGS, LogsTable.ALL_LOG_COLUMNS, null, null, null);
        if (cursor == null) { return; }
        while (cursor.moveToNext()) {
            listAllLogs.add(getLogAtCurrentPosition(cursor));
        }
        cursor.close();
    }

    private SessionLog getLogAtCurrentPosition(Cursor cursor) {
        int columnLogId = cursor.getColumnIndex(LogsTable.LOG_ID);
        int columnDate = cursor.getColumnIndex(LogsTable.COLUMN_DATE);
        int columnLength = cursor.getColumnIndex(LogsTable.COLUMN_LENGTH);
        int columnGoal = cursor.getColumnIndex(LogsTable.COLUMN_GOAL);
        int columnActiveWork = cursor.getColumnIndex(LogsTable.COLUMN_ACTIVE_WORK);
        int columnRestTime = cursor.getColumnIndex(LogsTable.COLUMN_REST_TIME);
        int columnSetsCompleted = cursor.getColumnIndex(LogsTable.COLUMN_SETS_COMPLETED);
        int columnRepsCompleted = cursor.getColumnIndex(LogsTable.COLUMN_REPS_COMPLETED);
        int columnSuccess = cursor.getColumnIndex(LogsTable.COLUMN_SUCCESS);
        int columnDrill = cursor.getColumnIndex(LogsTable.COLUMN_DRILL);

        Drill drill = new Drill(
                "5 Spots Shooting (Mid-Range)",
                R.drawable.ic_account_multiple_outline_white_48dp,
                Drill.SHOOTING_ARRAY);

        SessionLog sessionLog = new SessionLog.Builder()
                .sessionLength(new Date(cursor.getLong(columnLength)))
                .sessionGoal(cursor.getString(columnGoal))
                .activeWork(new Date(cursor.getLong(columnActiveWork)))
                .restTime(new Date(cursor.getLong(columnRestTime)))
                .setsCompleted(cursor.getInt(columnSetsCompleted))
                .repsCompleted(cursor.getInt(columnRepsCompleted))
                .successRate(cursor.getDouble(columnSuccess))
                .drill(drill)
                .create();
        sessionLog.setSessionDate(new Date(cursor.getLong(columnDate)));
        sessionLog.setSessionId(cursor.getInt(columnLogId));
        return sessionLog;
    }

}
