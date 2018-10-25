package com.armandgray.taap.application;

import android.app.Application;

import com.armandgray.shared.db.DatabaseManager;
import com.armandgray.shared.viewModel.DrillViewModel;
import com.armandgray.shared.viewModel.LogsViewModel;
import com.armandgray.shared.viewModel.PerformanceViewModel;
import com.armandgray.shared.viewModel.PreferencesViewModel;

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
        };

        Assert.assertThat(component, is(notNullValue()));
    }
}
