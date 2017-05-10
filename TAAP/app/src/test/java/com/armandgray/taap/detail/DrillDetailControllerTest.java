package com.armandgray.taap.detail;

import android.app.Dialog;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDialog;
import org.robolectric.shadows.ShadowToast;

import java.util.Calendar;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DrillDetailControllerTest {

    private ActivityController<DrillDetailActivity> activityController;
    private DrillDetailActivity activity;
    private DrillDetailController controller;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(DrillDetailActivity.class);
        activity = activityController.create().visible().get();
        controller = activity.controller;
    }

    @Test
    public void activityInstanceOfAppCompatActivity_TestConstructor() throws Exception {
        assertEquals("detail.DrillDetailActivity", controller.activity.getLocalClassName());
    }

    @Test
    public void doesShowTimerDialogOnFabClick() throws Exception {
        controller.views.fab.performClick();
        activityController.start().resume();

        Dialog resultDialog = ShadowDialog.getLatestDialog();
        assertNotNull(resultDialog);
        assertEquals(new TimerDialog().getDialog(), resultDialog);
    }

    @Test
    public void doesCreateViewsHandler_TestConstructor() throws Exception {
        assertNotNull(controller.views);
        assertNotNull(controller.views.activity);
    }

    @Test
    public void doesTrackActiveWorkTime() throws Exception {
        controller.views.fab.performClick();
        controller.views.fab.performClick();

        assertNotNull(controller.activeWorkTime);
        assertTrue(controller.activeWorkTime > 0);
    }

    @Test
    public void doesTrackRestTime() throws Exception {
        controller.views.fab.performClick();
        controller.views.fab.performClick();
        controller.views.fab.performClick();

        assertNotNull(controller.restTime);
        assertTrue(controller.restTime > 0);
    }

    @Test
    public void doesToastErrorIfSuccessesGreaterThanReps_OnBtnFinishedClick() throws Exception {
        activityController.start().resume();
        controller.views.npSuccesses.setValue(100);
        Dialog resultDialog = ShadowDialog.getLatestDialog();

        controller.views.fab.performClick();
        controller.views.btnFinished.performClick();

        assertNull(resultDialog);
        assertThat(ShadowToast.getTextOfLatestToast(),
                equalTo(activity.getString(R.string.invalid_successes)));

    }

    @Test
    public void doesAddElapsedTimeToActiveWorkIfDrillActive_OnBtnFinishedClick() throws Exception {
        controller.views.fab.performClick();
        controller.views.btnFinished.performClick();

        assertNotNull(controller.activeWorkTime);
        assertTrue(controller.activeWorkTime > 0);
    }

    @Test
    public void doesAssignSessionLogFields_OnBtnFinishedClick() throws Exception {
        controller.views.fab.performClick();
        controller.views.fab.performClick();
        controller.views.fab.performClick();
        controller.views.fab.performClick();
        controller.views.fab.performClick();
        controller.views.btnFinished.performClick();

        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, 0, 0, 0);
        assertNotNull(controller.sessionLog);
        assertTrue(controller.sessionLog
                .getSessionLength().getTime() > calendar.getTime().getTime());
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
        controller = null;
    }

}