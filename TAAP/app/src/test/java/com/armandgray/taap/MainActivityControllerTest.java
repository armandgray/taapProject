package com.armandgray.taap;

import android.widget.ArrayAdapter;

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
public class MainActivityControllerTest {

    private ActivityController<MainActivity> activityController;
    private MainActivity activity;
    private MainActivityController controller;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(MainActivity.class);
        activity = activityController.create().visible().get();
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
    public void canGetAllSpinnerItems_MethodTest() throws Exception {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, 0);
        adapter = adapter.getClass().cast(controller.views.spinner.getAdapter());
        adapter.clear();
        String[] drillTypes = activity.getResources().getStringArray(R.array.drill_types);
        for (String type : drillTypes) { adapter.add(type); }
        adapter.notifyDataSetChanged();
        assertEquals(drillTypes, controller.getAllSpinnerItems(adapter));
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
        controller = null;
    }

}