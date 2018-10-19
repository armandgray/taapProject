package com.armandgray.shared.model;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "performances",
        indices = {@Index(name = "drill", value = "drill_id", unique = true)}/*,
        foreignKeys = @ForeignKey(
                entity = Drill.class,
                parentColumns = "id",
                childColumns = "drill_id")*/)
public class Performance {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "drill_id")
    private int drillId;

    private int count;

    private int total;

    private int reps;

    private double goal;

    @ColumnInfo(name = "start_time")
    private long startTime;

    @ColumnInfo(name = "end_time")
    private long endTime;

    public Performance() {
        // Default Constructor For Room Object Creation
    }

    public Performance(Drill drill) {
        this.drillId = drill.getId();
        this.count = 0;
        this.total = 0;
        this.reps = drill.getReps();
        this.goal = drill.getGoal();
        this.startTime = System.currentTimeMillis();
    }

    public Performance(Performance clone) {
        this.id = clone.id;
        this.drillId = clone.drillId;
        this.count = clone.count;
        this.total = clone.total;
        this.reps = clone.reps;
        this.startTime = clone.startTime;
        this.endTime = clone.endTime;
        this.goal = clone.goal;
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

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public double getGoal() {
        return this.goal;
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

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setGoal(double goal) {
        this.goal = goal;
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
