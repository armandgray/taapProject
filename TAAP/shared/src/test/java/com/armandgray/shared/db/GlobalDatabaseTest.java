package com.armandgray.shared.db;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import io.reactivex.Single;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GlobalDatabaseTest {

    @Test
    public void testDrillDatabase() {
        GlobalDatabase database = new GlobalDatabase() {

            @NonNull
            @Override
            protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
                return null;
            }

            @NonNull
            @Override
            protected InvalidationTracker createInvalidationTracker() {
                return null;
            }

            @Override
            public void clearAllTables() {

            }

            @Override
            public DrillDao drillDao() {
                return null;
            }

            @Override
            public PerformanceDao performanceDao() {
                return null;
            }

            @Override
            public SettingsDao settingsDao() {
                return null;
            }
        };

        Assert.assertThat(database, is(notNullValue()));
    }

    @Ignore
    @Test
    public void testDatabaseName() {
        Assert.assertThat(GlobalDatabase.DATABASE_NAME, is("global-database"));
    }

    @Test
    public void testBaseDao() {

        GlobalDatabase.BaseDao dao = new GlobalDatabase.BaseDao() {
            @Override
            public Single<List<Long>> insert(Object[] arr) {
                return null;
            }

            @Override
            public Single<Integer> update(Object[] arr) {
                return null;
            }

            @Override
            public Single<Integer> delete(Object[] arr) {
                return null;
            }
        };

        Assert.assertThat(dao, is(notNullValue()));
    }
}
