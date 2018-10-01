package com.armandgray.taap.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.armandgray.taap.db.DatabaseOpenHelper.DATABASE_NAME;
import static com.armandgray.taap.db.DrillsTable.ALL_DRILL_COLUMNS;
import static com.armandgray.taap.db.DrillsTable.TABLE_DRILLS;
import static com.armandgray.taap.db.LogsTable.ALL_LOG_COLUMNS;
import static com.armandgray.taap.db.LogsTable.TABLE_LOGS;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DatabaseOpenHelperTest {

    @Test @Ignore
    public void doesExtendSQLiteOpenHelper() throws Exception {
        SQLiteOpenHelper databaseOpenHelper = new DatabaseOpenHelper(null);
        assertNotNull(databaseOpenHelper);
    }

    @Test @Ignore
    public void doesSetDatabaseName_TestConstructor() {
        DatabaseOpenHelper dbHelper = new DatabaseOpenHelper(RuntimeEnvironment.application);
        assertEquals(DATABASE_NAME, dbHelper.getDatabaseName());
    }

    @Test @Ignore
    public void canCreateTable_Drills() {
        DatabaseOpenHelper databaseOpenHelper =
                new DatabaseOpenHelper(RuntimeEnvironment.application);
        SQLiteDatabase database = databaseOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(TABLE_DRILLS, ALL_DRILL_COLUMNS, "", null, null, null, null);
        assertNotNull(cursor);
        cursor.close();
    }

    @Test @Ignore
    public void canCreateTable_Logs() {
        DatabaseOpenHelper databaseOpenHelper =
                new DatabaseOpenHelper(RuntimeEnvironment.application);
        SQLiteDatabase database = databaseOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(TABLE_LOGS, ALL_LOG_COLUMNS, "", null, null, null, null);
        assertNotNull(cursor);
        cursor.close();
    }

}