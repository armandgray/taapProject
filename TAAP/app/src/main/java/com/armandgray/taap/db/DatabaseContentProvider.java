package com.armandgray.taap.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.armandgray.taap.models.Drill;
import com.armandgray.taap.models.SessionLog;

import static com.armandgray.taap.utils.StringHelper.getArrayAsString;

public class DatabaseContentProvider extends ContentProvider {

    public static final Uri CONTENT_URI_DRILLS;
    public static final Uri CONTENT_URI_LOGS;
    @VisibleForTesting
    static final String AUTHORITY = "com.armandgray.taap.db.provider";
    @VisibleForTesting
    static final String BASE_PATH_DRILLS = "drills";
    @VisibleForTesting
    static final String BASE_PATH_LOGS = "logs";

    @VisibleForTesting
    static final int ALL_DRILLS = 1;
    @VisibleForTesting
    static final int DRILLS_ID = 2;
    @VisibleForTesting
    static final int ALL_LOGS = 3;
    @VisibleForTesting
    static final int LOGS_ID = 4;

    @VisibleForTesting
    static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static final int EXECUTION_FAILURE = -1;

    static {
        CONTENT_URI_DRILLS = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_DRILLS);
        CONTENT_URI_LOGS = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_LOGS);

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
        if (uriMatcher.match(uri) == DRILLS_ID) {
            return database.delete(DrillsTable.TABLE_DRILLS, selection, selectionArgs);
        }
        if (uriMatcher.match(uri) == LOGS_ID) {
            return database.delete(LogsTable.TABLE_LOGS, selection, selectionArgs);
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
            drill.setDrillId(Integer.parseInt(uri.getLastPathSegment()));
        }
    }

    @NonNull
    @VisibleForTesting
    static ContentValues getDrillContentValues(Drill drill) {
        ContentValues drillValues = new ContentValues();
        drillValues.put(DrillsTable.COLUMN_TITLE, drill.getTitle());
        drillValues.put(DrillsTable.COLUMN_IMAGE_ID, drill.getImageId());
        drillValues.put(DrillsTable.COLUMN_CATEGORY, getArrayAsString(drill.getCategory()));
        return drillValues;
    }

    public static void insertLogToDatabase(SessionLog sessionLog, Context context) {
        ContentValues logValues = getLogContentValues(sessionLog);
        if (sessionLog.getDrill().getDrillId() == 0) {
            insertDrillToDatabase(sessionLog.getDrill(), context);
        }
        logValues.put(LogsTable.COLUMN_DRILL, sessionLog.getDrill().getDrillId());
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
