package com.armandgray.taap.utils;

import com.armandgray.taap.models.SessionLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.armandgray.taap.models.SessionLog.ACTIVE_WORK;
import static com.armandgray.taap.models.SessionLog.REST_TIME;
import static com.armandgray.taap.models.SessionLog.SESSION_LENGTH;

public class DateTimeHelper {

    public static final long ONE_DAY = 86400000L;
    public static final long ONE_HOUR = 3600000L;
    static int i = 0;

    public static Date getTimeElapsedAsDate(long timeElapsed) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeElapsed - (ONE_HOUR * 8) + ONE_DAY);
        return calendar.getTime();
    }

    public static Date getTimeElapsedAsDate(long timeElapsed, boolean isSessionLogAdapter) {
        if (!isSessionLogAdapter) { return getTimeElapsedAsDate(timeElapsed); }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeElapsed - (ONE_HOUR * 16) + ONE_DAY);
        return calendar.getTime();
    }

    public static Date getTotalTimeAsDate(ArrayList<SessionLog> logs) {
        long expectedTotal = 0L;
        for (SessionLog log : logs) { expectedTotal += log.getActiveWork().getTime(); }
        return getTimeElapsedAsDate(expectedTotal);
    }

    public static Date getTotalTimeAsDate(ArrayList<SessionLog> logs, String field) {
        long expectedTotal = 0L;
        long time;
        for (SessionLog log : logs) {
            time = getDateForField(log, field).getTime();
            expectedTotal += time;
        }
        return getTimeElapsedAsDate(expectedTotal);
    }

    private static Date getDateForField(SessionLog log, String field) {
        switch (field) {
            case SESSION_LENGTH:
                return log.getSessionLength();

            case ACTIVE_WORK:
                return log.getActiveWork();

            case REST_TIME:
                return log.getRestTime();
        }
        return log.getActiveWork();
    }

    public static String getDateFormattedAsString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        SimpleDateFormat simpleDateFormat =
                hour == 0 || date.equals(new Date(0))
                        ? new SimpleDateFormat("00:mm:ss", Locale.US)
                        : new SimpleDateFormat("kk:mm:ss", Locale.US);
        return simpleDateFormat.format(date);
    }

}
