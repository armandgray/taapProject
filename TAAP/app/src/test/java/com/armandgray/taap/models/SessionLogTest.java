package com.armandgray.taap.models;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

public class SessionLogTest {

    @Test
    public void canCreateSessionLogWithBuilder() throws Exception {
        SessionLog sessionLog = new SessionLog.Builder().create();
        assertNotNull(sessionLog);
    }

}