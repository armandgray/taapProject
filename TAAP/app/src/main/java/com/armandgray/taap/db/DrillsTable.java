package com.armandgray.taap.db;

public class DrillsTable {

    public static final String TABLE_DRILLS = "drills";
    public static final String DRILL_ID = "drillId";
    public static final String COLUMN_TITLE = "itemTitle";
    public static final String COLUMN_IMAGE_ID = "itemImageId";
    public static final String COLUMN_CATEGORY = "itemCategory";
    public static final String[] ALL_DRILL_COLUMNS = {
            DRILL_ID, COLUMN_TITLE, COLUMN_IMAGE_ID, COLUMN_CATEGORY };

    static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_DRILLS + " ("
                    + DRILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_TITLE + " TEXT, "
                    + COLUMN_IMAGE_ID + " INTEGER,"
                    + COLUMN_CATEGORY + " TEXT"
                    + ")";

    static final String SQL_DELETE = "DROP TABLE " + TABLE_DRILLS;
}
