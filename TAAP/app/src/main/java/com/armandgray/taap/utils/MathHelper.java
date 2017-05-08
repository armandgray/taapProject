package com.armandgray.taap.utils;

import com.armandgray.taap.models.SessionLog;

import java.util.ArrayList;

public class MathHelper {

    public static double getAveragePercentage(ArrayList<SessionLog> logs) {
        double total = 0.0;
//        for (Double percent : logs) { total += percent; }
        return total/ logs.size();
    }
}
