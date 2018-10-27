package com.armandgray.shared.db;

import com.armandgray.shared.application.TAAPAppComponent;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.hamcrest.CoreMatchers.is;

public class DatabaseManagerImplTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    TAAPAppComponent mockAppComponent;

    @Mock
    DatabaseManager.Component mockComponent;

    @Mock
    DatabaseManager.Component.Builder mockBuilder;

    @Mock
    DrillDao.DrillDaoWrapper mockDrillDaoWrapper;

    @Mock
    PerformanceDao.PerformanceDaoWrapper mockPerformanceDaoWrapper;

    @Mock
    SettingsDao.SettingsDaoWrapper mockSettingsDaoWrapper;

    private DatabaseManagerImpl testDatabaseManager;

    @Before
    public void setUp() {
        testDatabaseManager = new DatabaseManagerImpl();
        testDatabaseManager.drillDaoWrapper = mockDrillDaoWrapper;
        testDatabaseManager.performanceDaoWrapper = mockPerformanceDaoWrapper;
        testDatabaseManager.settingsDaoWrapper = mockSettingsDaoWrapper;

        Mockito.when(mockAppComponent.databaseBuilder()).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.databaseManager(testDatabaseManager)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.managerModule(Mockito.any(DatabaseManager.ManagerModule.class)))
                .thenReturn(mockBuilder);
        Mockito.when(mockBuilder.build()).thenReturn(mockComponent);
    }

    @Test
    public void testInject() {
        testDatabaseManager.inject(mockAppComponent);

        Mockito.verify(mockAppComponent, Mockito.times(1)).databaseBuilder();
        Mockito.verify(mockBuilder, Mockito.times(1)).databaseManager(testDatabaseManager);
        Mockito.verify(mockBuilder, Mockito.times(1))
                .managerModule(Mockito.any(DatabaseManager.ManagerModule.class));
        Mockito.verify(mockBuilder, Mockito.times(1)).build();
        Mockito.verify(mockComponent, Mockito.times(1)).inject(testDatabaseManager);
    }

    @Test
    public void testGetDrillDao() {
        Assert.assertThat(testDatabaseManager.getDrillDao(), is(mockDrillDaoWrapper));
    }

    @Test
    public void testGetPerformanceDao() {
        Assert.assertThat(testDatabaseManager.getPerformanceDao(), is(mockPerformanceDaoWrapper));
    }

    @Test
    public void testGetSettingsDao() {
        Assert.assertThat(testDatabaseManager.getSettingsDao(), is(mockSettingsDaoWrapper));
    }

    @Test
    public void testToString() {
        Assert.assertThat(testDatabaseManager.toString(),
                is("DatabaseManagerImpl@" + Integer.toHexString(testDatabaseManager.hashCode())));
    }

    @After
    public void tearDown() {
        testDatabaseManager = null;
    }
}