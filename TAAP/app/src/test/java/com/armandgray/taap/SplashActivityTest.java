package com.armandgray.taap;

import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SplashActivityTest {

    private ActivityController<SplashActivity> activityController;
    private SplashActivity activity;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(SplashActivity.class);
        activity = activityController.create().visible().get();
    }

    @Test
    public void doesStartMainActivity() throws Exception {
        Intent expectedIntent = new Intent(activity, MainActivity.class);
        assertEquals(expectedIntent.toString(),
                shadowOf(activity).getNextStartedActivity().toString());
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
    }

}