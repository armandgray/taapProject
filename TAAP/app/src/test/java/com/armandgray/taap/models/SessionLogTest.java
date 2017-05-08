package com.armandgray.taap.models;

import android.os.Parcelable;

import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class SessionLogTest {

    @Test
    public void canCreateSessionLogWithBuilder() throws Exception {
        SessionLog sessionLog = new SessionLog.Builder()
                .sessionLength(new Date(1))
                .sessionGoal("")
                .activeWork(new Date(4))
                .restTime(new Date(5))
                .setsCompleted(4)
                .repsCompleted(3)
                .successRate(0.23)
                .successRecord(0.55)
                .drill(new Drill("", 0, Drill.SHOOTING_ARRAY))
                .create();
        assertNotNull(sessionLog);
        assertNotNull(sessionLog.getSessionId());
        assertNotNull(sessionLog.getSessionDate());
        assertNotNull(sessionLog.getSessionLength());
        assertNotNull(sessionLog.getSessionGoal());
        assertNotNull(sessionLog.getActiveWork());
        assertNotNull(sessionLog.getRestTime());
        assertNotNull(sessionLog.getSetsCompleted());
        assertNotNull(sessionLog.getRepsCompleted());
        assertNotNull(sessionLog.getSuccessRate());
        assertNotNull(sessionLog.getSuccessRecord());
        assertNotNull(sessionLog.getDrill());
    }

    @Test
    public void canCreateSessionLogWithDefaultValues() throws Exception {
        SessionLog sessionLog = new SessionLog.Builder().create();
        assertNotNull(sessionLog);
        assertNotNull(sessionLog.getSessionDate());
        assertNotNull(sessionLog.getSessionLength());
        assertNotNull(sessionLog.getSessionGoal());
        assertNotNull(sessionLog.getActiveWork());
        assertNotNull(sessionLog.getRestTime());
        assertNotNull(sessionLog.getSetsCompleted());
        assertNotNull(sessionLog.getRepsCompleted());
        assertNotNull(sessionLog.getSuccessRate());
        assertNotNull(sessionLog.getSuccessRecord());
    }

    @Test
    public void canSetDrillId() throws Exception {
        SessionLog sessionLog = new SessionLog.Builder().create();
        sessionLog.setSessionId(100);
        assertNotNull(sessionLog);
        assertNotNull(sessionLog.getSessionId());
        assertEquals(100, sessionLog.getSessionId());
    }

    @Test
    public void canSetDate() throws Exception {
        SessionLog sessionLog = new SessionLog.Builder().create();
        sessionLog.setSessionDate(new Date(12313123123L));
        assertNotNull(sessionLog);
        assertNotNull(sessionLog.getSessionDate());
        assertEquals(new Date(12313123123L), sessionLog.getSessionDate());
    }

    @Test
    public void canGetFieldCount() throws Exception {
        assertEquals(9, SessionLog.getFieldCount());
    }

    @Test
    public void implementsParcelable() throws Exception {
        Parcelable sessionLog = new SessionLog.Builder().create();
        assertNotNull(sessionLog);
    }

}