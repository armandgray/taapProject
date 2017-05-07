package com.armandgray.taap.log;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.db.DrillsTable;
import com.armandgray.taap.db.LogsTable;
import com.armandgray.taap.models.SessionLog;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_DRILLS;
import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_LOGS;
import static com.armandgray.taap.db.DatabaseContentProviderTest.TEST_SESSION_LOG;
import static com.armandgray.taap.db.DatabaseContentProviderTest.assertCursorDataEqualsDrill;
import static com.armandgray.taap.db.DatabaseContentProviderTest.assertCursorDataEqualsLog;
import static com.armandgray.taap.log.LogActivity.SESSION_LOG;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LogActivityControllerTest {

    private static final String W_ALL = "wAll";

    private ActivityController<LogActivity> activityController;
    private LogActivity activity;
    private LogActivityController controller;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        Intent intent = new Intent(RuntimeEnvironment.application, LogActivity.class);
        intent.putExtra(SESSION_LOG, TEST_SESSION_LOG);
        activityController = Robolectric.buildActivity(LogActivity.class).withIntent(intent);
        activity = activityController.create().visible().get();
        controller = activity.controller;
    }

    @Test
    public void activityInstanceOfAppCompatActivity_TestConstructor() throws Exception {
        assertEquals("log.LogActivity", controller.activity.getLocalClassName());
    }

    @Test
    public void doesCreateViewsHandler_TestConstructor() throws Exception {
        assertNotNull(controller.views);
    }

    @Test
    public void doesGetSessionLogFromIntent() throws Exception {
        SessionLog expectedLog = activity.getIntent().getParcelableExtra(SESSION_LOG);

        assertNotNull(controller.sessionLog);
        assertEquals(expectedLog, controller.sessionLog);
    }

    @Test
    public void doesInsertNonNullSessionLogIntoDatabase() throws Exception {
        assertNotNull(controller.sessionLog);

        String selectedLog = LogsTable.LOG_ID + " = " + TEST_SESSION_LOG.getSessionId();
        Cursor logCursor = RuntimeEnvironment.application.getContentResolver()
                .query(CONTENT_URI_LOGS, LogsTable.ALL_LOG_COLUMNS, selectedLog,
                        null, null);

        assertNotNull(logCursor);
        assertCursorDataEqualsLog(logCursor, TEST_SESSION_LOG);
        logCursor.close();
    }

    @Test
    public void doesImplicitlyInsertDrillIntoDatabase() throws Exception {
        assertNotNull(controller.sessionLog);

        String selectedDrill =
                DrillsTable.DRILL_ID + " = " + TEST_SESSION_LOG.getDrill().getDrillId();
        Uri uri = Uri.parse(CONTENT_URI_DRILLS + "/" + TEST_SESSION_LOG.getDrill().getDrillId());
        Cursor drillCursor = RuntimeEnvironment.application.getContentResolver()
                .query(uri, DrillsTable.ALL_DRILL_COLUMNS, selectedDrill, null, null);

        assertNotNull(drillCursor);
        assertCursorDataEqualsDrill(drillCursor, TEST_SESSION_LOG.getDrill());
        drillCursor.close();
    }

    @Test
    public void doesAssignListAllLogsFromDatabase() throws Exception {
        assertNotNull(controller.listAllLogs);
        assertEquals(1, controller.listAllLogs.size());
        assertEquals(TEST_SESSION_LOG, controller.listAllLogs.get(0));
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
        controller = null;
    }

}