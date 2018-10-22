package com.armandgray.shared.model;

import com.armandgray.shared.db.PerformanceDao;
import com.armandgray.shared.helpers.DateHelper;

import java.util.Collection;
import java.util.Date;
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

    @NonNull
    private final String day;

    @NonNull
    private final String shortDay;

    public WorkoutInfo(@NonNull List<PerformanceDao.DaoLog> logs) {
        this.types = parseTypes(logs);
        this.location = parseLocation(logs);
        int overallReps = parseOverallReps(logs);
        this.overallPerformance = parseOverallPerformance(logs, overallReps);
        this.overallReps = overallReps;
        this.length = parseLength(logs);
        this.day = parseDay(logs, false);
        this.shortDay = parseDay(logs, true);
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
        return DateHelper.format(new Date(logs.stream()
                .map(PerformanceDao.DaoLog::getPerformance)
                .map(performance -> performance.getEndTime() - performance.getStartTime())
                .reduce(0L, (length, next) -> length + next)));
    }

    private String parseDay(@NonNull List<PerformanceDao.DaoLog> logs, boolean shortFormat) {
        if (logs.size() == 0) {
            return "";
        }

        Date date = new Date(logs.get(0).getPerformance().getStartTime());
        return shortFormat ? DateHelper.getShortSimpleDay(date) : DateHelper.getSimpleDay(date);
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

    @NonNull
    public String getShortDay() {
        return shortDay;
    }

    @NonNull
    public String getDay() {
        return day;
    }
}
