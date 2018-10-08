package com.armandgray.taap.application;

import android.app.Activity;

import com.armandgray.shared.application.TAAPApplication;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;

public class WearApplication extends TAAPApplication {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingInjector;

    @Override
    public void onCreate() {
        initDagger();

        super.onCreate();
    }

    private void initDagger() {
        AppComponent component = DaggerAppComponent.builder()
                .application(this)
                .appModule(new AppModule())
                .build();
        TAAPApplication.appComponent = component;
        component.inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingInjector;
    }
}
