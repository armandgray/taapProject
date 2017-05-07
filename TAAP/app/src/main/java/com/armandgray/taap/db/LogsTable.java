package com.armandgray.taap.db;

import static com.armandgray.taap.db.DrillsTable.DRILL_ID;
import static com.armandgray.taap.db.DrillsTable.TABLE_DRILLS;

public class LogsTable {

    public static final String TABLE_LOGS = "logs";
    public static final String LOG_ID = "logId";
    public static final String COLUMN_DATE = "itemDate";
    public static final String COLUMN_LENGTH = "itemLength";
    public static final String COLUMN_GOAL = "itemGoal";
    public static final String COLUMN_ACTIVE_WORK = "itemActiveWork";
    public static final String COLUMN_REST_TIME = "itemRestTime";
    public static final String COLUMN_SETS_COMPLETED = "itemSetsCompleted";
    public static final String COLUMN_REPS_COMPLETED = "itemRepsCompleted";
    public static final String COLUMN_SUCCESS = "itemSuccess";
    public static final String COLUMN_DRILL = "itemDrill";
    public static final String[] ALL_LOG_COLUMNS = {
            LOG_ID, COLUMN_DATE, COLUMN_LENGTH, COLUMN_GOAL, COLUMN_ACTIVE_WORK, COLUMN_REST_TIME,
            COLUMN_SETS_COMPLETED, COLUMN_REPS_COMPLETED, COLUMN_SUCCESS, COLUMN_DRILL };

    static final String SQL_CREATE =
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

    static final String SQL_DELETE = "DROP TABLE " + TABLE_LOGS;
}
