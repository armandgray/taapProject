package com.armandgray.shared.application;

import android.app.Application;

import dagger.android.HasActivityInjector;

public abstract class TAAPApplication extends Application implements HasActivityInjector {

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
