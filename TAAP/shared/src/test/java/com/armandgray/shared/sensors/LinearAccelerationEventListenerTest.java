package com.armandgray.shared.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

import com.armandgray.shared.rx.CountedSubjectWrapper;

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

public class LinearAccelerationEventListenerTest {

    private static final int TEST_ACCURACY = 12;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private CountedSubjectWrapper<LinearAccelerationAction> mockSensorEventSubject;

    @Mock
    private CountedSubjectWrapper<Integer> mockAccuracyEventSubject;

    @Mock
    private LinearAccelerationGestureDetector mockGestureDetector;

    @Mock
    private SensorEvent mockSensorEvent;

    @Mock
    private Sensor mockSensor;

    private LinearAccelerationEventListener testEventListener;

    @Before
    public void setUp() {
        testEventListener = new LinearAccelerationEventListener();
        testEventListener.sensorEventSubject = mockSensorEventSubject;
        testEventListener.accuracyEventSubject = mockAccuracyEventSubject;
        testEventListener.gestureDetector = mockGestureDetector;
    }

    @Test
    public void testOnSensorChanged() {
        testEventListener.onSensorChanged(mockSensorEvent);
        Mockito.verify(mockGestureDetector, Mockito.times(1)).onSensorEvent(mockSensorEvent);
    }

    @Test
    public void testOnAccuracyChanged() {
        testEventListener.onAccuracyChanged(mockSensor, TEST_ACCURACY);
        Mockito.verify(mockAccuracyEventSubject, Mockito.times(1)).onNext(TEST_ACCURACY);
    }

    @Test
    public void testOnHorizontalFlingAway() {
        Assert.assertThat(testEventListener.onHorizontalFlingAway(), is(true));
        Mockito.verify(mockSensorEventSubject, Mockito.times(1))
                .onNext(LinearAccelerationAction.HORIZONTAL_FLING_AWAY);
    }

    @Test
    public void testGetEventSubject() {
        Assert.assertThat(testEventListener.getEventSubject(), is(mockSensorEventSubject));
    }

    @Test
    public void testGetAccuracyEventSubject() {
        Assert.assertThat(testEventListener.getAccuracyEventSubject(),
                is(mockAccuracyEventSubject));
    }

    @After
    public void tearDown() {
        testEventListener = null;
    }
}
