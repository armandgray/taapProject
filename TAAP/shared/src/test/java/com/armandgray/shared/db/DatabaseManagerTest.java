package com.armandgray.shared.db;

import com.armandgray.shared.application.TAAPAppComponent;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class DatabaseManagerTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private DatabaseManager.Component mockDatabaseComponent;

    @Before
    public void setUp() {

    }

    @Test
    public void testDatabaseManager() {
        DatabaseManager manager = new DatabaseManager() {
            @Override
            public SharedPreferencesDao getSharedPreferencesDao() {
                return null;
            }

            @Override
            public DrillDao getDrillDao() {
                return null;
            }

            @Override
            public PerformanceDao getPerformanceDao() {
                return null;
            }

            @Override
            public SettingsDao getSettingsDao() {
                return null;
            }

            @Override
            public void inject(TAAPAppComponent parentComponent) {

            }
        };

        Assert.assertThat(manager, is(notNullValue()));
    }

    @Test
    public void testDatabaseManager_DefinesInterfaceTag() {
        Assert.assertThat(DatabaseManager.TAG, is(notNullValue()));
    }

    /**
     * Inner Class - Wrapper
     */

    @Test
    public void testStateEnum_Instances() {
        DatabaseManager.Wrapper[] values = DatabaseManager.Wrapper.values();
        Assert.assertThat(values.length, is(3));
        Assert.assertThat(Arrays.asList(values), containsInAnyOrder(
                DatabaseManager.Wrapper.CREATED,
                DatabaseManager.Wrapper.POPULATING,
                DatabaseManager.Wrapper.READY));
    }

    @Test
    public void testStateEnum_DatabaseComponent() {
        Assert.assertThat(DatabaseManager.Wrapper.databaseComponent(), is(nullValue()));
    }

    @Test
    public void testStateEnum_SetDatabaseComponent() {
        DatabaseManager.Wrapper.setDatabaseComponent(mockDatabaseComponent);
        Assert.assertThat(DatabaseManager.Wrapper.databaseComponent(), is(mockDatabaseComponent));
    }

    /**
     * Inner Class - Component
     */

    @Test
    public void testDatabaseManager_Component() {
        DatabaseManager.Component component = new DatabaseManager.Component() {
            @Override
            public void inject(DatabaseManagerImpl manager) {

            }

            @Override
            public void inject(SharedPreferencesDaoImpl impl) {

            }
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

    /**
     * Inner Class - ManagerModule
     */

    @Ignore
    @Test
    public void testManagerModule_ProvideSharedPreferencesDao() {
        // TODO implement test
    }

    @Ignore
    @Test
    public void testManagerModule_ProvideDatabase_ReturnsDatabase() {
        // TODO implement test
    }

    @Ignore
    @Test
    public void testManagerModule_ProvideDatabase_OnDatabaseLifecycleChanged() {
        // TODO implement test
    }

    @SuppressWarnings("ConstantConditions")
    @After
    public void tearDown() {
        DatabaseManager.Wrapper.setDatabaseComponent(null);
    }
}
