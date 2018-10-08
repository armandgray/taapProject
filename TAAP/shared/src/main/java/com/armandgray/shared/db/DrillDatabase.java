package com.armandgray.shared.db;

import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = { Drill.class, Performance.class }, version = 1)
public abstract class DrillDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "drill-database";

    public abstract DrillDao drillDao();

    public abstract PerformanceDao performanceDao();
}
