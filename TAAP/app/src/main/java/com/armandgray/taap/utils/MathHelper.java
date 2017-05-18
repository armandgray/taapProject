package com.armandgray.taap.utils;

import com.armandgray.taap.models.SessionLog;

import java.util.ArrayList;

public class MathHelper {

    public static double getAveragePercentage(ArrayList<SessionLog> logs) {
        double total = 0.0;
        for (SessionLog log : logs ) { total += log.getSuccessRate(); }
        return total/ logs.size();
    }

    public static String getPercentFormattedAsString(double decimal) {
        Double percent = decimal * 100;
        return percent.intValue() + "%";
    }
}
