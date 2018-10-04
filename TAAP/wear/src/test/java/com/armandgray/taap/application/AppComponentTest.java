package com.armandgray.taap.application;

import android.app.Application;

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
    public void testAppComponent_DeclaresApplicationInjectMethod() {
        AppComponent component = application -> {};
        Assert.assertThat(component, is(notNullValue()));
    }
}
