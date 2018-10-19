package com.armandgray.shared.db;

import android.content.Context;

import com.armandgray.shared.application.TAAPAppComponent;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;
import com.armandgray.shared.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class DatabaseManagerImpl implements DatabaseManager {

    @Inject
    Context context;

    @Inject
    DrillDaoWrapper drillDaoWrapper;

    @Inject
    PerformanceDaoWrapper performanceDaoWrapper;

    @Inject
    DrillDatabase database;

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

    static class DrillDaoWrapper implements DrillDao {

        @Inject
        DrillDatabase database;

        @Inject
        SchedulerProvider schedulers;

        @Inject
        DrillDaoWrapper() {
        }

        @Override
        public Single<List<Drill>> all() {
            return database.drillDao().all().subscribeOn(schedulers.io());
        }

        @Override
        public Single<Drill> drill(int id) {
            return database.drillDao().drill(id).subscribeOn(schedulers.io());
        }

        @Override
        public Completable insert(Drill drill) {
            return database.drillDao().insert(drill).subscribeOn(schedulers.io());
        }

        @Override
        public Single<List<Long>> insert(Drill... arr) {
            return database.drillDao().insert(arr).subscribeOn(schedulers.io());
        }

        @Override
        public Completable update(Drill drill) {
            return database.drillDao().update(drill).subscribeOn(schedulers.io());
        }

        @Override
        public Single<Integer> update(Drill... arr) {
            return database.drillDao().update(arr).subscribeOn(schedulers.io());
        }

        @Override
        public Completable delete(Drill drill) {
            return database.drillDao().delete(drill).subscribeOn(schedulers.io());
        }

        @Override
        public Single<Integer> delete(Drill... arr) {
            return database.drillDao().delete(arr).subscribeOn(schedulers.io());
        }
    }

    static class PerformanceDaoWrapper implements PerformanceDao {

        @Inject
        DrillDatabase database;

        @Inject
        SchedulerProvider schedulers;

        @Inject
        PerformanceDaoWrapper() {
        }

        @Override
        public Single<List<Performance>> all() {
            return database.performanceDao().all().subscribeOn(schedulers.io());
        }

        @Override
        public Completable insert(Performance performance) {
            return database.performanceDao().insert(performance).subscribeOn(schedulers.io());
        }

        @Override
        public Single<List<Long>> insert(Performance... arr) {
            return database.performanceDao().insert(arr).subscribeOn(schedulers.io());
        }

        @Override
        public Completable update(Performance performance) {
            return database.performanceDao().update(performance).subscribeOn(schedulers.io());
        }

        @Override
        public Single<Integer> update(Performance... arr) {
            return database.performanceDao().update(arr).subscribeOn(schedulers.io());
        }

        @Override
        public Completable delete(Performance performance) {
            return database.performanceDao().delete(performance).subscribeOn(schedulers.io());
        }

        @Override
        public Single<Integer> delete(Performance... arr) {
            return database.performanceDao().delete(arr).subscribeOn(schedulers.io());
        }
    }
}
