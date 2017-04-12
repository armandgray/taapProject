package com.armandgray.taap;

import org.junit.After;
import org.junit.Before;
import org.robolectric.Robolectric;

public class DrillActivityLifeCycleTest {

    private static DrillActivity activity;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        if (activity == null) {
            activity = Robolectric.buildActivity(DrillActivity.class).create().visible().get();
        }
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activity = null;
    }
}
