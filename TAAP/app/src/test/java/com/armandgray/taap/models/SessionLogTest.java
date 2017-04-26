package com.armandgray.taap.models;

import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertNotNull;

public class SessionLogTest {

    @Test
    public void canCreateSessionLogWithBuilder() throws Exception {
        SessionLog sessionLog = new SessionLog.Builder()
                .sessionLength(new Date(0))
                .sessionGoal(new Date(0))
                .activeWork(new Date(0))
                .restTime(new Date(0))
                .setsCompleted(0)
                .repsCompleted(0)
                .successRate(0.0)
                .successRecord(0.0)
                .create();
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

}