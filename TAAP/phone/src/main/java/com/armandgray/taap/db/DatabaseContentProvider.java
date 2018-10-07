package com.armandgray.taap.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.armandgray.shared.model.Drill;
import com.armandgray.taap.models.SessionLog;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

public class DatabaseContentProvider extends ContentProvider {


    public static final String[] ALL_TABLE_COLUMNS;
    public static final Uri CONTENT_URI_ALL;
    public static final Uri CONTENT_URI_DRILLS;
    public static final Uri CONTENT_URI_LOGS;
    public static final Uri CONTENT_URI_DELETE_ALL_DATA;
    @VisibleForTesting static final String AUTHORITY = "com.armandgray.taap.db.provider";
    @VisibleForTesting static final String BASE_PATH_ALL = "all";
    @VisibleForTesting static final String BASE_PATH_DRILLS = "drills";
    @VisibleForTesting static final String BASE_PATH_LOGS = "logs";
    @VisibleForTesting static final String BASE_PATH_DELETE_ALL_DATA = "delete_all";

    @VisibleForTesting static final int ALL_DATA = 1;
    @VisibleForTesting static final int ALL_DATA_DRILL_ID = 2;
    @VisibleForTesting static final int DELETE_ALL = 3;
    @VisibleForTesting static final int ALL_DRILLS = 4;
    @VisibleForTesting static final int DRILLS_ID = 5;
    @VisibleForTesting static final int ALL_LOGS = 6;
    @VisibleForTesting static final int LOGS_ID = 7;

    @VisibleForTesting
    static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static final int EXECUTION_FAILURE = -1;

