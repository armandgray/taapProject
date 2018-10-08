package com.armandgray.shared.db;

import com.armandgray.shared.model.Drill;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public interface DrillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDrill(Drill drill);

    @Query("SELECT * FROM drills")
    Flowable<List<Drill>> getDrills();
}
