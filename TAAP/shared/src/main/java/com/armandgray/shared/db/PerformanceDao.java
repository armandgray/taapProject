package com.armandgray.shared.db;

import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Embedded;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface PerformanceDao extends BaseDao<Performance> {

    @Query("SELECT * FROM performances")
    Single<List<Performance>> all();

    @Query("SELECT performances.*, drills.type FROM performances "
            + "INNER JOIN drills ON drills.id = performances.drill_id "
            + "WHERE start_time BETWEEN :startTime AND :endTime")
    Single<List<DaoLog>> logsBetween(long startTime, long endTime);

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
