package com.armandgray.taap;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityControllerTest {

    private MainActivity activity;
    private MainActivityController controller;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
        controller = activity.controller;
    }

    @Test
    public void activityInstanceOfAppCompatActivity_TestConstructor() throws Exception {
        assertEquals("MainActivity", controller.activity.getLocalClassName());
    }

    @Test
    public void doesCreateViewsHandler_TestConstructor() throws Exception {
        assertNotNull(controller.views);
    }

    @Test
    public void doesSetupInitialActivityState_TestConstructor() throws Exception {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        Spinner spinner = (Spinner) activity.findViewById(R.id.spDrillsSort);
        SearchView searchView = (SearchView) activity.findViewById(R.id.searchView);

        assertNotNull(toolbar);
        assertNotNull(activity.getSupportActionBar());
        assertNotNull(fab);
        assertNotNull(spinner);
        assertTrue(spinner.getCount() > 0);
        assertEquals(View.GONE, searchView.getVisibility());
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activity = null;
        controller = null;
    }

}