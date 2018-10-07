package com.armandgray.taap.db;

import android.content.Context;
import android.database.Cursor;

import com.armandgray.shared.model.Drill;
import com.armandgray.taap.models.SessionLog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.armandgray.taap.db.DatabaseContentProvider.ALL_TABLE_COLUMNS;
import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_ALL;
import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_DRILLS;

public class CursorDataHelper {

    public static ArrayList<Drill> getDrillsListFromDatabase(Context context) {
        Cursor cursor = context.getContentResolver()
                .query(CONTENT_URI_DRILLS, DrillsTable.ALL_DRILL_COLUMNS,
                        null, null, null);
        if (cursor == null) { return null; }
        ArrayList<Drill> listAllDrills = new ArrayList<>();
        addAllDrillsData(cursor, listAllDrills);
        cursor.close();
        return listAllDrills;
    }

    private static void addAllDrillsData(Cursor cursor, List<Drill> drills) {
        if (cursor.getCount() == 0) { return; }
        while (cursor.moveToNext()) {
            Drill drillAtCurrentPosition = getDrillFromCursor(cursor);
            drills.add(drillAtCurrentPosition);
        }
    }

    private static Drill getDrillFromCursor(Cursor cursor) {
        int columnDrillId = cursor.getColumnIndex(DrillsTable.DRILL_ID);
        int columnTitle = cursor.getColumnIndex(DrillsTable.COLUMN_TITLE);
        int columnImageId = cursor.getColumnIndex(DrillsTable.COLUMN_IMAGE_ID);
        int columnCategory = cursor.getColumnIndex(DrillsTable.COLUMN_CATEGORY);

//        Drill drill = new Drill(
//                cursor.getString(columnTitle),
//                cursor.getInt(columnImageId),
//                getStringAsArray(cursor.getString(columnCategory)));
//        drill.setId(cursor.getInt(columnDrillId));
//        return drill;
        return null;
    }

    public static ArrayList<SessionLog> getAllLogsFromDatabase(Context context) {
        ArrayList<SessionLog> list = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(CONTENT_URI_ALL, ALL_TABLE_COLUMNS,
                null, null, null);
        if (cursor == null) {
            return null;
        }

        addAllLogsForQuery(list, cursor);
        cursor.close();
        return list;
    }

    public static void addAllLogsForQuery(List<SessionLog> target, Cursor cursor) {
        if (cursor.getCount() == 0) { return; }
        while (cursor.moveToNext()) {
            SessionLog logAtCurrentPosition = getLogAtCurrentPosition(cursor);
            target.add(logAtCurrentPosition);
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

        Drill drill = getDrillFromCursor(cursor);
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

}
