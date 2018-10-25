package com.armandgray.shared.db;

import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;
import com.armandgray.shared.model.Setting;
import com.armandgray.shared.model.UXPreference;

import java.util.List;

import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.Update;
import io.reactivex.Single;

@Database(entities = {Drill.class, Performance.class, Setting.class}, version = 1)
@TypeConverters({Drill.Type.Converter.class, UXPreference.Converter.class})
public abstract class GlobalDatabase extends RoomDatabase {

    static final String DATABASE_NAME = "global-database";

    public abstract DrillDao drillDao();

    public abstract PerformanceDao performanceDao();

    public abstract SettingsDao settingsDao();

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
}
