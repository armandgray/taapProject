package com.armandgray.taap.log;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_DRILLS;
import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_LOGS;
import static com.armandgray.taap.db.DatabaseContentProviderTest.TEST_SESSION_LOG;
import static com.armandgray.taap.db.DatabaseContentProviderTest.assertCursorDataEqualsDrill;
import static com.armandgray.taap.db.DatabaseContentProviderTest.assertCursorDataEqualsLog;
import static com.armandgray.taap.log.LogActivity.SESSION_LOG;
import static com.armandgray.taap.models.Drill.BALL_HANDLING;
import static com.armandgray.taap.models.Drill.CONDITIONING;
import static com.armandgray.taap.models.Drill.DEFENSE;
import static com.armandgray.taap.models.Drill.FUNDAMENTALS;
import static com.armandgray.taap.models.Drill.OFFENSE;
import static com.armandgray.taap.models.Drill.PASSING;
import static com.armandgray.taap.models.Drill.SHOOTING;
import static com.armandgray.taap.utils.DateTimeHelper.getDateFormattedAsString;
import static com.armandgray.taap.utils.DateTimeHelper.getTotalTimeAsDate;
import static com.armandgray.taap.utils.MathHelper.getAveragePercentage;
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
        // TODO FIX ISSUE WITH SETUP & TEARDOWN NOT RELOADING CURSOR
        assertNotNull(controller.listAllLogs);
        assertEquals(1, controller.listAllLogs.size());
        assertEquals(TEST_SESSION_LOG.getSessionId(), controller.listAllLogs.get(0).getSessionId());
    }

    @Test
    public void doesRetrieveFieldValuesFromLogs_Fundamentals() throws Exception {
        ArrayList<SessionLog> expectedList = new ArrayList<>();
        for (SessionLog log : controller.listAllLogs) {
            if (Arrays.asList(log.getDrill().getCategory()).contains(FUNDAMENTALS)) {
                expectedList.add(log);
            }
        }

        assertNotNull(controller.listFundamentalLogs);
        assertEquals(expectedList, controller.listFundamentalLogs);
    }

    @Test
    public void doesRetrieveFieldValuesFromLogs_Defense() throws Exception {
        ArrayList<SessionLog> expectedList = new ArrayList<>();
        for (SessionLog log : controller.listAllLogs) {
            if (Arrays.asList(log.getDrill().getCategory()).contains(DEFENSE)) {
                expectedList.add(log);
            }
        }

        assertNotNull(controller.listDefenseLogs);
        assertEquals(expectedList, controller.listDefenseLogs);
    }

    @Test
    public void doesRetrieveFieldValuesFromLogs_Offense() throws Exception {
        ArrayList<SessionLog> expectedList = new ArrayList<>();
        for (SessionLog log : controller.listAllLogs) {
            List<String> category = Arrays.asList(log.getDrill().getCategory());
            if (category.contains(OFFENSE) || category.contains(PASSING)) {
                expectedList.add(log);
            }
        }

        assertNotNull(controller.listOffenseLogs);
        assertEquals(expectedList, controller.listOffenseLogs);
    }

    @Test
    public void doesRetrieveFieldValuesFromLogs_Conditioning() throws Exception {
        ArrayList<SessionLog> expectedList = new ArrayList<>();
        for (SessionLog log : controller.listAllLogs) {
            if (Arrays.asList(log.getDrill().getCategory()).contains(CONDITIONING)) {
                expectedList.add(log);
            }
        }

        assertNotNull(controller.listConditioningLogs);
        assertEquals(expectedList, controller.listConditioningLogs);
    }

    @Test
    public void doesRetrieveFieldValuesFromLogs_Shooting() throws Exception {
        ArrayList<SessionLog> expectedList = new ArrayList<>();
        for (SessionLog log : controller.listAllLogs) {
            if (Arrays.asList(log.getDrill().getCategory()).contains(SHOOTING)) {
                expectedList.add(log);
            }
        }

        assertNotNull(controller.listShootingLogs);
        assertEquals(expectedList, controller.listShootingLogs);
    }

    @Test
    public void doesRetrieveFieldValuesFromLogs_BallHandling() throws Exception {
        ArrayList<SessionLog> expectedList = new ArrayList<>();
        for (SessionLog log : controller.listAllLogs) {
            if (Arrays.asList(log.getDrill().getCategory()).contains(BALL_HANDLING)) {
                expectedList.add(log);
            }
        }

        assertNotNull(controller.listBallHandlingLogs);
        assertEquals(expectedList, controller.listBallHandlingLogs);
    }

    @Test
    public void doesSetViewValuesFromLogs_Fundamentals() throws Exception {
        Double expectedSuccessRate = getAveragePercentage(controller.listFundamentalLogs);
        expectedSuccessRate *= 100;
        Date expectedTime = getTotalTimeAsDate(controller.listFundamentalLogs);

        LinearLayout layout = controller.views.layoutFundamentals;
        TextView tvTime = (TextView) layout.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) layout.findViewById(R.id.tvSuccessRate);

        assertEquals(getDateFormattedAsString(expectedTime), tvTime.getText());
        assertEquals(expectedSuccessRate.intValue() + "%", tvSuccessRate.getText());
    }

    @Test
    public void doesSetViewValuesFromLogs_Defense() throws Exception {
        Double expectedSuccessRate = getAveragePercentage(controller.listDefenseLogs);
        expectedSuccessRate *= 100;
        Date expectedTime = getTotalTimeAsDate(controller.listDefenseLogs);

        LinearLayout layout = controller.views.layoutDefense;
        TextView tvTime = (TextView) layout.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) layout.findViewById(R.id.tvSuccessRate);

        assertEquals(getDateFormattedAsString(expectedTime), tvTime.getText());
        assertEquals(expectedSuccessRate.intValue() + "%", tvSuccessRate.getText());
    }

    @Test
    public void doesSetViewValuesFromLogs_Offense() throws Exception {
        Double expectedSuccessRate = getAveragePercentage(controller.listOffenseLogs);
        expectedSuccessRate *= 100;
        Date expectedTime = getTotalTimeAsDate(controller.listOffenseLogs);

        LinearLayout layout = controller.views.layoutOffense;
        TextView tvTime = (TextView) layout.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) layout.findViewById(R.id.tvSuccessRate);

        assertEquals(getDateFormattedAsString(expectedTime), tvTime.getText());
        assertEquals(expectedSuccessRate.intValue() + "%", tvSuccessRate.getText());
    }

    @Test
    public void doesSetViewValuesFromLogs_Conditioning() throws Exception {
        Double expectedSuccessRate = getAveragePercentage(controller.listConditioningLogs);
        expectedSuccessRate *= 100;
        Date expectedTime = getTotalTimeAsDate(controller.listConditioningLogs);

        LinearLayout layout = controller.views.layoutConditioning;
        TextView tvTime = (TextView) layout.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) layout.findViewById(R.id.tvSuccessRate);

        assertEquals(getDateFormattedAsString(expectedTime), tvTime.getText());
        assertEquals(expectedSuccessRate.intValue() + "%", tvSuccessRate.getText());
    }

    @Test
    public void doesSetViewValuesFromLogs_Shooting() throws Exception {
        Double expectedSuccessRate = getAveragePercentage(controller.listShootingLogs);
        expectedSuccessRate *= 100;
        Date expectedTime = getTotalTimeAsDate(controller.listShootingLogs);

        LinearLayout layout = controller.views.layoutShooting;
        TextView tvTime = (TextView) layout.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) layout.findViewById(R.id.tvSuccessRate);

        assertEquals(getDateFormattedAsString(expectedTime), tvTime.getText());
        assertEquals(expectedSuccessRate.intValue() + "%", tvSuccessRate.getText());
    }

    @Test
    public void doesSetViewValuesFromLogs_BallHandling() throws Exception {
        Double expectedSuccessRate = getAveragePercentage(controller.listBallHandlingLogs);
        expectedSuccessRate *= 100;
        Date expectedTime = getTotalTimeAsDate(controller.listBallHandlingLogs);

        LinearLayout layout = controller.views.layoutBallHandling;
        TextView tvTime = (TextView) layout.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) layout.findViewById(R.id.tvSuccessRate);

        assertEquals(getDateFormattedAsString(expectedTime), tvTime.getText());
        assertEquals(expectedSuccessRate.intValue() + "%", tvSuccessRate.getText());
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
        controller = null;
    }

}