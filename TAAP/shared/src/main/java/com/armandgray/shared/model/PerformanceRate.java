package com.armandgray.shared.model;

import androidx.annotation.NonNull;

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

    public PerformanceRate(PerformanceRate clone) {
        this.drill = clone.drill;
        this.count = clone.count;
        this.total = clone.total;
        this.max = clone.max;
        this.successRate = clone.successRate;
    }

    public String getDrill() {
        return this.drill;
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%d/%d", count, total);
    }
}
