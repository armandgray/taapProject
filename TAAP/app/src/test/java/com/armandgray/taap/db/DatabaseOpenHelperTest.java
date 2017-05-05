package com.armandgray.taap.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.armandgray.taap.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static com.armandgray.taap.db.DatabaseOpenHelper.DATABASE_NAME;
import static com.armandgray.taap.db.DrillsTable.ALL_DRILL_COLUMNS;
import static com.armandgray.taap.db.DrillsTable.TABLE_DRILLS;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DatabaseOpenHelperTest {

    @Test
    public void doesExtendSQLiteOpenHelper() throws Exception {
        SQLiteOpenHelper databaseOpenHelper = new DatabaseOpenHelper(null);
        assertNotNull(databaseOpenHelper);
    }

    @Test
    public void doesSetDatabaseName_TestConstructor() {
        ShadowApplication context = Shadows.shadowOf(RuntimeEnvironment.application);
        DatabaseOpenHelper dbHelper = new DatabaseOpenHelper(context.getApplicationContext());
        assertEquals(DATABASE_NAME, dbHelper.getDatabaseName());
    }

    @Test
    public void canCreateTable_Drill() {
        DatabaseOpenHelper databaseOpenHelper =
                new DatabaseOpenHelper(RuntimeEnvironment.application);
        SQLiteDatabase database = databaseOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(TABLE_DRILLS, ALL_DRILL_COLUMNS, "", null, null, null, null);
        assertNotNull(cursor);
        cursor.close();
    }

}