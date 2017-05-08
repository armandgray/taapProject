package com.armandgray.taap.utils;

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

}
