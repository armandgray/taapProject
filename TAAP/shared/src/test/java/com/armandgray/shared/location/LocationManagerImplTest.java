package com.armandgray.shared.location;

import android.content.Context;

import com.armandgray.shared.application.TAAPAppComponent;
import com.google.android.gms.common.GoogleApiAvailability;

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

public class LocationManagerImplTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Context mockContext;

    @Mock
    private GoogleApiAvailability mockGoogleApiAvailability;
    
    @Mock
    private TAAPAppComponent mockAppComponent;

    @Mock
    private LocationManager.Component mockComponent;

    @Mock
    private LocationManager.Component.Builder mockBuilder;
    
    private LocationManagerImpl testLocationManager;
    
    @Before
    public void setUp() {
        testLocationManager = new LocationManagerImpl();
        testLocationManager.googleApiAvailability = mockGoogleApiAvailability;
        testLocationManager.context = mockContext;

        Mockito.when(mockAppComponent.locationBuilder()).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.locationManager(testLocationManager)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.managerModule(Mockito.any(LocationManager.ManagerModule.class)))
                .thenReturn(mockBuilder);
        Mockito.when(mockBuilder.build()).thenReturn(mockComponent);
    }

    @Test
    public void testInject() {
        testLocationManager.inject(mockAppComponent);

        Mockito.verify(mockAppComponent, Mockito.times(1)).locationBuilder();
        Mockito.verify(mockBuilder, Mockito.times(1)).locationManager(testLocationManager);
        Mockito.verify(mockBuilder, Mockito.times(1))
                .managerModule(Mockito.any(LocationManager.ManagerModule.class));
        Mockito.verify(mockBuilder, Mockito.times(1)).build();
        Mockito.verify(mockComponent, Mockito.times(1)).inject(testLocationManager);
    }

    @Ignore
    @Test
    public void testGetAvailability() {
    }

    @Test
    public void testToString() {
        Assert.assertThat(testLocationManager.toString(),
                is("LocationManagerImpl@" + Integer.toHexString(testLocationManager.hashCode())));
    }

    @After
    public void tearDown() {
        testLocationManager = null;
    }
}