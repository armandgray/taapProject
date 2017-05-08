package com.armandgray.taap.utils;

import com.armandgray.taap.models.SessionLog;

import org.junit.Test;

import java.util.ArrayList;

import static com.armandgray.taap.utils.MathHelper.getAveragePercentage;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class MathHelperTest {

    @Test
    public void canGetAveragePercentage() throws Exception {
        ArrayList<SessionLog> logs = new ArrayList<>();
        logs.add(new SessionLog.Builder().successRate(0.20).create());
        logs.add(new SessionLog.Builder().successRate(0.40).create());
        logs.add(new SessionLog.Builder().successRate(0.90).create());
        double total = 0.0;
        for (SessionLog log : logs ) { total += log.getSuccessRate(); }

        assertNotNull(getAveragePercentage(logs));
        assertEquals(total/logs.size() , getAveragePercentage(logs));
    }

}