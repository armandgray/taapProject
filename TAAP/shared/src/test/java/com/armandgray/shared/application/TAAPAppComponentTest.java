package com.armandgray.shared.application;

import com.armandgray.shared.db.DatabaseManager;
import com.armandgray.shared.location.LocationManager;
import com.armandgray.shared.permission.PermissionManager;
import com.armandgray.shared.permission.PermissionViewModel;
import com.armandgray.shared.sensors.GeneralSensorManager;
import com.armandgray.shared.viewModel.DrillViewModel;
import com.armandgray.shared.viewModel.LocationViewModel;
import com.armandgray.shared.viewModel.LogsViewModel;
import com.armandgray.shared.viewModel.PerformanceViewModel;
import com.armandgray.shared.viewModel.PreferencesViewModel;
import com.armandgray.shared.voice.VoiceManager;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TAAPAppComponentTest {

    @Test
    public void testAppComponent_DeclaresInjectMethods() {
        TAAPAppComponent component = new TAAPAppComponent() {

            @Override
            public DatabaseManager.Component.Builder databaseBuilder() {
                return null;
            }

            @Override
            public LocationManager.Component.Builder locationBuilder() {
                return null;
            }

            @Override
            public PermissionManager.Component.Builder permissionBuilder() {
                return null;
            }

            @Override
            public GeneralSensorManager.Component.Builder sensorBuilder() {
                return null;
            }

            @Override
            public VoiceManager.Component.Builder voiceBuilder() {
                return null;
            }

            @Override
            public void inject(PerformanceViewModel viewModel) {
            }

            @Override
            public void inject(DrillViewModel viewModel) {
            }

            @Override
            public void inject(LogsViewModel viewModel) {
            }

            @Override
            public void inject(PreferencesViewModel viewModel) {
            }

            @Override
            public void inject(LocationViewModel viewModel) {

            }

            @Override
            public void inject(PermissionViewModel viewModel) {

            }
        };

        Assert.assertThat(component, is(notNullValue()));
    }

    @Test
    public void testAppComponent_DeclaresInjectableSubComponentInterface() {
        TAAPAppComponent.InjectableSubComponent component = parentComponent -> {

                };
        Assert.assertThat(component, is(notNullValue()));
    }
}
