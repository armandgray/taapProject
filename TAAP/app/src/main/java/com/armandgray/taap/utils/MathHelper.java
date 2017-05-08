package com.armandgray.taap.utils;

import java.util.ArrayList;

public class MathHelper {

    public static double getAveragePercentage(ArrayList<Double> percentages) {
        double total = 0.0;
        for (Double percent : percentages ) { total += percent; }
        return total/percentages.size();
    }
}
