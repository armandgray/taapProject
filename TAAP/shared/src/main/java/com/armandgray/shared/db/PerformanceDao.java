package com.armandgray.shared.db;

import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;
import com.armandgray.shared.rx.SchedulerProvider;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Embedded;
import androidx.room.Query;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface PerformanceDao extends GlobalDatabase.BaseDao<Performance> {

    @Query("SELECT * FROM performances")
    Single<List<Performance>> all();

    @Query("SELECT performances.*, drills.type FROM performances "
            + "INNER JOIN drills ON drills.title = performances.drill_title "
            + "WHERE start_time BETWEEN :startTime AND :endTime")
    Single<List<DaoLog>> logsBetween(long startTime, long endTime);

    @Query("DELETE FROM drills")
    void deleteAll();

    @DatabaseManager.DatabaseScope
    class PerformanceDaoWrapper implements PerformanceDao {

        @Inject
        GlobalDatabase database;

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
        public Single<List<DaoLog>> logsBetween(long startTime, long endTime) {
            return database.performanceDao()
                    .logsBetween(startTime, endTime)
                    .subscribeOn(schedulers.io());
        }

        @Override
        public void deleteAll() {
            // TODO remove hack
            Observable.just("One").subscribeOn(schedulers.io())
                    .doFinally(database.performanceDao()::deleteAll)
                    .subscribe();
        }

        @Override
        public Single<List<Long>> insert(Performance... arr) {
            return database.performanceDao().insert(arr).subscribeOn(schedulers.io());
        }

        @Override
        public Single<Integer> update(Performance... arr) {
            return database.performanceDao().update(arr).subscribeOn(schedulers.io());
        }

        @Override
        public Single<Integer> delete(Performance... arr) {
            return database.performanceDao().delete(arr).subscribeOn(schedulers.io());
        }
    }

    class DaoLog implements Comparable<DaoLog> {

        private List<Drill.Type> type;

        @Embedded
        private Performance performance;

        public List<Drill.Type> getType() {
            return type;
        }

        public void setType(List<Drill.Type> type) {
            this.type = type;
        }

        public Performance getPerformance() {
            return performance;
        }

        public void setPerformance(Performance performance) {
            this.performance = performance;
        }

        @NonNull
        @Override
        public String toString() {
            return String.format(Locale.getDefault(), "DaoLog{%s, %s}", type, performance);
        }

        @Override
        public int compareTo(DaoLog that) {
            return that == null ? 1 : this.performance.compareTo(that.performance);
        }
    }
}
