package com.armandgray.taap.activity.main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class MainActivityTest {

    private ActivityController<MainActivity> activityController;
    private MainActivity activity;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(MainActivity.class);
        activity = activityController.create().visible().get();
    }

    @Test
    public void stub_test_testing() {
        // test
        assertTrue(true);
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activity.finish();
        activityController.pause().stop().destroy();
        activityController = null;
        activity = null;
    }
}
