package com.armandgray.taap.application;

import android.app.Application;

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

public class AppComponentTest {

    @Test
    public void testAppComponentBuilder_DeclaresInstanceBindingMethods() {
        AppComponent.Builder component = new AppComponent.Builder() {
            @Override
            public AppComponent.Builder application(Application application) {
                return null;
            }

            @Override
            public AppComponent.Builder appModule(AppModule module) {
                return null;
            }

            @Override
            public AppComponent build() {
                return null;
            }
        };

        Assert.assertThat(component, is(notNullValue()));
    }

    @Test
    public void testAppComponent_DeclaresInjectMethods() {
        AppComponent component = new AppComponent() {
            @Override
            public void inject(WearApplication application) {
            }

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
}
