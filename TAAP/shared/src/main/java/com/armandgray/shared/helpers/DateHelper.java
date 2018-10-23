package com.armandgray.shared.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

public class DateHelper {

    private static final SimpleDateFormat TIME_FORMATTER;
    private static final GregorianCalendar CALENDAR = new GregorianCalendar();


    static {
        TIME_FORMATTER = new SimpleDateFormat("H:mm:ss", Locale.getDefault());
    }

    private DateHelper() {
        // Strict Helper Class
    }

    @NonNull
    public static String format(Date date) {
        return TIME_FORMATTER.format(date);
    }

    @NonNull
    public static String getSimpleDay(@NonNull Date date) {
        long timeDifference = new Date().getTime() - date.getTime();
        long daysAway = TimeUnit.DAYS.convert(timeDifference, TimeUnit.MILLISECONDS);
        if (daysAway == 0) {
            return "Today";
        } else if (daysAway == 1) {
            return "Yesterday";
        } else if (daysAway < 5) {
            return getDay(date);
        } else if (daysAway < 15) {
            return getDayOfMonth(date);
        } else {
            CALENDAR.setTime(date);
            return (CALENDAR.get(Calendar.MONTH) + 1) + "/" + CALENDAR.get(Calendar.DAY_OF_MONTH);
        }
    }

    @NonNull
    public static String getShortSimpleDay(@NonNull Date date) {
        long timeDifference = new Date().getTime() - date.getTime();
        long daysAway = TimeUnit.DAYS.convert(timeDifference, TimeUnit.MILLISECONDS);
        if (daysAway == 0) {
            return "Today";
        } else if (daysAway == 1) {
            return "Yest.";
        } else if (daysAway < 5) {
            return getDay(date).substring(0, 3);
        } else if (daysAway < 15) {
            return getDayOfMonth(date);
        } else {
            CALENDAR.setTime(date);
            return (CALENDAR.get(Calendar.MONTH) + 1) + "/" + CALENDAR.get(Calendar.DAY_OF_MONTH);
        }
    }

    @NonNull
    private static String getDay(Date date) {
        CALENDAR.setTime(date);
        switch (CALENDAR.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                return "Sunday";

            case Calendar.MONDAY:
                return "Monday";

            case Calendar.TUESDAY:
                return "Tuesday";

            case Calendar.WEDNESDAY:
                return "Wednesday";

            case Calendar.THURSDAY:
                return "Thursday";

            case Calendar.FRIDAY:
                return "Friday";

            case Calendar.SATURDAY:
                return "Saturday";

            default:
                return "";
        }
    }

    @NonNull
    private static String getDayOfMonth(Date date) {
        CALENDAR.setTime(date);
        int dayOfMonth = CALENDAR.get(Calendar.DAY_OF_MONTH);
        return dayOfMonth + getDateSuffix(dayOfMonth);
    }

    private static String getDateSuffix(int dayOfMonth) {
        if (dayOfMonth == 1 || dayOfMonth == 21 || dayOfMonth == 31) {
            return "st";
        } else if (dayOfMonth == 2 || dayOfMonth == 22) {
            return "nd";
        } else if (dayOfMonth == 3 || dayOfMonth == 23) {
            return "rd";
        } else {
            return "th";
        }
    }
}
