package com.armandgray.shared.db;

import com.armandgray.shared.application.TAAPAppComponent;

import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

public class DatabaseManagerTest {

    @Test
    public void testDatabaseManager() {
        DatabaseManager manager = new DatabaseManager() {
            @Override
            public DrillDao getDrillDao() {
                return null;
            }

            @Override
            public PerformanceDao getPerformanceDao() {
                return null;
            }

            @Override
            public void inject(TAAPAppComponent parentComponent) {

            }
        };

        Assert.assertThat(manager, is(notNullValue()));
    }

    @Test
    public void testDatabaseManager_DefinesStateEnum() {
        Assert.assertThat(DatabaseManager.State.CREATED, is(notNullValue()));
        Assert.assertThat(DatabaseManager.State.OPEN, is(notNullValue()));
        Assert.assertThat(DatabaseManager.State.POPULATED, is(notNullValue()));
    }

    @Test
    public void testDatabaseManager_DefinesInterfaceTag() {
        Assert.assertThat(DatabaseManager.TAG, is(notNullValue()));
    }

    @Test
    public void testDatabaseManager_Component() {
        DatabaseManager.Component component = manager -> {

        };
        DatabaseManager.Component.Builder builder = new DatabaseManager.Component.Builder() {
            @Override
            public DatabaseManager.Component.Builder databaseManager(DatabaseManager manager) {
                return null;
            }

            @Override
            public DatabaseManager.Component.Builder managerModule(
                    DatabaseManager.ManagerModule module) {
                return null;
            }

            @Override
            public DatabaseManager.Component build() {
                return null;
            }
        };

        Assert.assertThat(component, is(notNullValue()));
        Assert.assertThat(builder, is(notNullValue()));
    }

    @Test
    public void testDatabaseManager_Scope() {
        DatabaseManager.DatabaseScope scope = new DatabaseManager.DatabaseScope() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }
        };

        Assert.assertThat(scope, is(notNullValue()));
    }

    @Test
    public void testManagerModule_ProvideDatabase_ReturnsDatabase() {
        // TODO implement test
    }

    @Test
    public void testManagerModule_ProvideDatabase_OnDatabaseLifecycleChanged() {
        // TODO implement test
    }
}
