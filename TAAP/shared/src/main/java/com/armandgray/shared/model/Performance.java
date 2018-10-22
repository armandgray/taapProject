package com.armandgray.shared.model;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "performances",
        indices = {@Index(name = "drill", value = "drill_id", unique = true)},
        foreignKeys = @ForeignKey(
                entity = Drill.class,
                parentColumns = "id",
                childColumns = "drill_id",
                onUpdate = CASCADE,
                onDelete = CASCADE))
public class Performance {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "drill_id")
    private int drillId;

    private int count;

    private int total;

    private int reps;

    private double goal;

    @Nullable
    @TypeConverters(WorkoutLocation.Converter.class)
    private WorkoutLocation location;

    @ColumnInfo(name = "start_time")
    private long startTime;

    @ColumnInfo(name = "end_time")
    private long endTime;

    public Performance() {
        // Default Constructor For Room Object Creation
    }

    @Ignore
    public Performance(@NonNull Drill drill) {
        this.drillId = drill.getId();
        this.count = 0;
        this.total = 0;
        this.reps = drill.getReps();
        this.goal = drill.getGoal();
        this.location = new WorkoutLocation("Today - YMCA Embarcadero");
        this.startTime = System.currentTimeMillis();
    }

    @Ignore
    public Performance(@NonNull Performance clone) {
        this.id = clone.id;
        this.drillId = clone.drillId;
        this.count = clone.count;
        this.total = clone.total;
        this.reps = clone.reps;
        this.goal = clone.goal;
        this.location = clone.location;
        this.startTime = clone.startTime;
        this.endTime = clone.endTime;
    }

    public int getId() {
        return id;
    }

    public int getDrillId() {
        return drillId;
    }

    public int getCount() {
        return this.count;
    }

    public int getTotal() {
        return this.total;
    }

    public int getReps() {
        return this.reps;
    }

    public double getGoal() {
        return this.goal;
    }

    @SuppressWarnings("WeakerAccess") // VisibleForRoom
    @Nullable
    public WorkoutLocation getLocation() {
        return this.location;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void raiseCount() {
        this.count++;
    }

    public void raiseTotal() {
        this.total++;
    }

    public boolean isSuccess() {
        return (double) this.count / this.total >= this.goal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDrillId(int drillId) {
        this.drillId = drillId;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setGoal(double goal) {
        this.goal = goal;
    }

    public void setLocation(@NonNull WorkoutLocation location) {
        this.location = location;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void clear() {
        this.count = 0;
        this.total = 0;
        this.startTime = System.currentTimeMillis();
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%d/%d", count, total);
    }
}
