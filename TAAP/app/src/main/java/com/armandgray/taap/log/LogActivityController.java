package com.armandgray.taap.log;

import android.database.Cursor;
import android.support.annotation.VisibleForTesting;

import com.armandgray.taap.db.DrillsTable;
import com.armandgray.taap.db.LogsTable;
import com.armandgray.taap.models.Drill;
import com.armandgray.taap.models.SessionLog;

import java.util.ArrayList;
import java.util.Date;

import static com.armandgray.taap.db.DatabaseContentProvider.ALL_TABLE_COLUMNS;
import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_ALL;
import static com.armandgray.taap.db.DatabaseContentProvider.insertLogToDatabase;
import static com.armandgray.taap.log.LogActivity.SESSION_LOG;
import static com.armandgray.taap.utils.StringHelper.getStringAsArray;

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
                .query(CONTENT_URI_ALL, ALL_TABLE_COLUMNS, null, null, null);
        if (cursor == null) { return; }
        while (cursor.moveToNext()) {
            SessionLog logAtCurrentPosition = getLogAtCurrentPosition(cursor);
            listAllLogs.add(logAtCurrentPosition);
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

        Drill drill = getLogDrillFromCursor(cursor);
        if (drill == null) { return null; }

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

    private Drill getLogDrillFromCursor(Cursor cursor) {
        int columnLogDrill = cursor.getColumnIndex(LogsTable.COLUMN_DRILL);
        int columnDrillId = cursor.getColumnIndex(DrillsTable.DRILL_ID);
        int columnTitle = cursor.getColumnIndex(DrillsTable.COLUMN_TITLE);
        int columnImageId = cursor.getColumnIndex(DrillsTable.COLUMN_IMAGE_ID);
        int columnCategory = cursor.getColumnIndex(DrillsTable.COLUMN_CATEGORY);

        if (cursor.getInt(columnDrillId) != cursor.getInt(columnLogDrill)) { return null; }
        return new Drill(
                cursor.getString(columnTitle),
                cursor.getInt(columnImageId),
                getStringAsArray(cursor.getString(columnCategory)));
    }

}
