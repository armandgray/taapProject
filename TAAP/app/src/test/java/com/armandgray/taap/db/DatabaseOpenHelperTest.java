package com.armandgray.taap.db;

import android.database.sqlite.SQLiteOpenHelper;

import com.armandgray.taap.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DatabaseOpenHelperTest {

    @Test
    public void doesExtendSQLiteOpenHelper() throws Exception {
        SQLiteOpenHelper databaseOpenHelper = new DatabaseOpenHelper(null);
        assertNotNull(databaseOpenHelper);
    }
}