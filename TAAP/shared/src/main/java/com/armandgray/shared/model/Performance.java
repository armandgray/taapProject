package com.armandgray.shared.model;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        indices = @Index("drill_title"),
        foreignKeys = @ForeignKey(
                entity = Drill.class,
                parentColumns = "title",
                childColumns = "drill_title",
                onUpdate = CASCADE,
                onDelete = CASCADE))
public class Performance implements Comparable<Performance> {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SuppressWarnings("NullableProblems")
    @ColumnInfo(name = "drill_title")
    @NonNull
    private String drillTitle;

    @NonNull
    private String date;

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
        String today = new SimpleDateFormat("yyyy-MM-dd hh:mm aa", Locale.getDefault()).format(new Date());

        this.drillTitle = drill.getTitle();
        this.count = 0;
        this.date = today;
        this.total = 0;
        this.reps = drill.getReps();
        this.goal = drill.getGoal();
        this.location = new WorkoutLocation("YMCA Embarcadero");
        captureStartTime();
        captureEndTime();
    }

    @Ignore
    public Performance(@NonNull Performance clone) {
        this.id = clone.id;
        this.drillTitle = clone.drillTitle;
        this.date = clone.date;
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

    @NonNull
    public String getDrillTitle() {
        return drillTitle;
    }

    @NonNull
    public String getDate() {
        return date;
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

    public long getLength() {
        return endTime % startTime;
    }

    public float getRate() {
        return (float) count / total;
    }

    public void raiseCount() {
        this.count++;
    }

    public void raiseTotal() {
        if (this.total == 0) {
            captureStartTime();
        }

        this.total++;
    }

    public boolean isSuccess() {
        return (double) this.count / this.total >= this.goal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDrillTitle(@NonNull String drillTitle) {
        this.drillTitle = drillTitle;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
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

    private void captureStartTime() {
        this.startTime = System.currentTimeMillis();
    }

    public void captureEndTime() {
        this.endTime = System.currentTimeMillis();
    }

    public void clear() {
        this.count = 0;
        this.total = 0;
    }

    @Override
    public int compareTo(Performance that) {
        return that == null ? 1 : Long.compare(that.startTime, this.startTime);
    }

    @NonNull
    @Override
    public String toString() {
        String length = new SimpleDateFormat("mm:ss.SSS", Locale.getDefault())
                .format(new Date(getLength()));
        return String.format(Locale.getDefault(), "Performance(%d){%s: %d/%d(%.2f) for %s}",
                id, drillTitle, count, total, getRate(), length);
    }
}
