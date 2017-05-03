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

import java.util.Calendar;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

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
    public void canGetTimeElapsed() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, 0, 0, 0);
        long dummyTime = System.currentTimeMillis();
        calendar.setTimeInMillis(dummyTime);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour > 12) { calendar.set(Calendar.HOUR_OF_DAY, 0); }
        assertNotNull(controller.getTimeElapsed(dummyTime));
        assertEquals(calendar.getTime(), controller.getTimeElapsed(dummyTime));
    }

    @Test
    public void doesZeroOutHoursForTimesLessThanOneHour_CanGetTimeElapsed() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, 0, 0, 0);
        calendar.setTimeInMillis(1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        assertNotNull(controller.getTimeElapsed(1));
        assertEquals(calendar.getTime().toString(), controller.getTimeElapsed(1).toString());
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
        controller = null;
    }

}