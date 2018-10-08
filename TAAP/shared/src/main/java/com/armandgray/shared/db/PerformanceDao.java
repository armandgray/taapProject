package com.armandgray.shared.db;

import com.armandgray.shared.model.Performance;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public interface PerformanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPerformance(Performance performance);

    @Query("SELECT * FROM performances")
    Flowable<Performance[]> getPerformances();
}
