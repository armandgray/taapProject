package com.armandgray.shared.db;

import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = { Drill.class, Performance.class }, version = 1)
@TypeConverters({Drill.Type.Converter.class})
public abstract class DrillDatabase extends RoomDatabase {

    static final String DATABASE_NAME = "drill-database";

    public abstract DrillDao drillDao();

    public abstract PerformanceDao performanceDao();
}
