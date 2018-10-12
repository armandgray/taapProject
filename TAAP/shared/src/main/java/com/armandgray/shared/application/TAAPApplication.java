package com.armandgray.shared.application;

import android.app.Application;

import com.armandgray.shared.db.DatabaseManagerImpl;

import javax.inject.Inject;

import dagger.android.HasActivityInjector;

public abstract class TAAPApplication extends Application implements HasActivityInjector {

    protected static TAAPAppComponent appComponent;

    @Inject
    DatabaseManagerImpl databaseManager;

    @Override
    public void onCreate() {
        super.onCreate();

        databaseManager.populateDefaults();
    }

    public static TAAPAppComponent getAppComponent() {
        return appComponent;
    }
}
