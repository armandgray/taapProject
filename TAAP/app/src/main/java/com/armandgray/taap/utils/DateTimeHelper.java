package com.armandgray.taap.utils;

import com.armandgray.taap.models.SessionLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.armandgray.taap.models.SessionLog.ACTIVE_WORK;
import static com.armandgray.taap.models.SessionLog.ALL_FIELDS;
import static com.armandgray.taap.models.SessionLog.REST_TIME;
import static com.armandgray.taap.models.SessionLog.SESSION_LENGTH;

public class DateTimeHelper {

    public static final long ONE_DAY = 86400000L;
    public static final long ONE_HOUR = 3600000L;

    public static Date getTimeElapsedAsDate(long timeElapsed) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeElapsed);
        return calendar.getTime();
    }

    public static Date getTimeElapsedAsDate(long timeElapsed, int hoursToSubtract) {
        if (hoursToSubtract == 0) { return getTimeElapsedAsDate(timeElapsed); }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeElapsed - (ONE_HOUR * hoursToSubtract) + ONE_DAY);
        return calendar.getTime();
    }

    public static Date getTotalTimeAsDate(ArrayList<SessionLog> logs) {
        long expectedTotal = 0L;
        for (SessionLog log : logs) { expectedTotal += log.getActiveWork().getTime(); }
        long extraHoursAddedForPositiveTimesInMillis = ONE_HOUR * 8 * (logs.size() - 1);
        expectedTotal -= extraHoursAddedForPositiveTimesInMillis;
        return getTimeElapsedAsDate(expectedTotal);
    }

    public static Date getTotalTimeAsDate(ArrayList<SessionLog> logs, String field) {
        if (field == null || !ALL_FIELDS.contains(field)) { return getTotalTimeAsDate(logs); }
        long expectedTotal = 0L;
        for (SessionLog log : logs) { expectedTotal += getDateForField(log, field).getTime(); }
        long extraHoursAddedForPositiveTimesInMillis = ONE_HOUR * 8 * (logs.size() - 1);
        expectedTotal -= extraHoursAddedForPositiveTimesInMillis;
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
