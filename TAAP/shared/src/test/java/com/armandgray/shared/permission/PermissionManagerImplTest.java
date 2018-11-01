package com.armandgray.shared.permission;

import android.content.Context;
import android.content.pm.PackageManager;

import com.armandgray.shared.application.TAAPAppComponent;
import com.armandgray.shared.db.DatabaseManager;
import com.armandgray.shared.db.SharedPreferencesDao;
import com.armandgray.shared.rx.SchedulerProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class PermissionManagerImplTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private TAAPAppComponent mockAppComponent;

    @Mock
    private PermissionManager.Component mockComponent;

    @Mock
    private PermissionManager.Component.Builder mockBuilder;

    @Mock
    private Context mockContext;

    @Mock
    private SchedulerProvider mockSchedulers;

    @Mock
    private DatabaseManager mockDatabaseManager;

    @Mock
    private SharedPreferencesDao mockSharedPreferencesDao;

    private PermissionManagerImpl testPermissionManager;
    private boolean testRationaleResult;

    @Before
    public void setUp() {
        testPermissionManager = new PermissionManagerImpl();
        testPermissionManager.context = mockContext;
        testPermissionManager.schedulers = mockSchedulers;
        testPermissionManager.databaseManager = mockDatabaseManager;

        Mockito.when(mockAppComponent.permissionBuilder()).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.permissionManager(testPermissionManager)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.managerModule(Mockito.any(PermissionManager.ManagerModule.class)))
                .thenReturn(mockBuilder);
        Mockito.when(mockBuilder.build()).thenReturn(mockComponent);

        testRationaleResult = false;
    }

    @Test
    public void testInject() {
        testPermissionManager.inject(mockAppComponent);

        Mockito.verify(mockAppComponent, Mockito.times(1)).permissionBuilder();
        Mockito.verify(mockBuilder, Mockito.times(1)).permissionManager(testPermissionManager);
        Mockito.verify(mockBuilder, Mockito.times(1))
                .managerModule(Mockito.any(PermissionManager.ManagerModule.class));
        Mockito.verify(mockBuilder, Mockito.times(1)).build();
        Mockito.verify(mockComponent, Mockito.times(1)).inject(testPermissionManager);
    }

    @Test
    public void testHasPermission() {
        Mockito.when(mockContext.checkSelfPermission(DangerousPermission.LOCATION.getKey()))
                .thenReturn(PackageManager.PERMISSION_GRANTED);
        Assert.assertThat(testPermissionManager.hasPermission(DangerousPermission.LOCATION),
                is(true));
    }

    @Test
    public void testGetPermissionRequestObservable() {
        Assert.assertThat(testPermissionManager.getPermissionRequestObservable(),
                is(notNullValue()));
    }

    @Test
    public void testOnResult() {
        // Arrange
        Mockito.when(mockDatabaseManager.getSharedPreferencesDao())
                .thenReturn(mockSharedPreferencesDao);

        // Act
        testPermissionManager.onResult(DangerousPermission.LOCATION);

        // Assert
        Mockito.verify(mockDatabaseManager, Mockito.times(1)).getSharedPreferencesDao();
        Mockito.verify(mockSharedPreferencesDao, Mockito.times(1))
                .registerRequestedPermission(DangerousPermission.LOCATION);
    }

    @Test
    public void testGetRationaleRequestObservable() {
        Assert.assertThat(testPermissionManager.getRationaleRequestObservable(),
                is(notNullValue()));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testOnRationaleResponded() {
        testPermissionManager.rationaleResultSubject
                .subscribe(allow -> {
                    testRationaleResult = allow;
                    Assert.assertThat(allow, is(true));
                });

        testPermissionManager.onRationaleResponded(true);
        Assert.assertThat(testRationaleResult, is(true));
    }

    @Ignore
    @Test
    public void testUsePermission() {
    }

    @Test
    public void testToString() {
        Assert.assertThat(testPermissionManager.toString(), is("PermissionManagerImpl@"
                + Integer.toHexString(testPermissionManager.hashCode())));
    }

    @After
    public void tearDown() {
        testPermissionManager = null;
        testRationaleResult = false;
    }
}