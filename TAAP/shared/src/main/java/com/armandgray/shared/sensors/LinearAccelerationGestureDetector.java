package com.armandgray.shared.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

class LinearAccelerationGestureDetector {

    private static final int HORIZONTAL_FLING_THRESHOLD = 4;

    private final LinearAccelerationGestureListener listener;

    private boolean consumed;

    LinearAccelerationGestureDetector(LinearAccelerationGestureListener listener) {
        this.listener = listener;
    }

    void onSensorEvent(SensorEvent sensorEvent) {
        LinearAccelerationEvent event = new LinearAccelerationEvent(sensorEvent);
        if (event.isStill() || consumed) {
            consumed = !event.isStill();
            return;
        }

        if (event.horizontal > HORIZONTAL_FLING_THRESHOLD && listener.onHorizontalFlingAway()) {
            consumed = true;
        }
    }

    static class LinearAccelerationEvent {

        final int horizontal;
        final int vertical;
        final int depth;

        LinearAccelerationEvent(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() != Sensor.TYPE_LINEAR_ACCELERATION) {
                throw new IllegalArgumentException("Wrong Sensor");
            }

            this.horizontal = (int) sensorEvent.values[0];
            this.vertical = (int) sensorEvent.values[1];
            this.depth = (int) sensorEvent.values[2];
        }

        boolean isStill() {
            return horizontal == 0 && vertical == 0 && depth == 0;
        }
    }

    interface LinearAccelerationGestureListener {

        default boolean onHorizontalFlingAway() {
            return false;
        }
    }
}
