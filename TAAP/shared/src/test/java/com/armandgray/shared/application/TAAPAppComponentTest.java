package com.armandgray.shared.application;

import com.armandgray.shared.viewModel.PercentageRateViewModel;

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
            public void inject(PercentageRateViewModel viewModel) {
            }
        };

        Assert.assertThat(component, is(notNullValue()));
    }
}