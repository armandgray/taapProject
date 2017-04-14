package com.armandgray.taap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityViewsTest {

    private MainActivity activity;
    private MainActivityViews views;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
        views = activity.controller.views;
    }

    @Test
    public void activityInstanceOfMainActivity_TestConstructor() throws Exception {
        assertEquals("MainActivity", views.activity.getLocalClassName());
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activity = null;
        views = null;
    }

}