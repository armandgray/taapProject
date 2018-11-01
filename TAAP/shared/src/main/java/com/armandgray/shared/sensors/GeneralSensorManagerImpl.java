package com.armandgray.shared.sensors;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.armandgray.shared.application.TAAPAppComponent;
import com.armandgray.shared.rx.SchedulerProvider;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;

public class GeneralSensorManagerImpl implements GeneralSensorManager {

    @Inject
    SchedulerProvider schedulers;

    @Inject
    SensorManager sensorManager;

    @Inject
    LinearAccelerationEventListener linearAccelerationEventListener;

    @Nullable
    @Inject
    @Named(LINEAR_ACCELEROMETER)
    Sensor linearAccelerometer;

    public GeneralSensorManagerImpl() {
    }

    @Override
    public void inject(TAAPAppComponent appComponent) {
        Component component = appComponent.sensorBuilder()
                .sensorManager(this)
                .managerModule(new ManagerModule())
                .build();

        component.inject(this);
    }

    @Override
    public Observable<LinearAccelerationAction> getAutoTrackingObservable() {
        return hasAutoTrackingHardware()
                ? linearAccelerationActionObservable()
                : Observable.just(LinearAccelerationAction.MISSING_HARDWARE);
    }

    private boolean hasAutoTrackingHardware() {
        return linearAccelerometer != null;
    }

    private Observable<LinearAccelerationAction> linearAccelerationActionObservable() {
        return linearAccelerationEventListener.getEventSubject()
                .doOnObserved(registerListener())
                .doOnUnObserved(unregisterListener())
                .toObservable()
                .compose(schedulers.asyncTask());

    }

    private Action registerListener() {
        return () -> {
            LinearAccelerationAction active = LinearAccelerationAction.ACTIVE;
            linearAccelerationEventListener.getEventSubject().onNext(active);
            sensorManager.registerListener(linearAccelerationEventListener,
                    linearAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        };
    }

    private Action unregisterListener() {
        return () -> {
            LinearAccelerationAction inactive = LinearAccelerationAction.INACTIVE;
            linearAccelerationEventListener.getEventSubject().onNext(inactive);
            sensorManager.unregisterListener(linearAccelerationEventListener);
        };
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }
}
