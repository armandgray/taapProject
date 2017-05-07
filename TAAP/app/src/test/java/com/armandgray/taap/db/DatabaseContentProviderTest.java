package com.armandgray.taap.db;

import android.content.ContentProvider;
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
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

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
        ContentProvider databaseContentProvider = new DatabaseContentProvider();
        assertNotNull(databaseContentProvider);
    }

    @Test
    public void doesAssignWritableDatabase_TestOnCreate() {
        // TODO add test
//        assertTrue(contentProvider.onCreate());
//        assertNotNull(contentProvider.database);
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
        values.put(DrillsTable.COLUMN_CATEGORY, drill.getCategory()[0]);

        DatabaseOpenHelper databaseOpenHelper =
                new DatabaseOpenHelper(RuntimeEnvironment.application);
        DatabaseContentProvider contentProvider = new DatabaseContentProvider();
        contentProvider.database = databaseOpenHelper.getWritableDatabase();

        contentProvider.insert(CONTENT_URI_DRILLS, values);
        Cursor cursor = databaseOpenHelper.getReadableDatabase()
                .query(DrillsTable.TABLE_DRILLS, DrillsTable.ALL_DRILL_COLUMNS,
                        null, null, null, null, null);

        assertTrue(cursor.moveToNext());
        assertEquals(DrillsTable.ALL_DRILL_COLUMNS.length, cursor.getColumnCount());
        assertEquals(1, cursor.getCount());
        assertEquals(drill.getTitle(),
                cursor.getString(cursor.getColumnIndex(DrillsTable.COLUMN_TITLE)));
        assertEquals(drill.getImageId(),
                cursor.getInt(cursor.getColumnIndex(DrillsTable.COLUMN_IMAGE_ID)));
        assertEquals(drill.getCategory()[0],
                cursor.getString(cursor.getColumnIndex(DrillsTable.COLUMN_CATEGORY)));
        cursor.close();
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        contentProvider = null;
    }

}