package com.armandgray.shared.db;

import android.content.Context;

import com.armandgray.shared.application.TAAPAppComponent;

import javax.inject.Inject;

public class DatabaseManagerImpl implements DatabaseManager {

    @Inject
    Context context;

    @Inject
    DrillDao.DrillDaoWrapper drillDaoWrapper;

    @Inject
    PerformanceDao.PerformanceDaoWrapper performanceDaoWrapper;

    @Inject
    SettingsDao.SettingsDaoWrapper settingsDaoWrapper;

    @Inject
    GlobalDatabase database;

    public DatabaseManagerImpl() {
    }

    @Override
    public void inject(TAAPAppComponent appComponent) {
        Component component = appComponent.databaseBuilder()
                .databaseManager(this)
                .managerModule(new ManagerModule())
                .build();

        component.inject(this);
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
}
