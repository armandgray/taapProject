package com.armandgray.shared.application;

import com.armandgray.shared.db.ShootingPercentageViewModel;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

public class TAAPAppComponentTest {

    @SuppressWarnings("Convert2Lambda")
    @Test
    public void testAppComponent_DeclaresInjectMethods() {
        TAAPAppComponent component = new TAAPAppComponent() {

            @Override
            public void inject(ShootingPercentageViewModel viewModel) {
            }
        };

        Assert.assertThat(component, is(notNullValue()));
    }
}