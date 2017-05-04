package com.armandgray.taap.db;

import org.junit.Test;

import static com.armandgray.taap.db.DrillsTable.COLUMN_CATEGORY;
import static com.armandgray.taap.db.DrillsTable.COLUMN_ID;
import static com.armandgray.taap.db.DrillsTable.COLUMN_IMAGE_ID;
import static com.armandgray.taap.db.DrillsTable.COLUMN_TITLE;
import static com.armandgray.taap.db.DrillsTable.SQL_CREATE;
import static com.armandgray.taap.db.DrillsTable.SQL_DELETE;
import static com.armandgray.taap.db.DrillsTable.TABLE_DRILLS;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class DrillsTableTest {

    @Test
    public void hasFields_TableColumns() throws Exception {
        assertNotNull(TABLE_DRILLS);
        assertEquals("drills", TABLE_DRILLS);
        assertNotNull(COLUMN_ID);
        assertEquals("drillId", COLUMN_ID);
        assertNotNull(COLUMN_TITLE);
        assertEquals("itemTitle", COLUMN_TITLE);
        assertNotNull(COLUMN_IMAGE_ID);
        assertEquals("itemImageId", COLUMN_IMAGE_ID);
        assertNotNull(COLUMN_CATEGORY);
        assertEquals("itemCategory", COLUMN_CATEGORY);
    }

    @Test
    public void hasField_SQLCreate() throws Exception {
        String expected =
                "CREATE TABLE " + TABLE_DRILLS + " ("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_TITLE + " TEXT, "
                        + COLUMN_IMAGE_ID + " INTEGER,"
                        + COLUMN_CATEGORY + " TEXT"
                        + ")";

        assertNotNull(SQL_CREATE);
        assertEquals(expected, SQL_CREATE);
    }

    @Test
    public void hasField_SQLDelete() throws Exception {
        assertNotNull(SQL_DELETE);
        assertEquals("DROP TABLE " + TABLE_DRILLS, SQL_DELETE);
    }

}