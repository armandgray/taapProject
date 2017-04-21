package com.armandgray.taap.utils;

import org.junit.Test;

import static com.armandgray.taap.utils.DrillsHelper.getDrillsList;
import static junit.framework.Assert.assertNotNull;

public class DrillsHelperTest {

    @Test
    public void canGetDrillsList() throws Exception {
        assertNotNull(getDrillsList());
    }


}