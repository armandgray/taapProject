package com.armandgray.taap.application;

import android.app.Application;
import android.content.Context;

import com.armandgray.shared.db.DrillDatabase;
import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AppModuleTest {

    @SuppressWarnings("NullableProblems")
    @Test
    public void testAppModule_DeclaresInjectionMethods() {
        AppModule module = new AppModule() {

            @Override
            Context provideContext(Application application) {
                return null;
            }

            @Override
            DrillDatabase provideDrillDatabase(Context context) {
                return null;
            }

            @Override
            Gson provideGson() {
                return null;
            }
        };

        Assert.assertThat(module, is(notNullValue()));
    }
}
