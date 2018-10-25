package com.armandgray.shared.db;

import com.armandgray.shared.model.Drill;
import com.armandgray.shared.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface DrillDao extends GlobalDatabase.BaseDao<Drill> {

    @Query("SELECT * FROM drills")
    Single<List<Drill>> all();

    @Query("SELECT * FROM drills WHERE id == :id")
    Single<Drill> drill(int id);

    @DatabaseManager.DatabaseScope
    class DrillDaoWrapper implements DrillDao {

        @Inject
        GlobalDatabase database;

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
        public Single<List<Long>> insert(Drill... arr) {
            return database.drillDao().insert(arr).subscribeOn(schedulers.io());
        }

        @Override
        public Single<Integer> update(Drill... arr) {
            return database.drillDao().update(arr).subscribeOn(schedulers.io());
        }

        @Override
        public Single<Integer> delete(Drill... arr) {
            return database.drillDao().delete(arr).subscribeOn(schedulers.io());
        }
    }
}
