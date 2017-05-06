package com.armandgray.taap.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

public class DatabaseContentProvider extends ContentProvider {

    public static final Uri CONTENT_URI_DRILLS;
    public static final Uri CONTENT_URI_LOGS;
    @VisibleForTesting static final String AUTHORITY = "com.armandgray.taap.db.provider";
    @VisibleForTesting static final String BASE_PATH_DRILLS = "drills";
    @VisibleForTesting static final String BASE_PATH_LOGS = "logs";

    @VisibleForTesting static final int ALL_DRILLS = 1;
    @VisibleForTesting static final int DRILLS_ID = 2;

    @VisibleForTesting static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        CONTENT_URI_DRILLS = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_DRILLS);
        CONTENT_URI_LOGS = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_LOGS);

        uriMatcher.addURI(AUTHORITY, BASE_PATH_DRILLS, ALL_DRILLS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH_DRILLS + "/#", DRILLS_ID);
    }


    @VisibleForTesting SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        database = new DatabaseOpenHelper(getContext()).getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
