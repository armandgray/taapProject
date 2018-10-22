package com.armandgray.shared.model;

import com.armandgray.shared.db.PerformanceDao;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class WorkoutInfo {

    @NonNull
    private final List<Drill.Type> types;

    @Nullable
    private final WorkoutLocation location;

    @NonNull
    private final String overallPerformance;

    private final int overallReps;

    @NonNull
    private final String length;

    public WorkoutInfo(@NonNull List<PerformanceDao.DaoLog> logs) {
        this.types = parseTypes(logs);
        this.location = parseLocation(logs);
        int overallReps = parseOverallReps(logs);
        this.overallPerformance = parseOverallPerformance(logs, overallReps);
        this.overallReps = overallReps;
        this.length = parseLength(logs);
    }

    @NonNull
    private List<Drill.Type> parseTypes(@NonNull List<PerformanceDao.DaoLog> logs) {
        return logs.stream()
                .map(PerformanceDao.DaoLog::getType)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    @Nullable
    private WorkoutLocation parseLocation(@NonNull List<PerformanceDao.DaoLog> logs) {
        int size = logs.size();
        return size != 0 ? logs.get(size - 1).getPerformance().getLocation() : null;
    }

    private int parseOverallReps(@NonNull List<PerformanceDao.DaoLog> logs) {
        return logs.stream()
                .map(PerformanceDao.DaoLog::getPerformance)
                .map(Performance::getReps)
                .reduce(0, (reps, next) -> reps + next);
    }

    private String parseOverallPerformance(@NonNull List<PerformanceDao.DaoLog> logs, int reps) {
        int total = reps == 0 ? 1 : reps;
        int count = logs.stream()
                .map(PerformanceDao.DaoLog::getPerformance)
                .map(Performance::getCount)
                .reduce(0, (sum, next) -> sum + next);
        return count * 100 / total + "%";
    }

    @NonNull
    private String parseLength(@NonNull List<PerformanceDao.DaoLog> logs) {
        int size = logs.size();
        return size != 0 ? String.valueOf(
                logs.get(0).getPerformance().getStartTime() - logs.get(size - 1)
                        .getPerformance().getEndTime()) : "0:00";
    }

    @NonNull
    public List<Drill.Type> getTypes() {
        return types;
    }

    @Nullable
    public WorkoutLocation getLocation() {
        return location;
    }

    @NonNull
    public String getOverallPerformance() {
        return overallPerformance;
    }

    public int getOverallReps() {
        return overallReps;
    }

    @NonNull
    public String getLength() {
        return length;
    }

    @NonNull
    @Override
    public String toString() {
        String workoutLocation = location != null ? location.toString() : "N/A";
        return String.format(Locale.getDefault(), "Log{@%s: %s, %s, %s}",
                workoutLocation, overallPerformance, length, types);
    }
}
