package com.armandgray.taap.utils;

import com.armandgray.taap.models.SessionLog;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.armandgray.taap.models.SessionLog.REST_TIME;
import static com.armandgray.taap.models.SessionLog.SESSION_LENGTH;
import static com.armandgray.taap.utils.DateTimeHelper.ONE_DAY;
import static com.armandgray.taap.utils.DateTimeHelper.ONE_HOUR;
import static com.armandgray.taap.utils.DateTimeHelper.getDateFormattedAsString;
import static com.armandgray.taap.utils.DateTimeHelper.getTimeElapsedAsDate;
import static com.armandgray.taap.utils.DateTimeHelper.getTotalTimeAsDate;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class DateTimeHelperTest {

    @Test
    public void canGetTimeElapsed() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, 0, 0, 0);
        long dummyTime = System.currentTimeMillis();
        calendar.setTimeInMillis(dummyTime);
        assertNotNull(getTimeElapsedAsDate(dummyTime));
        assertEquals(calendar.getTime(), getTimeElapsedAsDate(dummyTime));
    }

    @Test
    public void canGetTimeElapsed_Telescope_NumHours() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, 0, 0, 0);
        long dummyTime = System.currentTimeMillis();
        calendar.setTimeInMillis(dummyTime - (ONE_HOUR * 16) + ONE_DAY);
        assertNotNull(getTimeElapsedAsDate(dummyTime, 16));
        assertEquals(calendar.getTime(), getTimeElapsedAsDate(dummyTime, 16));
    }

    @Test
    public void doesZeroOutHoursForTimesLessThanOneHour_CanGetTimeElapsed() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, 0, 0, 0);

        assertNotNull(getTimeElapsedAsDate(calendar.getTimeInMillis()));
        assertEquals(calendar.getTime().toString(), getTimeElapsedAsDate(calendar.getTimeInMillis()).toString());
    }

    @Test
    public void canGetTotalTimeAsDate() throws Exception {
        ArrayList<SessionLog> logs = new ArrayList<>();
        logs.add(new SessionLog.Builder().activeWork(new Date(133353535L)).create());
        logs.add(new SessionLog.Builder().activeWork(new Date(1991991291L)).create());
        logs.add(new SessionLog.Builder().activeWork(new Date(10302939L)).create());

        long expectedTotal = 0L;
        for (SessionLog log : logs) { expectedTotal += log.getActiveWork().getTime(); }

        assertNotNull(getTotalTimeAsDate(logs));
        assertEquals(getTimeElapsedAsDate(expectedTotal) , getTotalTimeAsDate(logs));
    }

    @Test
    public void canGetTotalTimeAsDate_Telescope_Field() throws Exception {
        ArrayList<SessionLog> logs = new ArrayList<>();
        logs.add(new SessionLog.Builder()
                .sessionLength(new Date(133353535L))
                .restTime(new Date(13335322535L))
                .create());
        logs.add(new SessionLog.Builder()
                .sessionLength(new Date(1991991291L))
                .restTime(new Date(133355333335L))
                .create());
        logs.add(new SessionLog.Builder()
                .sessionLength(new Date(10302939L))
                .restTime(new Date(1322535L))
                .create());

        long expectedTotalLength = 0L;
        long expectedTotalRest = 0L;
        for (SessionLog log : logs) {
            expectedTotalLength += log.getSessionLength().getTime();
            expectedTotalRest += log.getRestTime().getTime();
        }

        assertNotNull(getTotalTimeAsDate(logs, SESSION_LENGTH));
        assertNotNull(getTotalTimeAsDate(logs, REST_TIME));
        assertEquals(getTimeElapsedAsDate(expectedTotalLength),
                getTotalTimeAsDate(logs, SESSION_LENGTH));
        assertEquals(getTimeElapsedAsDate(expectedTotalRest),
                getTotalTimeAsDate(logs, REST_TIME));
    }

    @Test
    public void canGetDateFormattedAsString() throws Exception {
        Date testDate = new Date(System.currentTimeMillis());
        assertNotNull(getDateFormattedAsString(testDate));
        assertEquals(new SimpleDateFormat("kk:mm:ss", Locale.US).format(testDate),
                getDateFormattedAsString(testDate));
    }

    @Test
    public void canGetDateFormattedAsString_ZeroHour() throws Exception {
        Date testDate = new Date(0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(testDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        SimpleDateFormat simpleDateFormat =
                hour == 0
                        ? new SimpleDateFormat("00:mm:ss", Locale.US)
                        : new SimpleDateFormat("hh:mm:ss", Locale.US);
        assertNotNull(getDateFormattedAsString(calendar.getTime()));
        assertEquals(simpleDateFormat.format(testDate),
                getDateFormattedAsString(calendar.getTime()));
    }

    @Test
    public void canGetDateFormattedAsString_DateZero() throws Exception {
        Date testDate = new Date(0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(testDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        SimpleDateFormat simpleDateFormat =
                hour == 0
                        ? new SimpleDateFormat("00:mm:ss", Locale.US)
                        : new SimpleDateFormat("hh:mm:ss", Locale.US);
        assertNotNull(getDateFormattedAsString(testDate));
        assertEquals(simpleDateFormat.format(testDate), getDateFormattedAsString(testDate));
    }

}