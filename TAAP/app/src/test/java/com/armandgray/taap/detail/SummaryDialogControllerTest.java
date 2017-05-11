package com.armandgray.taap.detail;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.models.SessionLog;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static com.armandgray.taap.detail.DetailSummaryDialog.DIALOG;
import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SummaryDialogControllerTest {

    private ActivityController<DrillDetailActivity> activityController;
    private DrillDetailActivity activity;
    private DetailSummaryDialog dialog;
    private SummaryDialogController controller;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(DrillDetailActivity.class);
        activity = activityController.create().start().resume().visible().get();
        dialog = DetailSummaryDialog.newInstance(new SessionLog.Builder().create());
        dialog.show(activity.getSupportFragmentManager(), DIALOG);
        controller = dialog.controller;
    }

    @Test
    public void dialogInstanceOfDialogFragment_TestConstructor() throws Exception {
        assertEquals("settings.SettingsActivity", controller.dialog.getClass());
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
        dialog = null;
        controller = null;
    }

}