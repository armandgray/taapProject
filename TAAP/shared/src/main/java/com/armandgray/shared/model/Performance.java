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

    private static final int DEFAULT_MAX = 10;
    private static final float DEFAULT_SUCCESS_RATE = 0.75f;

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "drill_id")
    private int drillId;

    private int count;
    private int total;
    private int max;

    @ColumnInfo(name = "start_time")
    private long startTime;

    @ColumnInfo(name = "end_time")
    private long endTime;

    @ColumnInfo(name = "success_rate")
    private float successRate;

    public Performance(int drillId) {
        this.drillId = drillId;
        this.count = 0;
        this.total = 0;
        this.max = DEFAULT_MAX;
        this.startTime = System.currentTimeMillis();
        this.successRate = DEFAULT_SUCCESS_RATE;
    }

    public Performance(Performance clone) {
        this.id = clone.id;
        this.drillId = clone.drillId;
        this.count = clone.count;
        this.total = clone.total;
        this.max = clone.max;
        this.startTime = clone.startTime;
        this.endTime = clone.endTime;
        this.successRate = clone.successRate;
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

    public int getMax() {
        return this.max;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public float getSuccessRate() {
        return this.successRate;
    }

    public void raiseCount() {
        this.count++;
    }

    public void raiseTotal() {
        this.total++;
    }

    public boolean isSuccess() {
        return (float) this.count / this.total >= this.successRate;
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

    public void setMax(int max) {
        this.max = max;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setSuccessRate(float successRate) {
        this.successRate = successRate;
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
