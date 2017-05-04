package com.armandgray.taap.db;

import android.database.sqlite.SQLiteOpenHelper;

import com.armandgray.taap.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DatabaseOpenHelperTest {

    private static final String TAAP_DB = "taap.db";

    @Test
    public void doesExtendSQLiteOpenHelper() throws Exception {
        SQLiteOpenHelper databaseOpenHelper = new DatabaseOpenHelper(null);
        assertNotNull(databaseOpenHelper);
    }

    @Test
    public void doesSetDatabaseName_TestConstructor() {
        ShadowApplication context = Shadows.shadowOf(RuntimeEnvironment.application);
        DatabaseOpenHelper dbHelper = new DatabaseOpenHelper(context.getApplicationContext());
        assertEquals(TAAP_DB, dbHelper.getDatabaseName());
    }
}