package com.armandgray.taap.log;

import android.content.Intent;
import android.database.Cursor;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.db.DrillsTable;
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
import static com.armandgray.taap.db.DatabaseContentProviderTest.TEST_DRILL;
import static com.armandgray.taap.db.DatabaseContentProviderTest.TEST_SESSION_LOG;
import static com.armandgray.taap.db.DatabaseContentProviderTest.assertCursorDataEqualsDrill;
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

        String selectedDrill = DrillsTable.DRILL_ID + " = " + TEST_DRILL.getDrillId();
        Cursor cursor = RuntimeEnvironment.application.getContentResolver()
                .query(CONTENT_URI_DRILLS, DrillsTable.ALL_DRILL_COLUMNS, selectedDrill,
                        null, null);

        assertNotNull(cursor);
        assertCursorDataEqualsDrill(cursor, TEST_DRILL);
        cursor.close();
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
        controller = null;
    }

}