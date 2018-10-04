package com.armandgray.shared.application;

import android.app.Application;

import dagger.android.HasActivityInjector;

public abstract class TAAPApplication extends Application implements HasActivityInjector {

    protected static TAAPAppComponent appComponent;

    public static TAAPAppComponent getAppComponent() {
        return appComponent;
    }
}
