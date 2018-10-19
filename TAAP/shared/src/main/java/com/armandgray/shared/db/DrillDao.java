package com.armandgray.shared.db;

import com.armandgray.shared.model.Drill;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface DrillDao extends BaseDao<Drill> {

    @Query("SELECT * FROM drills")
    Single<List<Drill>> all();

    @Query("SELECT * FROM drills WHERE id == :id")
    Single<Drill> drill(int id);
}
