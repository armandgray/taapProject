package com.armandgray.taap.utils;

import org.junit.Test;

import java.util.Calendar;

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
        assertNotNull(controller.getTimeElapsedAsDate(dummyTime));
        assertEquals(calendar.getTime(), controller.getTimeElapsedAsDate(dummyTime));
    }

    @Test
    public void doesZeroOutHoursForTimesLessThanOneHour_CanGetTimeElapsed() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, 0, 0, 0);
        calendar.setTimeInMillis(1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        assertNotNull(controller.getTimeElapsedAsDate(1));
        assertEquals(calendar.getTime().toString(), controller.getTimeElapsedAsDate(1).toString());
    }

}