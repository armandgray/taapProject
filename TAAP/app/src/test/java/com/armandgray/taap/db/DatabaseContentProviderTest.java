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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

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

    private DatabaseContentProvider contentProvider;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
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
        while ( !cursor.isAfterLast() ) {
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
        Drill drill = new Drill(
                "5 Spots Shooting (Mid-Range)",
                R.drawable.ic_account_multiple_outline_white_48dp,
                Drill.SHOOTING_ARRAY);
        ContentValues values = new ContentValues();
        values.put(DrillsTable.COLUMN_TITLE, drill.getTitle());
        values.put(DrillsTable.COLUMN_IMAGE_ID, drill.getImageId());
        values.put(DrillsTable.COLUMN_CATEGORY, getArrayAsString(drill.getCategory()));
        RuntimeEnvironment.application.getContentResolver().insert(CONTENT_URI_DRILLS, values);

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
        Drill drill = new Drill(
                "5 Spots Shooting (Mid-Range)",
                R.drawable.ic_account_multiple_outline_white_48dp,
                Drill.SHOOTING_ARRAY);
        ContentValues values = new ContentValues();
        values.put(DrillsTable.COLUMN_TITLE, drill.getTitle());
        values.put(DrillsTable.COLUMN_IMAGE_ID, drill.getImageId());
        values.put(DrillsTable.COLUMN_CATEGORY, getArrayAsString(drill.getCategory()));
        RuntimeEnvironment.application.getContentResolver().insert(CONTENT_URI_DRILLS, values);

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

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        contentProvider = null;
    }

}