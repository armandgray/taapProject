package com.armandgray.taap.utils;

import android.database.Cursor;

import com.armandgray.taap.db.DrillsTable;
import com.armandgray.taap.db.LogsTable;
import com.armandgray.taap.models.Drill;
import com.armandgray.taap.models.SessionLog;

import java.util.Date;
import java.util.List;

import static com.armandgray.taap.utils.StringHelper.getStringAsArray;

public class CursorDataHelper {

    public static void addAllLogsData(Cursor cursor, List<SessionLog> logs) {
        while (cursor.moveToNext()) {
            SessionLog logAtCurrentPosition = getLogAtCurrentPosition(cursor);
            logs.add(logAtCurrentPosition);
        }
    }

    private static SessionLog getLogAtCurrentPosition(Cursor cursor) {
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

    private static Drill getLogDrillFromCursor(Cursor cursor) {
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

}
