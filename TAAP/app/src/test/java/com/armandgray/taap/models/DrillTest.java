package com.armandgray.taap.models;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

public class DrillTest {

    @Test
    public void canCreateDrill() throws Exception {
        assertNotNull(new Drill());
    }

}