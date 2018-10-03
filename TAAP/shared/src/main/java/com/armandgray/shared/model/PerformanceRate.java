package com.armandgray.shared.model;

import android.support.annotation.NonNull;

import java.util.Locale;

public class PerformanceRate {

    private String drill;
    private int count;
    private int total;
    private int max;
    private final float successRate;

    public PerformanceRate(@NonNull String drill, int max, float successRate) {
        if (max < 1) {
            throw new IllegalArgumentException("max < 1");
        }

        this.drill = drill;
        this.count = 0;
        this.total = 0;
        this.max = max;
        this.successRate = successRate;
    }

    public String getDrill() {
        return drill;
    }

    public int getTotal() {
        return total;
    }

    public int getMax() {
        return max;
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

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%d/%d", count, total);
    }

    public void clear() {
        this.count = 0;
        this.total = 0;
    }
}
