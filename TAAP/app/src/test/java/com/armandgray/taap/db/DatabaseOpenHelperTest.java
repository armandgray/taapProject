package com.armandgray.taap.db;

import android.database.sqlite.SQLiteOpenHelper;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

public class DatabaseOpenHelperTest {

    @Test
    public void doesImplementSQLiteOpenHelper() throws Exception {
        SQLiteOpenHelper databaseOpenHelper = new DatabaseOpenHelper();
        assertNotNull(databaseOpenHelper);
    }
}