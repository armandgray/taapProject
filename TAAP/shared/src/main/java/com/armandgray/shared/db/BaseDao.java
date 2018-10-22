package com.armandgray.shared.db;

import java.util.List;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;
import io.reactivex.Single;

interface BaseDao<T> {

    @SuppressWarnings("unchecked")
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<List<Long>> insert(T... arr);

    @SuppressWarnings("unchecked")
    @Update(onConflict = OnConflictStrategy.REPLACE)
    Single<Integer> update(T... arr);

    @SuppressWarnings("unchecked")
    @Delete
    Single<Integer> delete(T... arr);
}
