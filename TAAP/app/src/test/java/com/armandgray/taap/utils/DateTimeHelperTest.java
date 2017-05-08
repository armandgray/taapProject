package com.armandgray.taap.utils;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static com.armandgray.taap.utils.DateTimeHelper.getTimeElapsedAsDate;
import static com.armandgray.taap.utils.DateTimeHelper.getTotalTime;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class DateTimeHelperTest {

    @Test
    public void canGetTimeElapsed() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, 0, 0, 0);
        long dummyTime = System.currentTimeMillis();
        calendar.setTimeInMillis(dummyTime);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour > 12) { calendar.set(Calendar.HOUR_OF_DAY, 0); }
        assertNotNull(getTimeElapsedAsDate(dummyTime));
        assertEquals(calendar.getTime(), getTimeElapsedAsDate(dummyTime));
    }

    @Test
    public void doesZeroOutHoursForTimesLessThanOneHour_CanGetTimeElapsed() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, 0, 0, 0);
        calendar.setTimeInMillis(1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        assertNotNull(getTimeElapsedAsDate(1));
        assertEquals(calendar.getTime().toString(), getTimeElapsedAsDate(1).toString());
    }

    @Test
    public void canGetTotalTime() throws Exception {
        Date[] times = {new Date(133353535L), new Date(1991991291L), new Date(10302939)};
        long expectedTotal = 0L;
        for (Date time : times) { expectedTotal += time.getTime(); }

        assertNotNull(getTotalTime(times));
        assertEquals(expectedTotal , getTotalTime(times));
    }

}