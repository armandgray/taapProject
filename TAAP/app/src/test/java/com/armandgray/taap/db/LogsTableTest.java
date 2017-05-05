package com.armandgray.taap.db;

import org.junit.Test;

import java.util.Arrays;

import static com.armandgray.taap.db.DrillsTable.DRILL_ID;
import static com.armandgray.taap.db.DrillsTable.TABLE_DRILLS;
import static com.armandgray.taap.db.LogsTable.ALL_COLUMNS;
import static com.armandgray.taap.db.LogsTable.COLUMN_ACTIVE_WORK;
import static com.armandgray.taap.db.LogsTable.COLUMN_DATE;
import static com.armandgray.taap.db.LogsTable.COLUMN_DRILL;
import static com.armandgray.taap.db.LogsTable.COLUMN_GOAL;
import static com.armandgray.taap.db.LogsTable.LOG_ID;
import static com.armandgray.taap.db.LogsTable.COLUMN_LENGTH;
import static com.armandgray.taap.db.LogsTable.COLUMN_REPS_COMPLETED;
import static com.armandgray.taap.db.LogsTable.COLUMN_REST_TIME;
import static com.armandgray.taap.db.LogsTable.COLUMN_SETS_COMPLETED;
import static com.armandgray.taap.db.LogsTable.COLUMN_SUCCESS;
import static com.armandgray.taap.db.LogsTable.SQL_CREATE;
import static com.armandgray.taap.db.LogsTable.SQL_DELETE;
import static com.armandgray.taap.db.LogsTable.TABLE_LOGS;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class LogsTableTest {

    @Test
    public void hasFields_TableColumns() throws Exception {
        String[] allColumns = {
                LOG_ID, COLUMN_DATE, COLUMN_LENGTH, COLUMN_GOAL, COLUMN_ACTIVE_WORK,
                COLUMN_REST_TIME, COLUMN_SETS_COMPLETED, COLUMN_SUCCESS, COLUMN_DRILL };

        assertNotNull(TABLE_LOGS);
        assertEquals("logs", TABLE_LOGS);
        assertNotNull(LOG_ID);
        assertEquals("logId", LOG_ID);
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
        for (String column : allColumns) {
            assertTrue(Arrays.asList(ALL_COLUMNS).contains(column));
        }
    }

    @Test
    public void hasField_SQLCreate() throws Exception {
        String expected =
                "CREATE TABLE " + TABLE_LOGS + " ("
                        + LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_DATE + " INTEGER,"
                        + COLUMN_LENGTH + " INTEGER, "
                        + COLUMN_GOAL + " TEXT, "
                        + COLUMN_ACTIVE_WORK + " INTEGER, "
                        + COLUMN_REST_TIME + " INTEGER, "
                        + COLUMN_SETS_COMPLETED + " INTEGER, "
                        + COLUMN_REPS_COMPLETED + " INTEGER, "
                        + COLUMN_SUCCESS + " REAL, "
                        + COLUMN_DRILL + " INTEGER NOT NULL, "
                            + "FOREIGN KEY (" + COLUMN_DRILL + ") REFERENCES "
                                + TABLE_DRILLS + " (" + DRILL_ID + ")"
                        + ")";

        assertNotNull(SQL_CREATE);
        assertEquals(expected, SQL_CREATE);
    }

    @Test
    public void hasField_SQLDelete() throws Exception {
        assertNotNull(SQL_DELETE);
        assertEquals("DROP TABLE " + TABLE_LOGS, SQL_DELETE);
    }

}