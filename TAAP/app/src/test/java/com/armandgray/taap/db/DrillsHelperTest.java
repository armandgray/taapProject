package com.armandgray.taap.db;

import org.junit.Test;

import static com.armandgray.taap.db.DrillsDataHelper.getDrillsList;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class DrillsHelperTest {

    @Test
    public void canGetDrillsList() throws Exception {
        assertNotNull(getDrillsList());
        assertTrue(getDrillsList().size() > 0);
    }


}