package com.armandgray.shared.sensors;

import android.hardware.SensorEvent;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LinearAccelerationGestureDetectorTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private LinearAccelerationGestureDetector.LinearAccelerationGestureListener mockListener;

    @Mock
    private SensorEvent mockSensorEvent;

    private LinearAccelerationGestureDetector testGestureDetector;
    private LinearAccelerationGestureDetector.LinearAccelerationEvent testEvent;

    @Before
    public void setUp() {
        testGestureDetector = new LinearAccelerationGestureDetector(mockListener);
    }

    @Ignore
    @Test
    public void testOnSensorEvent() {
    }

    /**
     * Inner Class - LinearAccelerationGestureListener
     */

    @Ignore
    @Test
    public void testLinearAccelerationEventConstructor_ThrowsException() {

    }

    /**
     * Inner Class - LinearAccelerationGestureListener
     */

    @Test
    public void testLinearAccelerationGestureListener() {
        LinearAccelerationGestureDetector.LinearAccelerationGestureListener testListener =
                new LinearAccelerationGestureDetector.LinearAccelerationGestureListener() {

                    @Override
                    public boolean onHorizontalFlingAway() {
                        return false;
                    }
                };

        Assert.assertThat(testListener, is(notNullValue()));
    }

    @After
    public void tearDown() {
    }
}
