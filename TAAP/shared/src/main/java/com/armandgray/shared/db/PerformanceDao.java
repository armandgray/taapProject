package com.armandgray.shared.db;

import com.armandgray.shared.model.Performance;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface PerformanceDao extends BaseDao<Performance> {

    @Query("SELECT * FROM performances")
    Single<List<Performance>> all();
}
