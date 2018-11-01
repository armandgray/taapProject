package com.armandgray.shared.sensors;

import com.armandgray.shared.application.TAAPAppComponent;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.annotation.Annotation;

import io.reactivex.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GeneralSensorManagerTest {

    @Test
    public void testGeneralSensorManager() {
        GeneralSensorManager testManager = new GeneralSensorManager() {
            @Override
            public void inject(TAAPAppComponent parentComponent) {

            }

            @Override
            public Observable<LinearAccelerationAction> getAutoTrackingObservable() {
                return null;
            }
        };

        Assert.assertThat(testManager, is(notNullValue()));
    }

    @Test
    public void testGeneralSensorManager_DefinesInterfaceTag() {
        Assert.assertThat(GeneralSensorManager.TAG, is(notNullValue()));
    }

    @Test
    public void testGeneralSensorManager_DefinesInterfaceSensorName() {
        Assert.assertThat(GeneralSensorManager.LINEAR_ACCELEROMETER, is("LINEAR_ACCELEROMETER"));
    }

    /**
     * Inner Class - Component
     */

    @SuppressWarnings("Convert2Lambda")
    @Test
    public void testGeneralSensorManager_Component() {
        GeneralSensorManager.Component component = new GeneralSensorManager.Component() {
            @Override
            public void inject(GeneralSensorManagerImpl manager) {
            }
        };

        GeneralSensorManager.Component.Builder builder =
                new GeneralSensorManager.Component.Builder() {
                    @Override
                    public GeneralSensorManager.Component.Builder sensorManager(
                            GeneralSensorManager manager) {
                        return null;
                    }

                    @Override
                    public GeneralSensorManager.Component.Builder managerModule(
                            GeneralSensorManager.ManagerModule module) {
                        return null;
                    }

                    @Override
                    public GeneralSensorManager.Component build() {
                        return null;
                    }
                };

        Assert.assertThat(component, is(notNullValue()));
        Assert.assertThat(builder, is(notNullValue()));
    }

    @Test
    public void testGeneralSensorManager_Scope() {
        GeneralSensorManager.SensorScope scope = new GeneralSensorManager.SensorScope() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }
        };

        Assert.assertThat(scope, is(notNullValue()));
    }

    /**
     * Inner Class - ManagerModule
     */

    @Ignore
    @Test
    public void testManagerModule_ProvideSensorManager() {
    }

    @Ignore
    @Test
    public void testManagerModule_ProvideLinearAccelerometer() {
    }
}
