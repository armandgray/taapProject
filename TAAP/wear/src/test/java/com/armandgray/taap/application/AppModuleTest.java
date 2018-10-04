package com.armandgray.taap.application;

import android.app.Application;
import android.content.Context;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AppModuleTest {

    @Test
    public void testAppModule_DeclaresInjectionMethods() {
        AppModule module = new AppModule() {

            @Override
            Context provideContext(Application application) {
                return null;
            }
        };

        Assert.assertThat(module, is(notNullValue()));
    }
}
