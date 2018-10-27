package com.armandgray.shared.db;

import com.armandgray.shared.application.TAAPAppComponent;

import javax.inject.Inject;

import androidx.annotation.NonNull;

public class DatabaseManagerImpl implements DatabaseManager {

    @Inject
    GlobalDatabase database;

    @Inject
    SharedPreferencesDao sharedPreferencesDao;

    @Inject
    DrillDao.DrillDaoWrapper drillDaoWrapper;

    @Inject
    PerformanceDao.PerformanceDaoWrapper performanceDaoWrapper;

    @Inject
    SettingsDao.SettingsDaoWrapper settingsDaoWrapper;

    public DatabaseManagerImpl() {
    }

    @Override
    public void inject(TAAPAppComponent appComponent) {
        Component component = appComponent.databaseBuilder()
                .databaseManager(this)
                .managerModule(new ManagerModule())
                .build();

        DatabaseManager.State.setDatabaseComponent(component);
        component.inject(this);
    }

    @Override
    public SharedPreferencesDao getSharedPreferencesDao() {
        return sharedPreferencesDao;
    }

    @Override
    public DrillDao getDrillDao() {
        return drillDaoWrapper;
    }

    @Override
    public PerformanceDao getPerformanceDao() {
        return performanceDaoWrapper;
    }

    @Override
    public SettingsDao getSettingsDao() {
        return settingsDaoWrapper;
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }
}
