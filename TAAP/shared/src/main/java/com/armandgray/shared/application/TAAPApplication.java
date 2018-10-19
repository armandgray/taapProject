package com.armandgray.shared.application;

import android.app.Application;

import com.armandgray.shared.db.DatabaseManager;

import javax.inject.Inject;

import dagger.android.HasActivityInjector;

public abstract class TAAPApplication extends Application implements HasActivityInjector {

    protected static TAAPAppComponent appComponent;

    @Inject
    DatabaseManager databaseManager;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static TAAPAppComponent getAppComponent() {
        return appComponent;
    }
}
