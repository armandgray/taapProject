package com.armandgray.taap.db;

import android.database.sqlite.SQLiteOpenHelper;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

public class DatabaseOpenHelperTest {

    @Test
    public void doesExtendSQLiteOpenHelper() throws Exception {
        SQLiteOpenHelper databaseOpenHelper = new DatabaseOpenHelper(null);
        assertNotNull(databaseOpenHelper);
    }
}