    static {
        ArrayList<String> expectedColumns = new ArrayList<>();
        expectedColumns.addAll(Arrays.asList(LogsTable.ALL_LOG_COLUMNS));
        expectedColumns.addAll(Arrays.asList(DrillsTable.ALL_DRILL_COLUMNS));
        ALL_TABLE_COLUMNS = expectedColumns.toArray(new String[expectedColumns.size()]);

        CONTENT_URI_ALL = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_ALL);
        CONTENT_URI_DRILLS = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_DRILLS);
        CONTENT_URI_LOGS = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_LOGS);
        CONTENT_URI_DELETE_ALL_DATA = Uri.parse("content://" + AUTHORITY + "/"
                + BASE_PATH_DELETE_ALL_DATA);

        uriMatcher.addURI(AUTHORITY, BASE_PATH_ALL, ALL_DATA);
        uriMatcher.addURI(AUTHORITY, BASE_PATH_ALL + "/#", ALL_DATA_DRILL_ID);
        uriMatcher.addURI(AUTHORITY, BASE_PATH_DELETE_ALL_DATA, DELETE_ALL);
        uriMatcher.addURI(AUTHORITY, BASE_PATH_DRILLS, ALL_DRILLS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH_DRILLS + "/#", DRILLS_ID);
        uriMatcher.addURI(AUTHORITY, BASE_PATH_LOGS, ALL_LOGS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH_LOGS + "/#", LOGS_ID);
    }


    @VisibleForTesting
    SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        database = new DatabaseOpenHelper(getContext()).getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case ALL_DATA:
                return database.rawQuery("SELECT * FROM logs INNER JOIN drills ON logs.itemDrill = drills.drillId", null);

            case ALL_DATA_DRILL_ID:
                return database.rawQuery("SELECT * FROM logs INNER JOIN drills ON logs.itemDrill = drills.drillId WHERE logs.itemDrill = ?", selectionArgs);

            case ALL_DRILLS:
                return database.query(DrillsTable.TABLE_DRILLS, projection, selection,
                        null, null, null, null);

            case DRILLS_ID:
                return database.query(DrillsTable.TABLE_DRILLS, projection, selection,
                        null, null, null, null);

            case ALL_LOGS:
                return database.query(LogsTable.TABLE_LOGS, projection, selection,
                        null, null, null, null);

            case LOGS_ID:
                return database.query(LogsTable.TABLE_LOGS, projection, selection,
                        null, null, null, null);
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        if (uriMatcher.match(uri) == ALL_DRILLS || uriMatcher.match(uri) == DRILLS_ID) {
            return DrillsTable.TABLE_DRILLS;
        }
        if (uriMatcher.match(uri) == ALL_LOGS || uriMatcher.match(uri) == LOGS_ID) {
            return LogsTable.TABLE_LOGS;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (uriMatcher.match(uri) == ALL_DRILLS) {
            long id = database.insert(DrillsTable.TABLE_DRILLS, null, values);
            return Uri.parse(BASE_PATH_DRILLS + "/" + id);
        }
        if (uriMatcher.match(uri) == ALL_LOGS) {
            long id = database.insert(LogsTable.TABLE_LOGS, null, values);
            return Uri.parse(BASE_PATH_LOGS + "/" + id);
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case DRILLS_ID:
                return database.delete(DrillsTable.TABLE_DRILLS, selection, selectionArgs);

            case LOGS_ID:
                return database.delete(LogsTable.TABLE_LOGS, selection, selectionArgs);

            case DELETE_ALL:
                database.delete(DrillsTable.TABLE_DRILLS, null, null);
                return database.delete(LogsTable.TABLE_LOGS, null, null);
        }
        return EXECUTION_FAILURE;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (uriMatcher.match(uri) == DRILLS_ID) {
            return database.update(DrillsTable.TABLE_DRILLS, values, selection, selectionArgs);
        }
        if (uriMatcher.match(uri) == LOGS_ID) {
            return database.update(LogsTable.TABLE_LOGS, values, selection, selectionArgs);
        }
        return EXECUTION_FAILURE;
    }

    public static void insertDrillToDatabase(Drill drill, Context context) {
        ContentValues drillValues = getDrillContentValues(drill);
        Uri uri = context.getContentResolver().insert(CONTENT_URI_DRILLS, drillValues);
        if (uri != null) {
//            drill.setId(Integer.parseInt(uri.getLastPathSegment()));
        }
    }

    @NonNull
    @VisibleForTesting
    static ContentValues getDrillContentValues(Drill drill) {
        ContentValues drillValues = new ContentValues();
        drillValues.put(DrillsTable.COLUMN_TITLE, drill.getTitle());
        drillValues.put(DrillsTable.COLUMN_IMAGE_ID, drill.getImageResId());
//        drillValues.put(DrillsTable.COLUMN_CATEGORY, getArrayAsString(drill.getCategory()));
        return drillValues;
    }

    public static void insertLogToDatabase(SessionLog sessionLog, Context context) {
        ContentValues logValues = getLogContentValues(sessionLog);
        if (sessionLog.getDrill().getId() == 0) {
            insertDrillToDatabase(sessionLog.getDrill(), context);
        }
        logValues.put(LogsTable.COLUMN_DRILL, sessionLog.getDrill().getId());
        Uri uri = context.getContentResolver().insert(CONTENT_URI_LOGS, logValues);
        if (uri != null) {
            sessionLog.setSessionId(Integer.parseInt(uri.getLastPathSegment()));
        }
    }

    @NonNull
    @VisibleForTesting
    static ContentValues getLogContentValues(SessionLog testSessionLog) {
        ContentValues logValues = new ContentValues();
        logValues.put(LogsTable.COLUMN_DATE, testSessionLog.getSessionDate().getTime());
        logValues.put(LogsTable.COLUMN_LENGTH, testSessionLog.getSessionLength().getTime());
        logValues.put(LogsTable.COLUMN_GOAL, testSessionLog.getSessionGoal());
        logValues.put(LogsTable.COLUMN_ACTIVE_WORK, testSessionLog.getActiveWork().getTime());
        logValues.put(LogsTable.COLUMN_REST_TIME, testSessionLog.getRestTime().getTime());
        logValues.put(LogsTable.COLUMN_SETS_COMPLETED, testSessionLog.getSetsCompleted());
        logValues.put(LogsTable.COLUMN_REPS_COMPLETED, testSessionLog.getRepsCompleted());
        logValues.put(LogsTable.COLUMN_SUCCESS, testSessionLog.getSuccessRate());
        return logValues;
    }
}
