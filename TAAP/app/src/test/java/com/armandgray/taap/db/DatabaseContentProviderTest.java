package com.armandgray.taap.db;

import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;
import com.armandgray.taap.models.Drill;
import com.armandgray.taap.models.SessionLog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Date;

import static com.armandgray.taap.db.DatabaseContentProvider.ALL_DRILLS;
import static com.armandgray.taap.db.DatabaseContentProvider.ALL_LOGS;
import static com.armandgray.taap.db.DatabaseContentProvider.AUTHORITY;
import static com.armandgray.taap.db.DatabaseContentProvider.BASE_PATH_DRILLS;
import static com.armandgray.taap.db.DatabaseContentProvider.BASE_PATH_LOGS;
import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_DRILLS;
import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_LOGS;
import static com.armandgray.taap.db.DatabaseContentProvider.DRILLS_ID;
import static com.armandgray.taap.db.DatabaseContentProvider.LOGS_ID;
import static com.armandgray.taap.db.DatabaseContentProvider.uriMatcher;
import static com.armandgray.taap.utils.StringsHelper.getArrayAsString;
import static com.armandgray.taap.utils.StringsHelper.getStringAsArray;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DatabaseContentProviderTest {

    private static final long TIME_IN_MILLIS = 1494179392802L;
    private static final Drill TEST_DRILL = new Drill(
                "5 Spots Shooting (Mid-Range)",
                R.drawable.ic_account_multiple_outline_white_48dp,
                Drill.SHOOTING_ARRAY);
    
    private static final SessionLog TEST_SESSION_LOG = new SessionLog.Builder()
            .sessionLength(new Date(TIME_IN_MILLIS))
            .sessionGoal("")
            .activeWork(new Date(TIME_IN_MILLIS + 555555))
            .restTime(new Date(TIME_IN_MILLIS + 111111))
            .setsCompleted(4)
            .repsCompleted(3)
            .successRate(0.23)
            .successRecord(0.55)
            .drill(TEST_DRILL)
            .create();

    @Test
    public void hasContentUri_Drills() {
        assertNotNull(CONTENT_URI_DRILLS);
        assertEquals(Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_DRILLS), CONTENT_URI_DRILLS);
    }

    @Test
    public void hasContentUri_Logs() {
        assertNotNull(CONTENT_URI_LOGS);
        assertEquals(Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_LOGS), CONTENT_URI_LOGS);
    }

    @Test
    public void hasContentUriMatcher() {
        assertNotNull(uriMatcher);
        assertEquals(ALL_DRILLS, uriMatcher.match(CONTENT_URI_DRILLS));
        assertEquals(DRILLS_ID,
                uriMatcher.match(Uri.parse(DatabaseContentProvider.CONTENT_URI_DRILLS + "/" + 0)));
        assertEquals(ALL_LOGS, uriMatcher.match(CONTENT_URI_LOGS));
        assertEquals(LOGS_ID,
                uriMatcher.match(Uri.parse(DatabaseContentProvider.CONTENT_URI_LOGS + "/" + 0)));
    }

    @Test
    public void doesExtendContentProvider() {
        ContentProvider databaseContentProvider = new DatabaseContentProvider();
        assertNotNull(databaseContentProvider);
    }

    @Test
    public void doesAssignWritableDatabase_TestOnCreate() {
        assertNotNull(getDatabaseContentProvider());
        assertNotNull(getDatabaseContentProvider().database);
    }

    @Test
    public void doesAssignWritableDatabase_WithDrillsAndLogsTables_TestOnCreate() {
        Cursor cursor = getDatabaseContentProvider().database
                .rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        assertTrue(cursor.moveToFirst());
        ArrayList<String> listTableNames = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            listTableNames.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();

        assertTrue(listTableNames.contains(DrillsTable.TABLE_DRILLS));
        assertTrue(listTableNames.contains(LogsTable.TABLE_LOGS));
    }

    @Test
    public void canQueryDatabaseForDrillUsingContentProvider() {
        insertDrillToDatabase();

        String selectedDrill = DrillsTable.DRILL_ID + " = " + TEST_DRILL.getDrillId();
        Cursor cursor = RuntimeEnvironment.application.getContentResolver()
                .query(CONTENT_URI_DRILLS, DrillsTable.ALL_DRILL_COLUMNS, selectedDrill,
                        null, null);

        assertNotNull(cursor);
        assertCursorDataEqualsDrill(cursor, TEST_DRILL);
        cursor.close();
    }

    @Test
    public void canGetTypeForUriUsingContentProvider() {
        assertEquals(DrillsTable.TABLE_DRILLS, RuntimeEnvironment.application.getContentResolver()
                .getType(CONTENT_URI_DRILLS));
        assertEquals(DrillsTable.TABLE_DRILLS, RuntimeEnvironment.application.getContentResolver()
                .getType(Uri.parse(CONTENT_URI_DRILLS + "/" + 0)));
        assertEquals(LogsTable.TABLE_LOGS, RuntimeEnvironment.application.getContentResolver()
                .getType(CONTENT_URI_LOGS));
        assertEquals(LogsTable.TABLE_LOGS, RuntimeEnvironment.application.getContentResolver()
                .getType(Uri.parse(CONTENT_URI_LOGS + "/" + 0)));
    }

    @Test
    public void canInsertDrillIntoDatabaseUsingContentProvider() {
        insertDrillToDatabase();

        Cursor cursor = (new DatabaseOpenHelper(RuntimeEnvironment.application))
                .getReadableDatabase()
                .query(DrillsTable.TABLE_DRILLS, DrillsTable.ALL_DRILL_COLUMNS,
                        null, null, null, null, null);

        assertTrue(cursor.moveToNext());
        assertCursorDataEqualsDrill(cursor, TEST_DRILL);
        cursor.close();
    }

    @Test
    public void canDeleteDrillFromDatabaseUsingContentProvider() {
        insertDrillToDatabase();

        String selectedDrill = DrillsTable.DRILL_ID + " = " + 1;
        RuntimeEnvironment.application.getContentResolver()
                .delete(CONTENT_URI_DRILLS, selectedDrill, null);

        assertFalse(getDatabaseContentProvider()
                .database
                .rawQuery("SELECT * FROM drills", null)
                .moveToFirst());
    }

    @Test
    public void canUpdateDrillFromDatabaseUsingContentProvider() {
        insertDrillToDatabase();

        String selectedDrill = DrillsTable.DRILL_ID + " = " + TEST_DRILL.getDrillId();
        ContentResolver contentResolver = RuntimeEnvironment.application.getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI_DRILLS, DrillsTable.ALL_DRILL_COLUMNS,
                selectedDrill, null, null);

        assertNotNull(cursor);
        assertCursorDataEqualsDrill(cursor, TEST_DRILL);

        Drill updatedDrill = new Drill(
                "Pass & Pass Back (Left Layup)",
                R.drawable.ic_fitness_center_white_24dp,
                Drill.PASSING_ARRAY);
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(DrillsTable.COLUMN_TITLE, updatedDrill.getTitle());
        updatedValues.put(DrillsTable.COLUMN_IMAGE_ID, updatedDrill.getImageId());
        updatedValues.put(DrillsTable.COLUMN_CATEGORY, getArrayAsString(updatedDrill.getCategory()));
        contentResolver.update(CONTENT_URI_DRILLS, updatedValues, selectedDrill, null);
        updatedDrill.setDrillId(TEST_DRILL.getDrillId());

        cursor = contentResolver.query(CONTENT_URI_DRILLS,
                DrillsTable.ALL_DRILL_COLUMNS, selectedDrill, null, null);

        assertNotNull(cursor);
        assertCursorDataEqualsDrill(cursor, updatedDrill);
        cursor.close();
    }

    @Test
    public void canQueryDatabaseForLogUsingContentProvider() {

    }

    @Test
    public void canInsertLogIntoDatabaseUsingContentProvider() {
        insertDrillToDatabase();
        insertLogToDatabase();

        Cursor cursor = (new DatabaseOpenHelper(RuntimeEnvironment.application))
                .getReadableDatabase()
                .query(LogsTable.TABLE_LOGS, LogsTable.ALL_LOG_COLUMNS,
                        null, null, null, null, null);

        assertTrue(cursor.moveToFirst());
        assertCursorDataEqualsLog(cursor, TEST_SESSION_LOG);
        cursor.close();
    }

    @Test
    public void canDeleteLogFromDatabaseUsingContentProvider() {

    }

    @Test
    public void canUpdateLogFromDatabaseUsingContentProvider() {

    }

    private DatabaseContentProvider getDatabaseContentProvider() {
        ContentResolver contentResolver = RuntimeEnvironment.application.getContentResolver();
        ContentProviderClient contentProviderClient = contentResolver
                .acquireContentProviderClient(DatabaseContentProvider.CONTENT_URI_DRILLS);
        assertNotNull(contentProviderClient);
        return (DatabaseContentProvider)
                contentProviderClient.getLocalContentProvider();
    }

    private void insertDrillToDatabase() {
        Drill drill = TEST_DRILL;
        ContentValues drillValues = new ContentValues();
        drillValues.put(DrillsTable.COLUMN_TITLE, drill.getTitle());
        drillValues.put(DrillsTable.COLUMN_IMAGE_ID, drill.getImageId());
        drillValues.put(DrillsTable.COLUMN_CATEGORY, getArrayAsString(drill.getCategory()));
        Uri uri = RuntimeEnvironment.application.getContentResolver()
                .insert(CONTENT_URI_DRILLS, drillValues);
        if (uri != null) { drill.setDrillId(Integer.parseInt(uri.getLastPathSegment())); }
    }

    private void assertCursorDataEqualsDrill(Cursor cursor, Drill drill) {
        assertTrue(cursor.moveToFirst());
        assertEquals(DrillsTable.ALL_DRILL_COLUMNS.length, cursor.getColumnCount());
        assertEquals(1, cursor.getCount());
        assertEquals(drill.getDrillId(),
                cursor.getInt(cursor.getColumnIndex(DrillsTable.DRILL_ID)));
        assertEquals(drill.getTitle(),
                cursor.getString(cursor.getColumnIndex(DrillsTable.COLUMN_TITLE)));
        assertEquals(drill.getImageId(),
                cursor.getInt(cursor.getColumnIndex(DrillsTable.COLUMN_IMAGE_ID)));
        assertThat(drill.getCategory(), is(getStringAsArray(cursor.getString(
                cursor.getColumnIndex(DrillsTable.COLUMN_CATEGORY)))));
    }

    private void insertLogToDatabase() {
        ContentValues logValues = new ContentValues();
        logValues.put(LogsTable.COLUMN_DATE, TEST_SESSION_LOG.getSessionDate().getTime());
        logValues.put(LogsTable.COLUMN_LENGTH, TEST_SESSION_LOG.getSessionLength().getTime());
        logValues.put(LogsTable.COLUMN_GOAL, TEST_SESSION_LOG.getSessionGoal());
        logValues.put(LogsTable.COLUMN_ACTIVE_WORK, TEST_SESSION_LOG.getActiveWork().getTime());
        logValues.put(LogsTable.COLUMN_REST_TIME, TEST_SESSION_LOG.getRestTime().getTime());
        logValues.put(LogsTable.COLUMN_SETS_COMPLETED, TEST_SESSION_LOG.getSetsCompleted());
        logValues.put(LogsTable.COLUMN_REPS_COMPLETED, TEST_SESSION_LOG.getRepsCompleted());
        logValues.put(LogsTable.COLUMN_SUCCESS, TEST_SESSION_LOG.getSuccessRate());
        logValues.put(LogsTable.COLUMN_DRILL, 1);
        RuntimeEnvironment.application.getContentResolver().insert(CONTENT_URI_LOGS, logValues);
    }

    private void assertCursorDataEqualsLog(Cursor cursor, SessionLog sessionLog) {
        assertEquals(LogsTable.ALL_LOG_COLUMNS.length, cursor.getColumnCount());
        assertEquals(1, cursor.getCount());
        assertEquals(sessionLog.getSessionDate().getTime(),
                cursor.getLong(cursor.getColumnIndex(LogsTable.COLUMN_DATE)));
        assertEquals(sessionLog.getSessionLength().getTime(),
                cursor.getLong(cursor.getColumnIndex(LogsTable.COLUMN_LENGTH)));
        assertEquals(sessionLog.getSessionGoal(),
                cursor.getString(cursor.getColumnIndex(LogsTable.COLUMN_GOAL)));
        assertEquals(sessionLog.getActiveWork().getTime(),
                cursor.getLong(cursor.getColumnIndex(LogsTable.COLUMN_ACTIVE_WORK)));
        assertEquals(sessionLog.getRestTime().getTime(),
                cursor.getLong(cursor.getColumnIndex(LogsTable.COLUMN_REST_TIME)));
        assertEquals(sessionLog.getSetsCompleted(),
                cursor.getInt(cursor.getColumnIndex(LogsTable.COLUMN_SETS_COMPLETED)));
        assertEquals(sessionLog.getRepsCompleted(),
                cursor.getInt(cursor.getColumnIndex(LogsTable.COLUMN_REPS_COMPLETED)));
        assertEquals(sessionLog.getSuccessRate(),
                cursor.getDouble(cursor.getColumnIndex(LogsTable.COLUMN_SUCCESS)));
        assertEquals(1,
                cursor.getInt(cursor.getColumnIndex(LogsTable.COLUMN_DRILL)));
    }

}