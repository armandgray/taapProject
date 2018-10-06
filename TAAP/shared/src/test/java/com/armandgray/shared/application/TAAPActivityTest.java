package com.armandgray.shared.application;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class TAAPActivityTest {

    private ActivityController<TAAPActivity> activityController;
    private TAAPActivity activity;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
    }

    @Test
    public void stub_test_testing() {
        // TODO Complete Testing
        assertTrue(true);
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
    }
}
