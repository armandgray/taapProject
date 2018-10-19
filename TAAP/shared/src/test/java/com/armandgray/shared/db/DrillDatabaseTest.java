package com.armandgray.shared.db;

import org.junit.Assert;
import org.junit.Test;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class DrillDatabaseTest {

    @Test
    public void testDrillDatabase() {
        DrillDatabase database = new DrillDatabase() {

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
        };

        Assert.assertThat(database, is(notNullValue()));
    }
}
