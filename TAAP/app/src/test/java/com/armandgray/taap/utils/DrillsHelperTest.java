package com.armandgray.taap.utils;

import org.junit.Test;

import static com.armandgray.taap.utils.DrillsHelper.getDrillsList;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class DrillsHelperTest {

    @Test
    public void canGetDrillsList() throws Exception {
        assertNotNull(getDrillsList());
        assertTrue(getDrillsList().size() > 0);
    }


}