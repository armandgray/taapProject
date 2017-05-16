package com.armandgray.taap.detail;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;
import com.armandgray.taap.detail.dialogs.TimerDialog;
import com.armandgray.taap.models.SessionLog;
import com.armandgray.taap.utils.LogSetsRvAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDialog;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.armandgray.taap.MainActivity.SELECTED_DRILL;
import static com.armandgray.taap.db.DatabaseContentProvider.ALL_TABLE_COLUMNS;
import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_ALL;
import static com.armandgray.taap.db.DatabaseContentProvider.insertDrillToDatabase;
import static com.armandgray.taap.db.DatabaseContentProvider.insertLogToDatabase;
import static com.armandgray.taap.db.DatabaseContentProviderTest.TEST_SESSION_LOG;
import static com.armandgray.taap.db.DatabaseContentProviderTest.assertCursorDataEqualsLogWithAllTableColumns;
import static com.armandgray.taap.detail.dialogs.DetailSummaryDialog.DIALOG;
import static com.armandgray.taap.utils.CursorDataHelper.addAllLogsData;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DrillDetailControllerTest {

    public static final SessionLog DUMMY_SESSION_LOG = new SessionLog.Builder()
            .successRate(1.00)
            .drill(TEST_SESSION_LOG.getDrill())
            .create();
    private ActivityController<DrillDetailActivity> activityController;
    private DrillDetailActivity activity;
    private DrillDetailController controller;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        Intent intent = new Intent(RuntimeEnvironment.application, DrillDetailActivity.class);
        intent.putExtra(SELECTED_DRILL, TEST_SESSION_LOG.getDrill());
        activityController = Robolectric.buildActivity(DrillDetailActivity.class).withIntent(intent);
        activity = activityController.create().start().resume().visible().get();
        controller = activity.controller;
    }

    @Test
    public void activityInstanceOfAppCompatActivity_TestConstructor() throws Exception {
        assertEquals("detail.DrillDetailActivity", controller.activity.getLocalClassName());
    }

    @Test
    public void doesShowTimerDialogOnFabClick() throws Exception {
        controller.views.fab.performClick();

        Dialog resultDialog = ShadowDialog.getLatestDialog();
        int resultId = resultDialog.findViewById(R.id.timerDialogContainer).getId();
        resultDialog.dismiss();

        TimerDialog expectedDialog = new TimerDialog();
        expectedDialog.show(activity.getSupportFragmentManager(), DIALOG);
        int expectedId = expectedDialog.getDialog().findViewById(R.id.timerDialogContainer).getId();
        expectedDialog.dismiss();

        assertNotNull(resultDialog);
        assertEquals(expectedId, resultId);
    }

    @Test
    public void doesCreateViewsHandler_TestConstructor() throws Exception {
        assertNotNull(controller.views);
        assertNotNull(controller.views.activity);
    }

    @Test
    public void doesTrackActiveWorkTime() throws Exception {
        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();
        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();

        assertNotNull(controller.activeWorkTime);
        assertTrue(controller.activeWorkTime > 0);
    }

    @Test
    public void doesTrackRestTime() throws Exception {
        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();
        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();
        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();

        assertNotNull(controller.restTime);
        assertTrue(controller.restTime > 0);
    }

    @Test
    public void doesToastErrorIfSuccessesGreaterThanReps_OnTogglePausePlay_BeforeActive() throws Exception {
        controller.views.npSuccesses.setValue(100);
        Dialog resultDialog = ShadowDialog.getLatestDialog();

        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();
        controller.views.fab.performClick();

        assertNull(resultDialog);
        assertThat(ShadowToast.getTextOfLatestToast(),
                equalTo(activity.getString(R.string.invalid_successes)));

    }

    @Test
    public void doesAddElapsedTimeToActiveWorkIfDrillActive_OnBtnFinishedClick() throws Exception {
        controller.sessionLog = DUMMY_SESSION_LOG;
        controller.views.drill = DUMMY_SESSION_LOG.getDrill();

        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();
        controller.views.btnFinished.performClick();

        assertNotNull(controller.activeWorkTime);
        assertTrue(controller.activeWorkTime > 0);
    }

    @Test
    public void doesRecordCurrentSetData_OnBtnFinishedClick() throws Exception {
        controller.views.npSets.setValue(1);
        controller.views.npReps.setValue(1);
        controller.views.npSuccesses.setValue(1);

        controller.successRate = 0.5;
        controller.setsCompleted = 1;

        int expectedSets = controller.setsCompleted + 1;
        int expectedReps = controller.repsCompleted + 1;
        double expectedRate = 0.75;

        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();
        controller.views.btnFinished.performClick();

        assertEquals(expectedSets, controller.setsCompleted);
        assertEquals(expectedReps, controller.repsCompleted);
        assertEquals(expectedRate, controller.successRate);
    }

    @Test
    public void doesToastRestMessageIfDrillActive_OnTogglePausePlay() {
        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();

        assertThat(ShadowToast.getTextOfLatestToast(),
                equalTo(activity.getString(R.string.rest_time_started)));
    }

    @Test
    public void doesAssignSessionLogFields_OnBtnFinishedClick() throws Exception {
        controller.sessionLog = DUMMY_SESSION_LOG;
        controller.views.drill = DUMMY_SESSION_LOG.getDrill();

        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();
        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();
        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();
        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();
        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();
        controller.views.btnFinished.performClick();

        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, 0, 0, 0);
        assertNotNull(controller.sessionLog);
        assertTrue(controller.sessionLog
                .getSessionLength().getTime() > calendar.getTime().getTime());
    }

    @Test
    public void doesSetSessionLogSuccessRecord() throws Exception {
        controller.sessionLog = DUMMY_SESSION_LOG;
        controller.views.drill = DUMMY_SESSION_LOG.getDrill();

        controller.views.npSets.setValue(1);
        controller.views.npReps.setValue(1);
        controller.views.npSuccesses.setValue(1);

        insertDrillToDatabase(TEST_SESSION_LOG.getDrill(), RuntimeEnvironment.application);
        insertLogToDatabase(TEST_SESSION_LOG, RuntimeEnvironment.application);

        int drillId = TEST_SESSION_LOG.getDrill().getDrillId();
        String[] selectionArgs = {String.valueOf(drillId)};
        Uri uri = Uri.parse(CONTENT_URI_ALL + "/" + drillId);
        Cursor cursor = RuntimeEnvironment.application.getContentResolver()
                .query(uri, ALL_TABLE_COLUMNS, null, selectionArgs, null);


        List<SessionLog> listAllLogs = new ArrayList<>();
        addAllLogsData(cursor, listAllLogs);
        double max = controller.sessionLog.getSuccessRate();
        for (SessionLog log : listAllLogs) {
            if (log.getSuccessRate() > max) { max = log.getSuccessRate(); }
        }

        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();
        controller.views.btnFinished.performClick();

        assertCursorDataEqualsLogWithAllTableColumns(cursor, TEST_SESSION_LOG);
        assertNotNull(controller.sessionLog);
        assertNotNull(cursor);
        assertEquals(1, cursor.getCount());
        assertEquals(1, listAllLogs.size());
        assertEquals(TEST_SESSION_LOG.getSuccessRate(), listAllLogs.get(0).getSuccessRate());
        assertEquals(1.00, controller.successRate);
        assertEquals(max, controller.sessionLog.getSuccessRecord());
        cursor.close();
    }


    @SuppressLint("InflateParams")
    @Test
    public void doesTogglePlayButtonOnTimerDialogDismiss() {
        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();

        Dialog resultDialog = ShadowDialog.getLatestDialog();
        View layout = LayoutInflater.from(activity)
                .inflate(R.layout.timer_dialog_layout, null, false);
        resultDialog.dismiss();

        assertNotNull(resultDialog);
        assertFalse(resultDialog.isShowing());
        assertEquals(layout.findViewById(R.id.timerDialogContainer).getId(),
                resultDialog.findViewById(R.id.timerDialogContainer).getId());
        assertFalse(controller.drillActive);
    }

    @Test
    public void doesAddLogAndUpdateRv_OnTogglePlay() throws Exception {
        controller.views.adapterPrevLogs = new LogSetsRvAdapter(new ArrayList<SessionLog>());
        ArrayList<SessionLog> expectedList = new ArrayList<>();
        for (int i = 0; i < controller.views.adapterPrevLogs.getItemCount(); i++) {
            expectedList.add(controller.views.adapterPrevLogs.getItemAtPosition(i));
        }
        expectedList.add(TEST_SESSION_LOG);

        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();
        controller.views.npSets.setValue(TEST_SESSION_LOG.getSetsCompleted());
        controller.views.npReps.setValue(TEST_SESSION_LOG.getRepsCompleted());
        controller.views.npSuccesses.setValue(1);
        controller.views.fab.performClick();

        double expectedRate = 1.0 / controller.views.npReps.getValue();
        assertEquals(expectedList.size(), controller.views.adapterPrevLogs.getItemCount());
        assertEquals(1, controller.views.adapterPrevLogs.getItemAtPosition(0).getSetsCompleted());
        assertEquals(expectedList.get(0).getRepsCompleted(),
                controller.views.adapterPrevLogs.getItemAtPosition(0).getRepsCompleted());
        assertEquals(Math.floor(expectedRate * 100) / 100,
                controller.views.adapterPrevLogs.getItemAtPosition(0).getSuccessRate());
    }

    @Test
    public void doesDecrementSetsLeft_OnTogglePlay_BeforeInactive() throws Exception {
        controller.views.npSets.setValue(5);

        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();
        controller.views.fab.performClick();

        assertEquals(4, controller.views.npSets.getValue());
    }

    @Test
    public void doesNotDecrementSetsLeftIfSetsIsOne_OnTogglePlay_BeforeInactive() throws Exception {
        controller.views.npSets.setValue(1);

        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();
        controller.views.fab.performClick();

        assertEquals(1, controller.views.npSets.getValue());
    }

    @Test
    public void doesIncrementSetsCompletedField_OnTogglePlay_BeforeActive() throws Exception {
        controller.views.npSets.setValue(5);

        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();
        controller.views.fab.performClick();

        assertEquals(1, controller.setsCompleted);
    }

    @Test
    public void doesIncrementRepsCompletedField_OnTogglePlay_BeforeActive() throws Exception {
        controller.views.npSets.setValue(1);
        controller.views.npReps.setValue(10);

        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();
        controller.views.fab.performClick();

        assertEquals(10, controller.repsCompleted);
    }

    @Test
    public void doesAverageSuccessRateField_OnTogglePlay_BeforeActive() throws Exception {
        controller.views.npSets.setValue(1);
        controller.views.npReps.setValue(10);
        controller.views.npSuccesses.setValue(5);

        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();
        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();

        controller.views.npReps.setValue(10);
        controller.views.npSuccesses.setValue(10);

        controller.views.fab.performClick();

        assertEquals(.75, controller.successRate);
    }

    @Test
    public void doesCarryPlaceholderToRerecordSet_OnSummaryDialogCancel_WithValueChange() throws Exception {
        controller.views.npSets.setValue(1);
        controller.views.npReps.setValue(1);
        controller.views.npSuccesses.setValue(1);

        controller.successRate = 0.5;
        controller.setsCompleted = 1;

        int expectedSets = controller.setsCompleted + 1;
        int expectedReps = controller.repsCompleted + 1;
        double expectedRate = 0.75;

        controller.views.fab.performClick();
        ShadowDialog.getLatestDialog().dismiss();
        controller.views.btnFinished.performClick();

        assertEquals(expectedSets, controller.setsCompleted);
        assertEquals(expectedReps, controller.repsCompleted);
        assertEquals(expectedRate, controller.successRate);

        ShadowDialog.getLatestDialog().dismiss();
        controller.views.btnFinished.performClick();
        
        controller.views.npSets.setValue(1);
        controller.views.npReps.setValue(2);
        controller.views.npSuccesses.setValue(1);

        expectedReps = controller.repsCompleted + 2;
        expectedRate = 0.5;

        assertEquals(expectedSets, controller.setsCompleted);
        assertEquals(expectedReps, controller.repsCompleted);
        assertEquals(expectedRate, controller.successRate);
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
        controller = null;
    }

}