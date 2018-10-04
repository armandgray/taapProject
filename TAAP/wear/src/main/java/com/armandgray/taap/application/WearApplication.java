package com.armandgray.taap.application;

import android.app.Activity;
import android.content.Context;

import com.armandgray.shared.application.TAAPApplication;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;

public class WearApplication extends TAAPApplication {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingInjector;

    @Inject
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        AppComponent component = DaggerAppComponent.builder()
                .application(this)
                .appModule(new AppModule())
                .build();
        component.inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingInjector;
    }
}
