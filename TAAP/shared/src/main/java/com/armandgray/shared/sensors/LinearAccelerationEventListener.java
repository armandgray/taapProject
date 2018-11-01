package com.armandgray.shared.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.armandgray.shared.rx.CountedSubjectWrapper;

import javax.inject.Inject;

import androidx.annotation.VisibleForTesting;

@GeneralSensorManager.SensorScope
class LinearAccelerationEventListener implements SensorEventListener,
        LinearAccelerationGestureDetector.LinearAccelerationGestureListener {

    @VisibleForTesting
    CountedSubjectWrapper<LinearAccelerationAction> sensorEventSubject;

    @VisibleForTesting
    CountedSubjectWrapper<Integer> accuracyEventSubject;

    @VisibleForTesting
    LinearAccelerationGestureDetector gestureDetector;

    @Inject
    LinearAccelerationEventListener() {
        this.gestureDetector = new LinearAccelerationGestureDetector(this);
        this.sensorEventSubject = CountedSubjectWrapper.createPublish();
        this.accuracyEventSubject = CountedSubjectWrapper.createPublish();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        gestureDetector.onSensorEvent(sensorEvent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        accuracyEventSubject.onNext(accuracy);
    }

    @Override
    public boolean onHorizontalFlingAway() {
        sensorEventSubject.onNext(LinearAccelerationAction.HORIZONTAL_FLING_AWAY);
        return true;
    }

    CountedSubjectWrapper<LinearAccelerationAction> getEventSubject() {
        return sensorEventSubject;
    }

    CountedSubjectWrapper<Integer> getAccuracyEventSubject() {
        return accuracyEventSubject;
    }
}
