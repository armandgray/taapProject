package com.armandgray.taap.db;

class LogsTable {

    static final String TABLE_LOGS = "logs";
    static final String LOG_ID = "logId";
    static final String COLUMN_DATE = "itemDate";
    static final String COLUMN_LENGTH = "itemLength";
    static final String COLUMN_GOAL = "itemGoal";
    static final String COLUMN_ACTIVE_WORK = "itemActiveWork";
    static final String COLUMN_REST_TIME = "itemRestTime";
    static final String COLUMN_SETS_COMPLETED = "itemSetsCompleted";
    static final String COLUMN_REPS_COMPLETED = "itemRepsCompleted";
    static final String COLUMN_SUCCESS = "itemSuccess";
    static final String COLUMN_DRILL = "itemDrill";
    static final String[] ALL_COLUMNS = {
            LOG_ID, COLUMN_DATE, COLUMN_LENGTH, COLUMN_GOAL, COLUMN_ACTIVE_WORK,
            COLUMN_REST_TIME, COLUMN_SETS_COMPLETED, COLUMN_SUCCESS, COLUMN_DRILL };

    static final String SQL_CREATE = "";

    }
