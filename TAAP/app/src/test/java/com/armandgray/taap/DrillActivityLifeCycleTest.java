package com.armandgray.taap;

import android.support.v7.widget.Toolbar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DrillActivityLifeCycleTest {

    private static DrillActivity activity;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        if (activity == null) {
            activity = Robolectric.buildActivity(DrillActivity.class).create().visible().get();
        }
    }

    @Test
    public void canGetContentView_TestOnCreate() throws Exception {
        assertEquals(R.id.activityDrillLayout, shadowOf(activity).getContentView().getId());
    }

    @Test
    public void canGetToolbar_TestOnCreate() throws Exception {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        assertNotNull(toolbar);
        assertNotNull(activity.getSupportActionBar());
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activity = null;
    }

}