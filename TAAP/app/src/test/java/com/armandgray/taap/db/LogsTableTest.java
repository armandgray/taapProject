package com.armandgray.taap.db;

import org.junit.Test;

import static com.armandgray.taap.db.LogsTable.ALL_COLUMNS;
import static com.armandgray.taap.db.LogsTable.COLUMN_ACTIVE_WORK;
import static com.armandgray.taap.db.LogsTable.COLUMN_DATE;
import static com.armandgray.taap.db.LogsTable.COLUMN_DRILL;
import static com.armandgray.taap.db.LogsTable.COLUMN_GOAL;
import static com.armandgray.taap.db.LogsTable.COLUMN_ID;
import static com.armandgray.taap.db.LogsTable.COLUMN_LENGTH;
import static com.armandgray.taap.db.LogsTable.COLUMN_REPS_COMPLETED;
import static com.armandgray.taap.db.LogsTable.COLUMN_REST_TIME;
import static com.armandgray.taap.db.LogsTable.COLUMN_SETS_COMPLETED;
import static com.armandgray.taap.db.LogsTable.COLUMN_SUCCESS;
import static com.armandgray.taap.db.LogsTable.TABLE_LOGS;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class LogsTableTest {

    @Test
    public void hasFields_TableColumns() throws Exception {
        String[] allColumns = {
                COLUMN_ID, COLUMN_DATE, COLUMN_LENGTH, COLUMN_GOAL, COLUMN_ACTIVE_WORK,
                COLUMN_REST_TIME, COLUMN_SETS_COMPLETED, COLUMN_SUCCESS, COLUMN_DRILL };

        assertNotNull(TABLE_LOGS);
        assertEquals("logs", TABLE_LOGS);
        assertNotNull(COLUMN_ID);
        assertEquals("logId", COLUMN_ID);
        assertNotNull(COLUMN_DATE);
        assertEquals("itemDate", COLUMN_DATE);
        assertNotNull(COLUMN_LENGTH);
        assertEquals("itemLength", COLUMN_LENGTH);
        assertNotNull(COLUMN_GOAL);
        assertEquals("itemGoal", COLUMN_GOAL);
        assertNotNull(COLUMN_ACTIVE_WORK);
        assertEquals("itemActiveWork", COLUMN_ACTIVE_WORK);
        assertNotNull(COLUMN_REST_TIME);
        assertEquals("itemRestTime", COLUMN_REST_TIME);
        assertNotNull(COLUMN_SETS_COMPLETED);
        assertEquals("itemSetsCompleted", COLUMN_SETS_COMPLETED);
        assertNotNull(COLUMN_REPS_COMPLETED);
        assertEquals("itemRepsCompleted", COLUMN_REPS_COMPLETED);
        assertNotNull(COLUMN_SUCCESS);
        assertEquals("itemSuccess", COLUMN_SUCCESS);
        assertNotNull(COLUMN_DRILL);
        assertEquals("itemDrill", COLUMN_DRILL);
        assertNotNull(ALL_COLUMNS);
        assertEquals(allColumns, ALL_COLUMNS);
    }

}