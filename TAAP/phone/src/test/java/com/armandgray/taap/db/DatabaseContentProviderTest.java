package com.armandgray.taap.db;

import com.armandgray.shared.model.Drill;
import com.armandgray.taap.R;
import com.armandgray.taap.models.SessionLog;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static com.armandgray.taap.db.DatabaseContentProvider.ALL_TABLE_COLUMNS;
import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_DELETE_ALL_DATA;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DatabaseContentProviderTest {

    private static final long TIME_IN_MILLIS = 1494179392802L;
    private static final Drill TEST_DRILL = new Drill(
            "5 Spots Shooting (Mid-Range)",
            R.drawable.ic_account_multiple_outline_white_48dp,
            Drill.Type.SHOOTING_ONLY);

    public static final SessionLog TEST_SESSION_LOG = new SessionLog.Builder()
            .sessionLength(new Date(TIME_IN_MILLIS))
            .sessionGoal("")
            .activeWork(new Date(TIME_IN_MILLIS + 555555))
            .restTime(new Date(TIME_IN_MILLIS + 111111))
            .setsCompleted(4)
            .repsCompleted(3)
            .successRate(0.23)
            .successRecord(0.55)
            .drill(TEST_DRILL)
            .create();

    @Test @Ignore
    public void hasField_ALL_TABLE_COLUMNS() {
        ArrayList<String> expectedColumns = new ArrayList<>();
        expectedColumns.addAll(Arrays.asList(LogsTable.ALL_LOG_COLUMNS));
        expectedColumns.addAll(Arrays.asList(DrillsTable.ALL_DRILL_COLUMNS));
        String[] expectedArray = expectedColumns.toArray(new String[expectedColumns.size()]);

        assertNotNull(ALL_TABLE_COLUMNS);
        assertThat(expectedArray, is(ALL_TABLE_COLUMNS));
    }

//    @Test @Ignore
//    public void hasContentUri_All() {
//        assertNotNull(CONTENT_URI_ALL);
//        assertEquals(Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_ALL), CONTENT_URI_ALL);
//    }
//
//    @Test @Ignore
//    public void hasContentUri_Drills() {
//        assertNotNull(CONTENT_URI_DRILLS);
//        assertEquals(Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_DRILLS), CONTENT_URI_DRILLS);
//    }
//
//    @Test @Ignore
//    public void hasContentUri_Logs() {
//        assertNotNull(CONTENT_URI_LOGS);
//        assertEquals(Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_LOGS), CONTENT_URI_LOGS);
//    }
//
//    @Test @Ignore
//    public void hasContentUri_DeleteAllData() {
//        assertNotNull(CONTENT_URI_DELETE_ALL_DATA);
//        assertEquals(Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_DELETE_ALL_DATA),
//                CONTENT_URI_DELETE_ALL_DATA);
//    }
//
//    @Test @Ignore
//    public void hasContentUriMatcher() {
//        assertNotNull(uriMatcher);
//        assertEquals(ALL_DATA, uriMatcher.match(CONTENT_URI_ALL));
//        assertEquals(ALL_DATA_DRILL_ID,
//                uriMatcher.match(Uri.parse(DatabaseContentProvider.CONTENT_URI_ALL + "/" + 0)));
//        assertEquals(ALL_DRILLS, uriMatcher.match(CONTENT_URI_DRILLS));
//        assertEquals(DRILLS_ID,
//                uriMatcher.match(Uri.parse(DatabaseContentProvider.CONTENT_URI_DRILLS + "/" + 0)));
//        assertEquals(ALL_LOGS, uriMatcher.match(CONTENT_URI_LOGS));
//        assertEquals(LOGS_ID,
//                uriMatcher.match(Uri.parse(DatabaseContentProvider.CONTENT_URI_LOGS + "/" + 0)));
//        assertEquals(DELETE_ALL, uriMatcher.match(CONTENT_URI_DELETE_ALL_DATA));
//    }
//
//    @Test @Ignore
//    public void doesExtendContentProvider() {
//        ContentProvider databaseContentProvider = new DatabaseContentProvider();
//        assertNotNull(databaseContentProvider);
//    }
//
//    @Test @Ignore
//    public void doesAssignWritableDatabase_TestOnCreate() {
//        assertNotNull(getDatabaseContentProvider());
//        assertNotNull(getDatabaseContentProvider().database);
//    }
//
//    @Test @Ignore
//    public void doesAssignWritableDatabase_WithDrillsAndLogsTables_TestOnCreate() {
//        Cursor cursor = getDatabaseContentProvider().database
//                .rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
//        assertTrue(cursor.moveToFirst());
//        ArrayList<String> listTableNames = new ArrayList<>();
//        while (!cursor.isAfterLast()) {
//            listTableNames.add(cursor.getString(0));
//            cursor.moveToNext();
//        }
//        cursor.close();
//
//        assertTrue(listTableNames.contains(DrillsTable.TABLE_DRILLS));
//        assertTrue(listTableNames.contains(LogsTable.TABLE_LOGS));
//    }
//
//    @Test @Ignore
//    public void canQueryDatabaseForDrillUsingContentProvider() {
//        insertDrillToDatabase(TEST_DRILL, RuntimeEnvironment.application);
//
//        String selectedDrill = DrillsTable.DRILL_ID + " = " + TEST_DRILL.getId();
//        Cursor cursor = RuntimeEnvironment.application.getContentResolver()
//                .query(CONTENT_URI_DRILLS, DrillsTable.ALL_DRILL_COLUMNS, selectedDrill,
//                        null, null);
//
//        assertNotNull(cursor);
//        assertCursorDataEqualsDrill(cursor, TEST_DRILL);
//        cursor.close();
//    }
//
//    @Test @Ignore
//    public void canQueryDatabaseForDrill_UsingDrillIdContentUri() {
//        insertDrillToDatabase(TEST_DRILL, RuntimeEnvironment.application);
//
//        String selectedDrill = DrillsTable.DRILL_ID + " = " + TEST_DRILL.getId();
//        Uri uri = Uri.parse(CONTENT_URI_DRILLS + "/" + TEST_DRILL.getId());
//        Cursor cursor = RuntimeEnvironment.application.getContentResolver()
//                .query(uri, DrillsTable.ALL_DRILL_COLUMNS, selectedDrill, null, null);
//
//        assertNotNull(cursor);
//        assertCursorDataEqualsDrill(cursor, TEST_DRILL);
//        cursor.close();
//    }
//
//    @Test @Ignore
//    public void canGetTypeForUriUsingContentProvider() {
//        assertEquals(DrillsTable.TABLE_DRILLS, RuntimeEnvironment.application.getContentResolver()
//                .getType(CONTENT_URI_DRILLS));
//        assertEquals(DrillsTable.TABLE_DRILLS, RuntimeEnvironment.application.getContentResolver()
//                .getType(Uri.parse(CONTENT_URI_DRILLS + "/" + 0)));
//        assertEquals(LogsTable.TABLE_LOGS, RuntimeEnvironment.application.getContentResolver()
//                .getType(CONTENT_URI_LOGS));
//        assertEquals(LogsTable.TABLE_LOGS, RuntimeEnvironment.application.getContentResolver()
//                .getType(Uri.parse(CONTENT_URI_LOGS + "/" + 0)));
//    }
//
//    @Test @Ignore
//    public void canInsertDrillIntoDatabaseUsingContentProvider() {
//        insertDrillToDatabase(TEST_DRILL, RuntimeEnvironment.application);
//
//        Cursor cursor = (new DatabaseOpenHelper(RuntimeEnvironment.application))
//                .getReadableDatabase()
//                .query(DrillsTable.TABLE_DRILLS, DrillsTable.ALL_DRILL_COLUMNS,
//                        null, null, null, null, null);
//
//        assertTrue(cursor.moveToNext());
//        assertCursorDataEqualsDrill(cursor, TEST_DRILL);
//        cursor.close();
//    }
//
//    @Test @Ignore
//    public void canDeleteDrillFromDatabaseUsingContentProvider() {
//        insertDrillToDatabase(TEST_DRILL, RuntimeEnvironment.application);
//
//        String selectedDrill = DrillsTable.DRILL_ID + " = " + TEST_DRILL.getId();
//        Uri uri = Uri.parse(CONTENT_URI_DRILLS + "/" + TEST_DRILL.getId());
//        RuntimeEnvironment.application.getContentResolver()
//                .delete(uri, selectedDrill, null);
//
//        assertFalse(getDatabaseContentProvider()
//                .database
//                .rawQuery("SELECT * FROM drills", null)
//                .moveToFirst());
//    }
//
//    @Test @Ignore
//    public void cannotDeleteDrillFromDatabase_UsingAllDrillContentUri() {
//        assertEquals(EXECUTION_FAILURE, RuntimeEnvironment.application.getContentResolver()
//                .delete(CONTENT_URI_DRILLS, null, null));
//    }
//
//    @Test @Ignore
//    public void canUpdateDrillFromDatabase_UsingDrillIdContentUri() {
//        insertDrillToDatabase(TEST_DRILL, RuntimeEnvironment.application);
//
//        String selectedDrill = DrillsTable.DRILL_ID + " = " + TEST_DRILL.getId();
//        ContentResolver contentResolver = RuntimeEnvironment.application.getContentResolver();
//        Cursor cursor = contentResolver.query(CONTENT_URI_DRILLS, DrillsTable.ALL_DRILL_COLUMNS,
//                selectedDrill, null, null);
//
//        assertNotNull(cursor);
//        assertCursorDataEqualsDrill(cursor, TEST_DRILL);
//
//        Drill updatedDrill = new Drill(
//                "Pass & Pass Back (Left Layup)",
//                R.drawable.ic_fitness_center_white_24dp,
//                Drill.PASSING_ARRAY);
//        ContentValues updatedValues = getDrillContentValues(updatedDrill);
//        Uri uri = Uri.parse(CONTENT_URI_DRILLS + "/" + TEST_DRILL.getId());
//        contentResolver.update(uri, updatedValues, selectedDrill, null);
//        updatedDrill.setId(TEST_DRILL.getId());
//
//        cursor = contentResolver.query(CONTENT_URI_DRILLS,
//                DrillsTable.ALL_DRILL_COLUMNS, selectedDrill, null, null);
//
//        assertNotNull(cursor);
//        assertCursorDataEqualsDrill(cursor, updatedDrill);
//        cursor.close();
//    }
//
//    @Test @Ignore
//    public void cannotUpdateDrillFromDatabase_UsingAllDrillsContentUri() {
//        assertEquals(EXECUTION_FAILURE, RuntimeEnvironment.application.getContentResolver()
//                .update(CONTENT_URI_DRILLS, null, null, null));
//    }
//
//    @Test @Ignore
//    public void canQueryDatabaseForLogUsingContentProvider() {
//        insertLogToDatabase(TEST_SESSION_LOG, RuntimeEnvironment.application);
//
//        String selectedLog = LogsTable.LOG_ID + " = " + TEST_SESSION_LOG.getSessionId();
//        Cursor cursor = RuntimeEnvironment.application.getContentResolver()
//                .query(CONTENT_URI_LOGS, LogsTable.ALL_LOG_COLUMNS, selectedLog,
//                        null, null);
//
//        assertNotNull(cursor);
//        assertCursorDataEqualsLog(cursor, TEST_SESSION_LOG);
//        cursor.close();
//    }
//
//    @Test @Ignore
//    public void canQueryDatabaseForLog_UsingLogIdContentUri() {
//        insertLogToDatabase(TEST_SESSION_LOG, RuntimeEnvironment.application);
//
//        String selectedLog = LogsTable.LOG_ID + " = " + TEST_SESSION_LOG.getSessionId();
//        Uri uri = Uri.parse(CONTENT_URI_LOGS + "/" + TEST_SESSION_LOG.getSessionId());
//        Cursor cursor = RuntimeEnvironment.application.getContentResolver()
//                .query(uri, LogsTable.ALL_LOG_COLUMNS, selectedLog,
//                        null, null);
//
//        assertNotNull(cursor);
//        assertCursorDataEqualsLog(cursor, TEST_SESSION_LOG);
//        cursor.close();
//    }
//
//    @Test @Ignore
//    public void canInsertLogIntoDatabaseUsingContentProvider() {
//        insertLogToDatabase(TEST_SESSION_LOG, RuntimeEnvironment.application);
//
//        Cursor cursor = (new DatabaseOpenHelper(RuntimeEnvironment.application))
//                .getReadableDatabase()
//                .query(LogsTable.TABLE_LOGS, LogsTable.ALL_LOG_COLUMNS,
//                        null, null, null, null, null);
//
//        assertTrue(cursor.moveToFirst());
//        assertCursorDataEqualsLog(cursor, TEST_SESSION_LOG);
//        cursor.close();
//    }
//
//    @Test @Ignore
//    public void canDeleteLogFromDatabaseUsingContentProvider() {
//        insertLogToDatabase(TEST_SESSION_LOG, RuntimeEnvironment.application);
//
//        String selectedLog = LogsTable.LOG_ID + " = " + TEST_SESSION_LOG.getSessionId();
//        Uri uri = Uri.parse(CONTENT_URI_LOGS + "/" + TEST_SESSION_LOG.getSessionId());
//        RuntimeEnvironment.application.getContentResolver()
//                .delete(uri, selectedLog, null);
//
//        assertFalse(getDatabaseContentProvider()
//                .database
//                .rawQuery("SELECT * FROM logs", null)
//                .moveToFirst());
//    }
//
//    @Test @Ignore
//    public void cannotDeleteLogFromDatabase_UsingAllLogsContentUri() {
//        assertEquals(EXECUTION_FAILURE, RuntimeEnvironment.application.getContentResolver()
//                .delete(CONTENT_URI_LOGS, null, null));
//    }
//
//    @Test @Ignore
//    public void canUpdateLogFromDatabase_UsingLogIdContentUri() {
//        insertLogToDatabase(TEST_SESSION_LOG, RuntimeEnvironment.application);
//
//        String selectedLog = LogsTable.LOG_ID + " = " + TEST_SESSION_LOG.getSessionId();
//        ContentResolver contentResolver = RuntimeEnvironment.application.getContentResolver();
//        Cursor cursor = contentResolver.query(CONTENT_URI_LOGS, LogsTable.ALL_LOG_COLUMNS,
//                selectedLog, null, null);
//
//        assertNotNull(cursor);
//        assertCursorDataEqualsLog(cursor, TEST_SESSION_LOG);
//
//        SessionLog updatedLog = new SessionLog.Builder()
//                .sessionLength(new Date(TIME_IN_MILLIS))
//                .sessionGoal(String.valueOf(TIME_IN_MILLIS))
//                .activeWork(new Date(TIME_IN_MILLIS + 55225555))
//                .restTime(new Date(TIME_IN_MILLIS + 11122111))
//                .setsCompleted(43)
//                .repsCompleted(30)
//                .successRate(0.93)
//                .successRecord(1.00)
//                .drill(TEST_DRILL)
//                .createBehavior();
//        ContentValues updatedValues = getLogContentValues(updatedLog);
//        Uri uri = Uri.parse(CONTENT_URI_LOGS + "/" + TEST_SESSION_LOG.getSessionId());
//        contentResolver.update(uri, updatedValues, selectedLog, null);
//        updatedLog.setSessionId(TEST_SESSION_LOG.getSessionId());
//
//        cursor = contentResolver.query(CONTENT_URI_LOGS, LogsTable.ALL_LOG_COLUMNS, selectedLog,
//                null, null);
//
//        assertNotNull(cursor);
//        assertCursorDataEqualsLog(cursor, updatedLog);
//        cursor.close();
//    }
//
//    @Test @Ignore
//    public void cannotUpdateLogFromDatabase_UsingAllLogsContentUri() {
//        assertEquals(EXECUTION_FAILURE, RuntimeEnvironment.application.getContentResolver()
//                .update(CONTENT_URI_LOGS, null, null, null));
//    }
//
//    @Test @Ignore
//    public void canQueryDatabaseForAllColumnsUsingContentProvider() {
//        insertLogToDatabase(TEST_SESSION_LOG, RuntimeEnvironment.application);
//        insertDrillToDatabase(TEST_SESSION_LOG.getDrillCategory(), RuntimeEnvironment.application);
//
//        Cursor cursor = RuntimeEnvironment.application.getContentResolver()
//                .query(CONTENT_URI_ALL, ALL_TABLE_COLUMNS, null, null, null);
//
//        assertNotNull(cursor);
//        assertCursorDataEqualsLogWithAllTableColumns(cursor, TEST_SESSION_LOG);
//        cursor.close();
//    }
//
//    @Test @Ignore
//    public void canQueryDatabaseOnSingleDrill_ForAllColumnsUsingContentProvider() {
//        insertLogToDatabase(TEST_SESSION_LOG, RuntimeEnvironment.application);
//        insertDrillToDatabase(TEST_SESSION_LOG.getDrillCategory(), RuntimeEnvironment.application);
//
//        int drillId = TEST_SESSION_LOG.getDrillCategory().getId();
//        String[] selectionArgs = {String.valueOf(drillId)};
//        Uri uri = Uri.parse(CONTENT_URI_ALL + "/" + drillId);
//        Cursor cursor = RuntimeEnvironment.application.getContentResolver()
//                .query(uri, ALL_TABLE_COLUMNS, null, selectionArgs, null);
//
//        assertNotNull(cursor);
//        assertCursorDataEqualsLogWithAllTableColumns(cursor, TEST_SESSION_LOG);
//        cursor.close();
//    }
//
//    @Test @Ignore
//    public void canDeleteAllDataFromAllColumns() {
//        insertDrillToDatabase(TEST_DRILL, RuntimeEnvironment.application);
//        insertDrillToDatabase(TEST_DRILL, RuntimeEnvironment.application);
//        insertDrillToDatabase(TEST_DRILL, RuntimeEnvironment.application);
//        insertLogToDatabase(TEST_SESSION_LOG, RuntimeEnvironment.application);
//        insertLogToDatabase(TEST_SESSION_LOG, RuntimeEnvironment.application);
//        insertLogToDatabase(TEST_SESSION_LOG, RuntimeEnvironment.application);
//
//        Cursor drillCursor = RuntimeEnvironment.application.getContentResolver()
//                .query(CONTENT_URI_DRILLS, DrillsTable.ALL_DRILL_COLUMNS,
//                        null, null, null);
//        Cursor logCursor = RuntimeEnvironment.application.getContentResolver()
//                .query(CONTENT_URI_LOGS, LogsTable.ALL_LOG_COLUMNS,
//                        null, null, null);
//
//        assertNotNull(drillCursor);
//        assertNotNull(logCursor);
//        assertEquals(3, drillCursor.getCount());
//        assertEquals(3, logCursor.getCount());
//        drillCursor.close();
//        logCursor.close();
//
//        RuntimeEnvironment.application.getContentResolver()
//                .delete(CONTENT_URI_DELETE_ALL_DATA, null, null);
//        drillCursor = RuntimeEnvironment.application.getContentResolver()
//                .query(CONTENT_URI_DRILLS, DrillsTable.ALL_DRILL_COLUMNS,
//                        null, null, null);
//        logCursor = RuntimeEnvironment.application.getContentResolver()
//                .query(CONTENT_URI_LOGS, LogsTable.ALL_LOG_COLUMNS,
//                        null, null, null);
//
//        assertNotNull(drillCursor);
//        assertNotNull(logCursor);
//        assertEquals(0, drillCursor.getCount());
//        assertEquals(0, logCursor.getCount());
//        drillCursor.close();
//        logCursor.close();
//    }
//
//    private DatabaseContentProvider getDatabaseContentProvider() {
//        ContentResolver contentResolver = RuntimeEnvironment.application.getContentResolver();
//        ContentProviderClient contentProviderClient = contentResolver
//                .acquireContentProviderClient(DatabaseContentProvider.CONTENT_URI_DRILLS);
//        assertNotNull(contentProviderClient);
//        return (DatabaseContentProvider)
//                contentProviderClient.getLocalContentProvider();
//    }
//
//    public static void assertCursorDataEqualsDrill(Cursor cursor, Drill drill) {
//        assertEquals(1, cursor.getCount());
//        assertTrue(cursor.moveToFirst());
//        assertEquals(DrillsTable.ALL_DRILL_COLUMNS.length, cursor.getColumnCount());
//        assertEquals(drill.getId(),
//                cursor.getInt(cursor.getColumnIndex(DrillsTable.DRILL_ID)));
//        assertEquals(drill.getDrillCategory(),
//                cursor.getString(cursor.getColumnIndex(DrillsTable.COLUMN_TITLE)));
//        assertEquals(drill.getImageResId(),
//                cursor.getInt(cursor.getColumnIndex(DrillsTable.COLUMN_IMAGE_ID)));
//        assertThat(drill.getDrillCategory(), is(getStringAsArray(cursor.getString(
//                cursor.getColumnIndex(DrillsTable.COLUMN_CATEGORY)))));
//    }
//
//    public static void assertCursorDataEqualsLog(Cursor cursor, SessionLog sessionLog) {
//        assertEquals(1, cursor.getCount());
//        assertTrue(cursor.moveToFirst());
//        assertEquals(LogsTable.ALL_LOG_COLUMNS.length, cursor.getColumnCount());
//        assertEquals(sessionLog.getSessionId(),
//                cursor.getInt(cursor.getColumnIndex(LogsTable.LOG_ID)));
//        assertEquals(sessionLog.getSessionDate().getTime(),
//                cursor.getLong(cursor.getColumnIndex(LogsTable.COLUMN_DATE)));
//        assertEquals(sessionLog.getSessionLength().getTime(),
//                cursor.getLong(cursor.getColumnIndex(LogsTable.COLUMN_LENGTH)));
//        assertEquals(sessionLog.getSessionGoal(),
//                cursor.getString(cursor.getColumnIndex(LogsTable.COLUMN_GOAL)));
//        assertEquals(sessionLog.getActiveWork().getTime(),
//                cursor.getLong(cursor.getColumnIndex(LogsTable.COLUMN_ACTIVE_WORK)));
//        assertEquals(sessionLog.getRestTime().getTime(),
//                cursor.getLong(cursor.getColumnIndex(LogsTable.COLUMN_REST_TIME)));
//        assertEquals(sessionLog.getSetsCompleted(),
//                cursor.getInt(cursor.getColumnIndex(LogsTable.COLUMN_SETS_COMPLETED)));
//        assertEquals(sessionLog.getRepsCompleted(),
//                cursor.getInt(cursor.getColumnIndex(LogsTable.COLUMN_REPS_COMPLETED)));
//        assertEquals(sessionLog.getGoal(),
//                cursor.getDouble(cursor.getColumnIndex(LogsTable.COLUMN_SUCCESS)));
//        assertEquals(sessionLog.getDrillCategory().getId(),
//                cursor.getInt(cursor.getColumnIndex(LogsTable.COLUMN_DRILL)));
//    }
//
//    public static void assertCursorDataEqualsLogWithAllTableColumns(Cursor cursor, SessionLog sessionLog) {
//        assertEquals(1, cursor.getCount());
//        assertTrue(cursor.moveToFirst());
//        assertThat(ALL_TABLE_COLUMNS, is(cursor.getColumnNames()));
//        assertEquals(sessionLog.getDrillCategory().getId(),
//                cursor.getInt(cursor.getColumnIndex(DrillsTable.DRILL_ID)));
//        assertEquals(sessionLog.getDrillCategory().getDrillCategory(),
//                cursor.getString(cursor.getColumnIndex(DrillsTable.COLUMN_TITLE)));
//        assertEquals(sessionLog.getDrillCategory().getImageResId(),
//                cursor.getInt(cursor.getColumnIndex(DrillsTable.COLUMN_IMAGE_ID)));
//        assertThat(sessionLog.getDrillCategory().getDrillCategory(), is(getStringAsArray(cursor.getString(
//                cursor.getColumnIndex(DrillsTable.COLUMN_CATEGORY)))));
//        assertEquals(sessionLog.getSessionId(),
//                cursor.getInt(cursor.getColumnIndex(LogsTable.LOG_ID)));
//        assertEquals(sessionLog.getSessionDate().getTime(),
//                cursor.getLong(cursor.getColumnIndex(LogsTable.COLUMN_DATE)));
//        assertEquals(sessionLog.getSessionLength().getTime(),
//                cursor.getLong(cursor.getColumnIndex(LogsTable.COLUMN_LENGTH)));
//        assertEquals(sessionLog.getSessionGoal(),
//                cursor.getString(cursor.getColumnIndex(LogsTable.COLUMN_GOAL)));
//        assertEquals(sessionLog.getActiveWork().getTime(),
//                cursor.getLong(cursor.getColumnIndex(LogsTable.COLUMN_ACTIVE_WORK)));
//        assertEquals(sessionLog.getRestTime().getTime(),
//                cursor.getLong(cursor.getColumnIndex(LogsTable.COLUMN_REST_TIME)));
//        assertEquals(sessionLog.getSetsCompleted(),
//                cursor.getInt(cursor.getColumnIndex(LogsTable.COLUMN_SETS_COMPLETED)));
//        assertEquals(sessionLog.getRepsCompleted(),
//                cursor.getInt(cursor.getColumnIndex(LogsTable.COLUMN_REPS_COMPLETED)));
//        assertEquals(sessionLog.getGoal(),
//                cursor.getDouble(cursor.getColumnIndex(LogsTable.COLUMN_SUCCESS)));
//        assertEquals(sessionLog.getDrillCategory().getId(),
//                cursor.getInt(cursor.getColumnIndex(LogsTable.COLUMN_DRILL)));
//    }

    @After
    public void tearDown() {

        RuntimeEnvironment.application.getContentResolver()
                .delete(CONTENT_URI_DELETE_ALL_DATA, null, null);
    }

}