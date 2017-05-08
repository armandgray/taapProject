package com.armandgray.taap.log;

import android.database.Cursor;
import android.support.annotation.VisibleForTesting;

import com.armandgray.taap.db.DrillsTable;
import com.armandgray.taap.db.LogsTable;
import com.armandgray.taap.models.Drill;
import com.armandgray.taap.models.SessionLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static com.armandgray.taap.db.DatabaseContentProvider.ALL_TABLE_COLUMNS;
import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_ALL;
import static com.armandgray.taap.db.DatabaseContentProvider.insertLogToDatabase;
import static com.armandgray.taap.log.LogActivity.SESSION_LOG;
import static com.armandgray.taap.models.Drill.BALL_HANDLING;
import static com.armandgray.taap.models.Drill.CONDITIONING;
import static com.armandgray.taap.models.Drill.DEFENSE;
import static com.armandgray.taap.models.Drill.FUNDAMENTALS;
import static com.armandgray.taap.models.Drill.OFFENSE;
import static com.armandgray.taap.models.Drill.PASSING;
import static com.armandgray.taap.models.Drill.SHOOTING;
import static com.armandgray.taap.utils.DateTimeHelper.getDateFormattedAsString;
import static com.armandgray.taap.utils.DateTimeHelper.getTotalTimeAsDate;
import static com.armandgray.taap.utils.MathHelper.getAveragePercentage;
import static com.armandgray.taap.utils.MathHelper.getPercentFormattedAsString;
import static com.armandgray.taap.utils.StringHelper.getStringAsArray;

class LogActivityController {

    @VisibleForTesting LogActivity activity;
    @VisibleForTesting LogActivityViews views;
    @VisibleForTesting SessionLog sessionLog;
    ArrayList<SessionLog> listAllLogs;
    ArrayList<SessionLog> listFundamentalLogs;
    ArrayList<SessionLog> listDefenseLogs;
    ArrayList<SessionLog> listOffenseLogs;
    ArrayList<SessionLog> listConditioningLogs;
    ArrayList<SessionLog> listShootingLogs;
    ArrayList<SessionLog> listBallHandlingLogs;

    LogActivityController(LogActivity activity) {
        this.activity = activity;
        this.views = new LogActivityViews(activity);
        this.sessionLog = activity.getIntent().getParcelableExtra(SESSION_LOG);
        this.listAllLogs = new ArrayList<>();
        this.listFundamentalLogs = new ArrayList<>();
        this.listDefenseLogs = new ArrayList<>();
        this.listOffenseLogs = new ArrayList<>();
        this.listConditioningLogs = new ArrayList<>();
        this.listShootingLogs = new ArrayList<>();
        this.listBallHandlingLogs = new ArrayList<>();

        if (sessionLog != null) { insertLogToDatabase(sessionLog, activity); }
        views.setupActivityInitialState();
        setupHistoryFields();
    }

    private void setupHistoryFields() {
        Cursor cursor = activity.getContentResolver().query(CONTENT_URI_ALL, ALL_TABLE_COLUMNS,
                null, null, null);
        if (cursor == null) { return; }
        retrieveAllLogsData(cursor);
        cursor.close();

        retrieveFieldData();
        setViewFields();
    }

    private void retrieveAllLogsData(Cursor cursor) {
        while (cursor.moveToNext()) {
            SessionLog logAtCurrentPosition = getLogAtCurrentPosition(cursor);
            listAllLogs.add(logAtCurrentPosition);
        }
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
        Drill drill = new Drill(
                cursor.getString(columnTitle),
                cursor.getInt(columnImageId),
                getStringAsArray(cursor.getString(columnCategory)));
        drill.setDrillId(cursor.getInt(columnDrillId));
        return drill;
    }

    private void retrieveFieldData() {
        addAll(listFundamentalLogs, listAllLogs, FUNDAMENTALS);
        addAll(listDefenseLogs, listAllLogs, DEFENSE);
        addAll(listOffenseLogs, listAllLogs, OFFENSE);
        addAll(listOffenseLogs, listAllLogs, PASSING);
        addAll(listConditioningLogs, listAllLogs, CONDITIONING);
        addAll(listShootingLogs, listAllLogs, SHOOTING);
        addAll(listBallHandlingLogs, listAllLogs, BALL_HANDLING);
    }

    private void addAll(ArrayList<SessionLog> targetList, ArrayList<SessionLog> sourceList, String category) {
        for (SessionLog log : sourceList) {
            if (Arrays.asList(log.getDrill().getCategory()).contains(category)) {
                targetList.add(log);
            }
        }
    }

    void setViewFields() {
        views.setDataValuesForRecordLayout(
                views.layoutFundamentals,
                getDateFormattedAsString(getTotalTimeAsDate(listFundamentalLogs)),
                getPercentFormattedAsString(getAveragePercentage(listFundamentalLogs)));
        views.setDataValuesForRecordLayout(
                views.layoutDefense,
                getDateFormattedAsString(getTotalTimeAsDate(listDefenseLogs)),
                getPercentFormattedAsString(getAveragePercentage(listDefenseLogs)));
        views.setDataValuesForRecordLayout(
                views.layoutOffense,
                getDateFormattedAsString(getTotalTimeAsDate(listOffenseLogs)),
                getPercentFormattedAsString(getAveragePercentage(listOffenseLogs)));
        views.setDataValuesForRecordLayout(
                views.layoutConditioning,
                getDateFormattedAsString(getTotalTimeAsDate(listConditioningLogs)),
                getPercentFormattedAsString(getAveragePercentage(listConditioningLogs)));
        views.setDataValuesForRecordLayout(
                views.layoutShooting,
                getDateFormattedAsString(getTotalTimeAsDate(listShootingLogs)),
                getPercentFormattedAsString(getAveragePercentage(listShootingLogs)));
        views.setDataValuesForRecordLayout(
                views.layoutBallHandling,
                getDateFormattedAsString(getTotalTimeAsDate(listBallHandlingLogs)),
                getPercentFormattedAsString(getAveragePercentage(listBallHandlingLogs)));
    }

}
