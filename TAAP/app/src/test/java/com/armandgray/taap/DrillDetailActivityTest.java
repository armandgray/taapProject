package com.armandgray.taap.settings;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.DrillDetailActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DrillDetailActivityTest {

    private ActivityController<DrillDetailActivity> activityController;
    private DrillDetailActivity activity;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        if (activity == null) {
            activityController = Robolectric.buildActivity(DrillDetailActivity.class);
            activity = activityController.create().visible().get();
        }
    }

    @Test
    public void createsDrillDetailActivityController_TestOnCreate() throws Exception {
        assertNotNull(activity.controller);
        assertNotNull(activity.controller.activity);
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activityController = null;
        activity = null;
    }

}