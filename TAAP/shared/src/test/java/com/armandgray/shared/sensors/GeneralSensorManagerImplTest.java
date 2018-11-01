package com.armandgray.shared.sensors;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.armandgray.shared.application.TAAPAppComponent;
import com.armandgray.shared.rx.CountedSubjectWrapper;
import com.armandgray.shared.rx.SchedulerProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Action;

import static org.hamcrest.CoreMatchers.is;

public class GeneralSensorManagerImplTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private TAAPAppComponent mockAppComponent;

    @Mock
    private GeneralSensorManager.Component mockComponent;

    @Mock
    private GeneralSensorManager.Component.Builder mockBuilder;

    @Mock
    private SensorManager mockSensorManager;

    @Mock
    private SchedulerProvider mockSchedulers;

    @Mock
    private CountedSubjectWrapper<LinearAccelerationAction> mockCountedSubjectWrapper;

    @Mock
    private Observable<LinearAccelerationAction> mockObservable;

    @Mock
    private LinearAccelerationEventListener mockLinearAccelEventListener;

    @Mock
    private Sensor mockLinearAccelerometer;

    private GeneralSensorManagerImpl testGeneralSensorManager;

    @Before
    public void setUp() {
        testGeneralSensorManager = new GeneralSensorManagerImpl();
        testGeneralSensorManager.schedulers = mockSchedulers;
        testGeneralSensorManager.sensorManager = mockSensorManager;
        testGeneralSensorManager.linearAccelerationEventListener = mockLinearAccelEventListener;
        testGeneralSensorManager.linearAccelerometer = mockLinearAccelerometer;

        Mockito.when(mockAppComponent.sensorBuilder()).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.sensorManager(testGeneralSensorManager)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.managerModule(Mockito.any(
                GeneralSensorManager.ManagerModule.class))).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.build()).thenReturn(mockComponent);

        Mockito.when(mockSchedulers.asyncTask())
                .thenAnswer((Answer<ObservableTransformer>) invocation -> testTransformer());
        Mockito.when(mockLinearAccelEventListener.getEventSubject())
                .thenReturn(mockCountedSubjectWrapper);
        Mockito.when(mockCountedSubjectWrapper.doOnObserved(Mockito.any()))
                .thenReturn(mockCountedSubjectWrapper);
        Mockito.when(mockCountedSubjectWrapper.doOnUnObserved(Mockito.any()))
                .thenReturn(mockCountedSubjectWrapper);
        Mockito.when(mockCountedSubjectWrapper.toObservable()).thenReturn(mockObservable);
    }

    private <U> ObservableTransformer<U, U> testTransformer() {
        return observable -> observable
                .doOnSubscribe(ignore -> {})
                .doOnDispose(() -> {});
    }

    @Test
    public void testInject() {
        testGeneralSensorManager.inject(mockAppComponent);

        Mockito.verify(mockAppComponent, Mockito.times(1)).sensorBuilder();
        Mockito.verify(mockBuilder, Mockito.times(1)).sensorManager(testGeneralSensorManager);
        Mockito.verify(mockBuilder, Mockito.times(1))
                .managerModule(Mockito.any(GeneralSensorManager.ManagerModule.class));
        Mockito.verify(mockBuilder, Mockito.times(1)).build();
        Mockito.verify(mockComponent, Mockito.times(1)).inject(testGeneralSensorManager);
    }

    @Test
    public void testGetAutoTrackingObservable_ReturnsMissingHardware_IfSensorIsNull() {
        testGeneralSensorManager.linearAccelerometer = null;

        Assert.assertThat(testGeneralSensorManager.getAutoTrackingObservable().blockingLast(),
                is(LinearAccelerationAction.MISSING_HARDWARE));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testGetAutoTrackingObservable_GetsEventListenerSubject() {
        testGeneralSensorManager.getAutoTrackingObservable().subscribe();
        Mockito.verify(mockLinearAccelEventListener, Mockito.times(1)).getEventSubject();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testGetAutoTrackingObservable_DoOnObserver() {
        testGeneralSensorManager.getAutoTrackingObservable().subscribe();
        Mockito.verify(mockCountedSubjectWrapper, Mockito.times(1)).doOnObserved(Mockito.any());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testGetAutoTrackingObservable_DoOnObserver_PassesRegisterCall() throws Exception {
        // Arrange
        ArgumentCaptor<Action> captor = ArgumentCaptor.forClass(Action.class);
        testGeneralSensorManager.getAutoTrackingObservable().subscribe();
        Mockito.verify(mockCountedSubjectWrapper, Mockito.times(1)).doOnObserved(captor.capture());

        // Act
        captor.getValue().run();

        // Assert
        Mockito.verify(mockCountedSubjectWrapper, Mockito.times(1))
                .onNext(LinearAccelerationAction.ACTIVE);
        Mockito.verify(mockSensorManager, Mockito.times(1))
                .registerListener(mockLinearAccelEventListener, mockLinearAccelerometer,
                        SensorManager.SENSOR_DELAY_NORMAL);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testGetAutoTrackingObservable_DoOnObserver_PassesUnRegisterCall() throws Exception {
        // Arrange
        ArgumentCaptor<Action> captor = ArgumentCaptor.forClass(Action.class);
        testGeneralSensorManager.getAutoTrackingObservable().subscribe();
        Mockito.verify(mockCountedSubjectWrapper, Mockito.times(1)).doOnUnObserved(captor.capture());

        // Act
        captor.getValue().run();

        // Assert
        Mockito.verify(mockCountedSubjectWrapper, Mockito.times(1))
                .onNext(LinearAccelerationAction.INACTIVE);
        Mockito.verify(mockSensorManager, Mockito.times(1))
                .unregisterListener(mockLinearAccelEventListener);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testGetAutoTrackingObservable_DoOnUnObserver() {
        testGeneralSensorManager.getAutoTrackingObservable().subscribe();
        Mockito.verify(mockCountedSubjectWrapper, Mockito.times(1)).doOnUnObserved(Mockito.any());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testGetAutoTrackingObservable_ToObservable() {
        testGeneralSensorManager.getAutoTrackingObservable().subscribe();
        Mockito.verify(mockCountedSubjectWrapper, Mockito.times(1)).toObservable();
    }

    @Ignore
    @SuppressWarnings({"ResultOfMethodCallIgnored", "unchecked"})
    @Test
    public void testGetAutoTrackingObservable_Compose() {
        // Arrange
        ArgumentCaptor<ObservableTransformer> captor = ArgumentCaptor
                .forClass(ObservableTransformer.class);

        // Act
        testGeneralSensorManager.getAutoTrackingObservable().subscribe();
        Mockito.verify(mockObservable, Mockito.times(1)).compose(captor.capture());

        // Assert
        Assert.assertThat(captor.getValue(), is(testTransformer()));
    }

    @Test
    public void testToString() {
        Assert.assertThat(testGeneralSensorManager.toString(), is("GeneralSensorManagerImpl@"
                + Integer.toHexString(testGeneralSensorManager.hashCode())));
    }

    @After
    public void tearDown() {
        testGeneralSensorManager = null;
    }
}