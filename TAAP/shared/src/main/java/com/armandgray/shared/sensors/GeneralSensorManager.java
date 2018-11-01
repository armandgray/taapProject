package com.armandgray.shared.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.armandgray.shared.application.TAAPAppComponent;
import com.armandgray.shared.helpers.StringHelper;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Named;
import javax.inject.Scope;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dagger.BindsInstance;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import io.reactivex.Observable;

/**
 * @see GeneralSensorManagerImpl
 */
public interface GeneralSensorManager
        extends TAAPAppComponent.InjectableSubComponent<TAAPAppComponent> {

    String TAG = StringHelper.toLogTag(GeneralSensorManager.class.getSimpleName());
    String LINEAR_ACCELEROMETER = "LINEAR_ACCELEROMETER";

    Observable<LinearAccelerationAction> getAutoTrackingObservable();

    @SensorScope
    @Subcomponent(modules = ManagerModule.class)
    interface Component {

        @Subcomponent.Builder
        interface Builder {

            @BindsInstance
            Component.Builder sensorManager(GeneralSensorManager manager);

            @BindsInstance
            Component.Builder managerModule(ManagerModule module);

            Component build();
        }

        void inject(GeneralSensorManagerImpl manager);
    }

    @Scope
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface SensorScope {
    }

    @Module
    class ManagerModule {

        @Provides
        @SensorScope
        @NonNull
        SensorManager provideSensorManager(Context context) {
            return (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        }

        @Provides
        @SensorScope
        @Nullable
        @Named(LINEAR_ACCELEROMETER)
        Sensor provideLinearAccelerometer(SensorManager sensorManager) {
            return sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        }
    }
}
