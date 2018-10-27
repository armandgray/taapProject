package com.armandgray.shared.application;

import android.app.Application;
import android.content.Context;

import com.armandgray.shared.db.DatabaseManager;
import com.armandgray.shared.db.DatabaseManagerImpl;
import com.armandgray.shared.location.LocationManager;
import com.armandgray.shared.location.LocationManagerImpl;
import com.armandgray.shared.permission.PermissionManager;
import com.armandgray.shared.permission.PermissionManagerImpl;
import com.armandgray.shared.rx.SchedulerProvider;
import com.google.gson.Gson;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;

@SuppressWarnings("WeakerAccess")
@Module
public abstract class TAAPAppModule {

    /** TAAP */

    @Provides
    @Singleton
    @NonNull
    protected DatabaseManager provideDatabaseManager() {
        DatabaseManager databaseManager = new DatabaseManagerImpl();
        databaseManager.inject(TAAPApplication.getAppComponent());
        return databaseManager;
    }

    @Provides
    @Singleton
    @NonNull
    protected SchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.DEFAULT;
    }

    @Provides
    @Singleton
    @NonNull
    protected LocationManager provideLocationManager() {
        LocationManager locationManager = new LocationManagerImpl();
        locationManager.inject(TAAPApplication.getAppComponent());
        return locationManager;
    }

    @Provides
    @Singleton
    @NonNull
    protected PermissionManager providePermissionManager() {
        PermissionManager permissionManager = new PermissionManagerImpl();
        permissionManager.inject(TAAPApplication.getAppComponent());
        return permissionManager;
    }

    /** Android APIs */

    @SuppressWarnings("NullableProblems")
    @Provides
    @Singleton
    @NonNull
    protected Context provideContext(Application application) {
        return application.getApplicationContext();
    }

    /** Utils */

    @SuppressWarnings("NullableProblems")
    @Provides
    @Singleton
    @NonNull
    protected Gson provideGson() {
        return new Gson();
    }
}
