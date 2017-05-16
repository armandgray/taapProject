package com.armandgray.taap.log;

import android.database.Cursor;
import android.support.annotation.VisibleForTesting;

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
import static com.armandgray.taap.models.SessionLog.ACTIVE_WORK;
import static com.armandgray.taap.models.SessionLog.REST_TIME;
import static com.armandgray.taap.models.SessionLog.SESSION_LENGTH;
import static com.armandgray.taap.utils.CursorDataHelper.addAllLogsData;
import static com.armandgray.taap.utils.DateTimeHelper.getDateFormattedAsString;
import static com.armandgray.taap.utils.DateTimeHelper.getTotalTimeAsDate;
import static com.armandgray.taap.utils.MathHelper.getAveragePercentage;
import static com.armandgray.taap.utils.MathHelper.getPercentFormattedAsString;

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
        addAllLogsData(cursor, listAllLogs);
        cursor.close();

        retrieveFieldData();
        setViewFields();
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

    private void addAll(ArrayList<SessionLog> targetList, ArrayList<SessionLog> sourceList, String[] categories) {
        for (SessionLog log : sourceList) {
            for (String category : categories) {
                if (Arrays.asList(log.getDrill().getCategory()).contains(category)) {
                    targetList.add(log);
                    break;
                }
            }
        }
    }

    private void setViewFields() {
        Date totalTimeAsDate = getTotalTimeAsDate(listAllLogs, SESSION_LENGTH);
        String dateFormattedAsString = getDateFormattedAsString(totalTimeAsDate);
        views.setDataValueForDetailLayout(
                views.layoutTotalSessionTime,
                dateFormattedAsString);
        views.setDataValueForDetailLayout(
                views.layoutTotalActiveTime,
                getDateFormattedAsString(getTotalTimeAsDate(listAllLogs, ACTIVE_WORK)));
        views.setDataValueForDetailLayout(
                views.layoutTotalRestTime,
                getDateFormattedAsString(getTotalTimeAsDate(listAllLogs, REST_TIME)));
        views.setDataValueForDetailLayout(
                views.layoutRepsCompleted,
                String.valueOf(getTotalReps()));
        views.setDataValueForDetailLayout(
                views.layoutExercisesCompleted,
                String.valueOf(listAllLogs.size()));
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

    private int getTotalReps() {
        int reps = 0;
        for (SessionLog log : listAllLogs) {
            reps += log.getRepsCompleted() > 0
                    ? log.getSetsCompleted() * log.getRepsCompleted()
                    : log.getSetsCompleted();
        }
        return reps;
    }

}
