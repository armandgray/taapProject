package com.armandgray.taap.detail;

import com.armandgray.taap.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

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
    public void doesCreateViewsHandler_TestConstructor() throws Exception {
        assertNotNull(controller.views);
        assertNotNull(controller.views.activity);
    }

    @Test
    public void canGetTimeElapsed() throws Exception {
        assertEquals(0, controller.getTimeElapsed(System.nanoTime(), System.nanoTime()));
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
        controller = null;
    }

}