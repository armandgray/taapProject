package com.armandgray.shared.model;

import android.support.annotation.NonNull;

public class PerformanceRate {

    private String drill;
    private int count;
    private int total;
    private int max;

    public PerformanceRate(@NonNull String drill, int max) {
        if (max < 1) {
            throw new IllegalArgumentException("max < 1");
        }

        this.drill = drill;
        this.count = 0;
        this.total = 0;
        this.max = max;
    }

    public String getDrill() {
        return drill;
    }

    public int getCount() {
        return count;
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
}
