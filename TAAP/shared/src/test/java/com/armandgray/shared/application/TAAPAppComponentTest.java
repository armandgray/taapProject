package com.armandgray.shared.application;

import com.armandgray.shared.viewModel.LogsViewModel;
import com.armandgray.shared.navigation.NavigationViewModel;
import com.armandgray.shared.viewModel.PreferencesViewModel;
import com.armandgray.shared.viewModel.DrillViewModel;
import com.armandgray.shared.viewModel.PerformanceViewModel;
import com.armandgray.shared.viewModel.SettingsViewModel;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TAAPAppComponentTest {

    @SuppressWarnings("Convert2Lambda")
    @Test
    public void testAppComponent_DeclaresInjectMethods() {
        TAAPAppComponent component = new TAAPAppComponent() {

            @Override
            public void inject(PerformanceViewModel viewModel) {
            }

            @Override
            public void inject(DrillViewModel viewModel) {
            }

            @Override
            public void inject(SettingsViewModel viewModel) {
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