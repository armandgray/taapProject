package com.armandgray.taap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    private MainActivity activity;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        if (activity == null) {
            activity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
        }
    }

    @Test
    public void createsDrillActivityController_TestOnCreate() throws Exception {
        assertNotNull(activity.controller);
        assertNotNull(activity.controller.activity);
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activity = null;
    }

}