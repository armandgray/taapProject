package com.armandgray.shared.db;

import java.util.List;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;
import io.reactivex.Completable;
import io.reactivex.Single;

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(T t);

    @SuppressWarnings("unchecked")
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<List<Long>> insert(T... arr);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Completable update(T t);

    @SuppressWarnings("unchecked")
    @Update(onConflict = OnConflictStrategy.REPLACE)
    Single<Integer> update(T... arr);

    @Delete
    Completable delete(T t);

    @SuppressWarnings("unchecked")
    @Delete
    Single<Integer> delete(T... arr);
}
