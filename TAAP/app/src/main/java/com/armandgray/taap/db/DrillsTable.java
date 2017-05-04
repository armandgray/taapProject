package com.armandgray.taap.db;

class DrillsTable {

    static final String TABLE_DRILLS = "drills";
    static final String COLUMN_ID = "drillId";
    static final String COLUMN_TITLE = "itemTitle";
    static final String COLUMN_IMAGE_ID = "itemImageId";
    static final String COLUMN_CATEGORY = "itemCategory";

    static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_DRILLS + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_TITLE + " TEXT, "
                    + COLUMN_IMAGE_ID + " INTEGER,"
                    + COLUMN_CATEGORY + " TEXT"
                    + ")";

    static final String SQL_DELETE = "DROP TABLE " + TABLE_DRILLS;
}
