package com.armandgray.taap.utils;

import com.armandgray.taap.models.SessionLog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateTimeHelper {

    public static Date getTimeElapsedAsDate(long timeElapsed) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, 0, 0, 0);
        if (timeElapsed != 0) { calendar.setTimeInMillis(timeElapsed); }
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour > 12) { calendar.set(Calendar.HOUR_OF_DAY, 0); }
        return calendar.getTime();
    }

    public static Date getTotalTimeAsDate(ArrayList<SessionLog> logs) {
        long expectedTotal = 0L;
        for (SessionLog log : logs) { expectedTotal += log.getActiveWork().getTime(); }
        return getTimeElapsedAsDate(expectedTotal);
    }

    public static String getDateFormattedAsString(Date testDate) {
        return null;
    }

}
