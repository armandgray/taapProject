package com.armandgray.shared.model;

import java.util.Locale;

import androidx.annotation.NonNull;

public class PerformanceRate {

    private static final int DEFAULT_MAX = 10;
    private static final float DEFAULT_SUCCESS_RATE = 0.75f;

    private int count;
    private int total;
    private int max;
    private final float successRate;

    PerformanceRate() {
        this.count = 0;
        this.total = 0;
        this.max = DEFAULT_MAX;
        this.successRate = DEFAULT_SUCCESS_RATE;
    }

    public PerformanceRate(PerformanceRate clone) {
        this.count = clone.count;
        this.total = clone.total;
        this.max = clone.max;
        this.successRate = clone.successRate;
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

    float getSuccessRate() {
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

    public void clear() {
        this.count = 0;
        this.total = 0;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%d/%d", count, total);
    }
}
