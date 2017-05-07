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

import org.junit.After;
import org.junit.Before;
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
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DatabaseContentProviderTest {

    private static final long TIME_IN_MILLIS = 1494179392802L;
    private static final SessionLog SESSION_LOG = new SessionLog.Builder()
            .sessionLength(new Date(TIME_IN_MILLIS))
            .sessionGoal("")
            .activeWork(new Date(TIME_IN_MILLIS + 555555))
            .restTime(new Date(TIME_IN_MILLIS + 111111))
            .setsCompleted(4)
            .repsCompleted(3)
            .successRate(0.23)
            .successRecord(0.55)
            .drill(getTestDrill())
            .create();

    private DatabaseContentProvider contentProvider;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        System.out.println(TIME_IN_MILLIS);
        contentProvider = new DatabaseContentProvider();
    }

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
        ContentProvider databaseContentProvider = contentProvider;
        assertNotNull(databaseContentProvider);
    }

    @Test
    public void doesAssignWritableDatabase_TestOnCreate() {
        ContentResolver contentResolver = RuntimeEnvironment.application.getContentResolver();
        ContentProviderClient contentProviderClient = contentResolver
                .acquireContentProviderClient(DatabaseContentProvider.CONTENT_URI_DRILLS);
        assertNotNull(contentProviderClient);
        DatabaseContentProvider contentProvider = (DatabaseContentProvider)
                contentProviderClient.getLocalContentProvider();

        assertNotNull(contentProvider);
        assertNotNull(contentProvider.database);
    }

    @Test
    public void doesAssignWritableDatabase_WithDrillsAndLogsTables_TestOnCreate() {
        ContentResolver contentResolver = RuntimeEnvironment.application.getContentResolver();
        ContentProviderClient contentProviderClient = contentResolver
                .acquireContentProviderClient(DatabaseContentProvider.CONTENT_URI_DRILLS);
        assertNotNull(contentProviderClient);
        DatabaseContentProvider contentProvider = (DatabaseContentProvider)
                contentProviderClient.getLocalContentProvider();

        assertNotNull(contentProvider);
        Cursor cursor = contentProvider.database
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
        Drill drill = new Drill(
                "5 Spots Shooting (Mid-Range)",
                R.drawable.ic_account_multiple_outline_white_48dp,
                Drill.SHOOTING_ARRAY);
        ContentValues values = new ContentValues();
        values.put(DrillsTable.COLUMN_TITLE, drill.getTitle());
        values.put(DrillsTable.COLUMN_IMAGE_ID, drill.getImageId());
        values.put(DrillsTable.COLUMN_CATEGORY, getArrayAsString(drill.getCategory()));
        ContentResolver contentResolver = RuntimeEnvironment.application.getContentResolver();
        contentResolver.insert(CONTENT_URI_DRILLS, values);

        String selectedDrill = DrillsTable.DRILL_ID + " = " + 1;
        Cursor cursor = shadowOf(contentResolver).query(CONTENT_URI_DRILLS,
                DrillsTable.ALL_DRILL_COLUMNS, selectedDrill, null, null, null);

        assertNotNull(cursor);
        assertTrue(cursor.moveToFirst());
        assertEquals(DrillsTable.ALL_DRILL_COLUMNS.length, cursor.getColumnCount());
        assertEquals(1, cursor.getCount());
        assertEquals(drill.getTitle(),
                cursor.getString(cursor.getColumnIndex(DrillsTable.COLUMN_TITLE)));
        assertEquals(drill.getImageId(),
                cursor.getInt(cursor.getColumnIndex(DrillsTable.COLUMN_IMAGE_ID)));
        assertThat(drill.getCategory(), is(getStringAsArray(cursor.getString(
                cursor.getColumnIndex(DrillsTable.COLUMN_CATEGORY)))));
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
        Drill drill = getTestDrill();
        insertDrillToDatabase();

        Cursor cursor = (new DatabaseOpenHelper(RuntimeEnvironment.application))
                .getReadableDatabase()
                .query(DrillsTable.TABLE_DRILLS, DrillsTable.ALL_DRILL_COLUMNS,
                        null, null, null, null, null);

        assertTrue(cursor.moveToNext());
        assertEquals(DrillsTable.ALL_DRILL_COLUMNS.length, cursor.getColumnCount());
        assertEquals(1, cursor.getCount());
        assertEquals(drill.getTitle(),
                cursor.getString(cursor.getColumnIndex(DrillsTable.COLUMN_TITLE)));
        assertEquals(drill.getImageId(),
                cursor.getInt(cursor.getColumnIndex(DrillsTable.COLUMN_IMAGE_ID)));
        assertEquals(getArrayAsString(drill.getCategory()),
                cursor.getString(cursor.getColumnIndex(DrillsTable.COLUMN_CATEGORY)));
        cursor.close();
    }

    @Test
    public void canDeleteDrillFromDatabaseUsingContentProvider() {
        Drill drill = new Drill(
                "5 Spots Shooting (Mid-Range)",
                R.drawable.ic_account_multiple_outline_white_48dp,
                Drill.SHOOTING_ARRAY);
        ContentValues values = new ContentValues();
        values.put(DrillsTable.COLUMN_TITLE, drill.getTitle());
        values.put(DrillsTable.COLUMN_IMAGE_ID, drill.getImageId());
        values.put(DrillsTable.COLUMN_CATEGORY, getArrayAsString(drill.getCategory()));
        ContentResolver contentResolver = RuntimeEnvironment.application.getContentResolver();
        contentResolver.insert(CONTENT_URI_DRILLS, values);

        String selectedDrill = DrillsTable.DRILL_ID + " = " + 1;
        contentResolver.delete(CONTENT_URI_DRILLS, selectedDrill, null);

        ContentProviderClient contentProviderClient = contentResolver
                .acquireContentProviderClient(DatabaseContentProvider.CONTENT_URI_DRILLS);
        assertNotNull(contentProviderClient);
        DatabaseContentProvider contentProvider = (DatabaseContentProvider)
                contentProviderClient.getLocalContentProvider();
        assertNotNull(contentProvider);

        Cursor cursor = contentProvider.database.rawQuery("SELECT * FROM drills", null);
        assertFalse(cursor.moveToFirst());
        cursor.close();
    }

    @Test
    public void canUpdateDrillFromDatabaseUsingContentProvider() {
        Drill drill = new Drill(
                "5 Spots Shooting (Mid-Range)",
                R.drawable.ic_account_multiple_outline_white_48dp,
                Drill.SHOOTING_ARRAY);
        ContentValues values = new ContentValues();
        values.put(DrillsTable.COLUMN_TITLE, drill.getTitle());
        values.put(DrillsTable.COLUMN_IMAGE_ID, drill.getImageId());
        values.put(DrillsTable.COLUMN_CATEGORY, getArrayAsString(drill.getCategory()));
        ContentResolver contentResolver = RuntimeEnvironment.application.getContentResolver();
        contentResolver.insert(CONTENT_URI_DRILLS, values);

        String selectedDrill = DrillsTable.DRILL_ID + " = " + 1;
        Cursor cursor = contentResolver.query(CONTENT_URI_DRILLS,
                DrillsTable.ALL_DRILL_COLUMNS, selectedDrill, null, null);

        assertNotNull(cursor);
        assertTrue(cursor.moveToFirst());
        assertEquals(DrillsTable.ALL_DRILL_COLUMNS.length, cursor.getColumnCount());
        assertEquals(1, cursor.getCount());
        assertEquals(drill.getTitle(),
                cursor.getString(cursor.getColumnIndex(DrillsTable.COLUMN_TITLE)));
        assertEquals(drill.getImageId(),
                cursor.getInt(cursor.getColumnIndex(DrillsTable.COLUMN_IMAGE_ID)));
        assertEquals(getArrayAsString(drill.getCategory()),
                cursor.getString(cursor.getColumnIndex(DrillsTable.COLUMN_CATEGORY)));

        Drill updatedDrill = new Drill(
                "Pass & Pass Back (Left Layup)",
                R.drawable.ic_fitness_center_white_24dp,
                Drill.PASSING_ARRAY);
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(DrillsTable.COLUMN_TITLE, updatedDrill.getTitle());
        updatedValues.put(DrillsTable.COLUMN_IMAGE_ID, updatedDrill.getImageId());
        updatedValues.put(DrillsTable.COLUMN_CATEGORY, getArrayAsString(updatedDrill.getCategory()));

        contentResolver.update(CONTENT_URI_DRILLS, updatedValues, selectedDrill, null);
        cursor = contentResolver.query(CONTENT_URI_DRILLS,
                DrillsTable.ALL_DRILL_COLUMNS, selectedDrill, null, null);

        assertNotNull(cursor);
        assertTrue(cursor.moveToFirst());
        assertEquals(DrillsTable.ALL_DRILL_COLUMNS.length, cursor.getColumnCount());
        assertEquals(1, cursor.getCount());
        assertEquals(updatedDrill.getTitle(),
                cursor.getString(cursor.getColumnIndex(DrillsTable.COLUMN_TITLE)));
        assertEquals(updatedDrill.getImageId(),
                cursor.getInt(cursor.getColumnIndex(DrillsTable.COLUMN_IMAGE_ID)));
        assertEquals(getArrayAsString(updatedDrill.getCategory()),
                cursor.getString(cursor.getColumnIndex(DrillsTable.COLUMN_CATEGORY)));
        cursor.close();
    }

    @Test
    public void canQueryDatabaseForLogUsingContentProvider() {
        Drill drill = new Drill(
                "5 Spots Shooting (Mid-Range)",
                R.drawable.ic_account_multiple_outline_white_48dp,
                Drill.SHOOTING_ARRAY);
        ContentValues values = new ContentValues();
        values.put(DrillsTable.COLUMN_TITLE, drill.getTitle());
        values.put(DrillsTable.COLUMN_IMAGE_ID, drill.getImageId());
        values.put(DrillsTable.COLUMN_CATEGORY, getArrayAsString(drill.getCategory()));
        ContentResolver contentResolver = RuntimeEnvironment.application.getContentResolver();
        contentResolver.insert(CONTENT_URI_DRILLS, values);

        String selectedDrill = DrillsTable.DRILL_ID + " = " + 1;
        Cursor cursor = shadowOf(contentResolver).query(CONTENT_URI_DRILLS,
                DrillsTable.ALL_DRILL_COLUMNS, selectedDrill, null, null, null);

        assertNotNull(cursor);
        assertTrue(cursor.moveToFirst());
        assertEquals(DrillsTable.ALL_DRILL_COLUMNS.length, cursor.getColumnCount());
        assertEquals(1, cursor.getCount());
        assertEquals(drill.getTitle(),
                cursor.getString(cursor.getColumnIndex(DrillsTable.COLUMN_TITLE)));
        assertEquals(drill.getImageId(),
                cursor.getInt(cursor.getColumnIndex(DrillsTable.COLUMN_IMAGE_ID)));
        assertThat(drill.getCategory(), is(getStringAsArray(cursor.getString(
                cursor.getColumnIndex(DrillsTable.COLUMN_CATEGORY)))));
        cursor.close();
    }

    @Test
    public void canInsertLogIntoDatabaseUsingContentProvider() {
        Drill drill = getTestDrill();
        insertDrillToDatabase();
        insertLogToDatabase();

        Cursor cursor = (new DatabaseOpenHelper(RuntimeEnvironment.application))
                .getReadableDatabase()
                .query(LogsTable.TABLE_LOGS, LogsTable.ALL_LOG_COLUMNS,
                        null, null, null, null, null);

        assertTrue(cursor.moveToFirst());
        assertEquals(LogsTable.ALL_LOG_COLUMNS.length, cursor.getColumnCount());
        assertEquals(1, cursor.getCount());
        assertEquals(SESSION_LOG.getSessionDate().getTime(),
                cursor.getLong(cursor.getColumnIndex(LogsTable.COLUMN_DATE)));
        assertEquals(SESSION_LOG.getSessionLength().getTime(),
                cursor.getLong(cursor.getColumnIndex(LogsTable.COLUMN_LENGTH)));
        assertEquals(SESSION_LOG.getSessionGoal(),
                cursor.getString(cursor.getColumnIndex(LogsTable.COLUMN_GOAL)));
        assertEquals(SESSION_LOG.getActiveWork().getTime(),
                cursor.getLong(cursor.getColumnIndex(LogsTable.COLUMN_ACTIVE_WORK)));
        assertEquals(SESSION_LOG.getRestTime().getTime(),
                cursor.getLong(cursor.getColumnIndex(LogsTable.COLUMN_REST_TIME)));
        assertEquals(SESSION_LOG.getSetsCompleted(),
                cursor.getInt(cursor.getColumnIndex(LogsTable.COLUMN_SETS_COMPLETED)));
        assertEquals(SESSION_LOG.getRepsCompleted(),
                cursor.getInt(cursor.getColumnIndex(LogsTable.COLUMN_REPS_COMPLETED)));
        assertEquals(SESSION_LOG.getSuccessRate(),
                cursor.getDouble(cursor.getColumnIndex(LogsTable.COLUMN_SUCCESS)));
        assertEquals(1,
                cursor.getInt(cursor.getColumnIndex(LogsTable.COLUMN_DRILL)));
        cursor.close();
    }

    @Test
    public void canDeleteLogFromDatabaseUsingContentProvider() {
        Drill drill = new Drill(
                "5 Spots Shooting (Mid-Range)",
                R.drawable.ic_account_multiple_outline_white_48dp,
                Drill.SHOOTING_ARRAY);
        ContentValues values = new ContentValues();
        values.put(DrillsTable.COLUMN_TITLE, drill.getTitle());
        values.put(DrillsTable.COLUMN_IMAGE_ID, drill.getImageId());
        values.put(DrillsTable.COLUMN_CATEGORY, getArrayAsString(drill.getCategory()));
        ContentResolver contentResolver = RuntimeEnvironment.application.getContentResolver();
        contentResolver.insert(CONTENT_URI_DRILLS, values);

        String selectedDrill = DrillsTable.DRILL_ID + " = " + 1;
        contentResolver.delete(CONTENT_URI_DRILLS, selectedDrill, null);

        ContentProviderClient contentProviderClient = contentResolver
                .acquireContentProviderClient(DatabaseContentProvider.CONTENT_URI_DRILLS);
        assertNotNull(contentProviderClient);
        DatabaseContentProvider contentProvider = (DatabaseContentProvider)
                contentProviderClient.getLocalContentProvider();
        assertNotNull(contentProvider);

        Cursor cursor = contentProvider.database.rawQuery("SELECT * FROM drills", null);
        assertFalse(cursor.moveToFirst());
        cursor.close();
    }

    @Test
    public void canUpdateLogFromDatabaseUsingContentProvider() {
        Drill drill = new Drill(
                "5 Spots Shooting (Mid-Range)",
                R.drawable.ic_account_multiple_outline_white_48dp,
                Drill.SHOOTING_ARRAY);
        ContentValues values = new ContentValues();
        values.put(DrillsTable.COLUMN_TITLE, drill.getTitle());
        values.put(DrillsTable.COLUMN_IMAGE_ID, drill.getImageId());
        values.put(DrillsTable.COLUMN_CATEGORY, getArrayAsString(drill.getCategory()));
        ContentResolver contentResolver = RuntimeEnvironment.application.getContentResolver();
        contentResolver.insert(CONTENT_URI_DRILLS, values);

        String selectedDrill = DrillsTable.DRILL_ID + " = " + 1;
        Cursor cursor = contentResolver.query(CONTENT_URI_DRILLS,
                DrillsTable.ALL_DRILL_COLUMNS, selectedDrill, null, null);

        assertNotNull(cursor);
        assertTrue(cursor.moveToFirst());
        assertEquals(DrillsTable.ALL_DRILL_COLUMNS.length, cursor.getColumnCount());
        assertEquals(1, cursor.getCount());
        assertEquals(drill.getTitle(),
                cursor.getString(cursor.getColumnIndex(DrillsTable.COLUMN_TITLE)));
        assertEquals(drill.getImageId(),
                cursor.getInt(cursor.getColumnIndex(DrillsTable.COLUMN_IMAGE_ID)));
        assertEquals(getArrayAsString(drill.getCategory()),
                cursor.getString(cursor.getColumnIndex(DrillsTable.COLUMN_CATEGORY)));

        Drill updatedDrill = new Drill(
                "Pass & Pass Back (Left Layup)",
                R.drawable.ic_fitness_center_white_24dp,
                Drill.PASSING_ARRAY);
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(DrillsTable.COLUMN_TITLE, updatedDrill.getTitle());
        updatedValues.put(DrillsTable.COLUMN_IMAGE_ID, updatedDrill.getImageId());
        updatedValues.put(DrillsTable.COLUMN_CATEGORY, getArrayAsString(updatedDrill.getCategory()));

        contentResolver.update(CONTENT_URI_DRILLS, updatedValues, selectedDrill, null);
        cursor = contentResolver.query(CONTENT_URI_DRILLS,
                DrillsTable.ALL_DRILL_COLUMNS, selectedDrill, null, null);

        assertNotNull(cursor);
        assertTrue(cursor.moveToFirst());
        assertEquals(DrillsTable.ALL_DRILL_COLUMNS.length, cursor.getColumnCount());
        assertEquals(1, cursor.getCount());
        assertEquals(updatedDrill.getTitle(),
                cursor.getString(cursor.getColumnIndex(DrillsTable.COLUMN_TITLE)));
        assertEquals(updatedDrill.getImageId(),
                cursor.getInt(cursor.getColumnIndex(DrillsTable.COLUMN_IMAGE_ID)));
        assertEquals(getArrayAsString(updatedDrill.getCategory()),
                cursor.getString(cursor.getColumnIndex(DrillsTable.COLUMN_CATEGORY)));
        cursor.close();
    }

    private static Drill getTestDrill() {
        return new Drill(
                "5 Spots Shooting (Mid-Range)",
                R.drawable.ic_account_multiple_outline_white_48dp,
                Drill.SHOOTING_ARRAY);
    }

    private void insertDrillToDatabase() {
        Drill drill = getTestDrill();
        ContentValues drillValues = new ContentValues();
        drillValues.put(DrillsTable.COLUMN_TITLE, drill.getTitle());
        drillValues.put(DrillsTable.COLUMN_IMAGE_ID, drill.getImageId());
        drillValues.put(DrillsTable.COLUMN_CATEGORY, getArrayAsString(drill.getCategory()));
        RuntimeEnvironment.application.getContentResolver().insert(CONTENT_URI_DRILLS, drillValues);
    }

    private void insertLogToDatabase() {
        ContentValues logValues = new ContentValues();
        logValues.put(LogsTable.COLUMN_DATE, SESSION_LOG.getSessionDate().getTime());
        logValues.put(LogsTable.COLUMN_LENGTH, SESSION_LOG.getSessionLength().getTime());
        logValues.put(LogsTable.COLUMN_GOAL, SESSION_LOG.getSessionGoal());
        logValues.put(LogsTable.COLUMN_ACTIVE_WORK, SESSION_LOG.getActiveWork().getTime());
        logValues.put(LogsTable.COLUMN_REST_TIME, SESSION_LOG.getRestTime().getTime());
        logValues.put(LogsTable.COLUMN_SETS_COMPLETED, SESSION_LOG.getSessionGoal());
        logValues.put(LogsTable.COLUMN_REPS_COMPLETED, SESSION_LOG.getSessionGoal());
        logValues.put(LogsTable.COLUMN_SUCCESS, SESSION_LOG.getSuccessRate());
        logValues.put(LogsTable.COLUMN_DRILL, 1);
        RuntimeEnvironment.application.getContentResolver().insert(CONTENT_URI_LOGS, logValues);
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        contentProvider = null;
    }

}