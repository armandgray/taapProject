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

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityControllerTest {

    private static final String W_ALL = "wAll";
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
    public void canGetAllSpinnerItems_MethodTest_OnSpinnerItemSelected() throws Exception {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, 0,
                activity.getResources().getStringArray(R.array.drill_types));
        String[] drillTypes = activity.getResources().getStringArray(R.array.drill_types);
        assertTrue(Arrays.equals(drillTypes, controller.getAllSpinnerItems(adapter)));
    }

    @Test
    public void doesAddSearchQueryToSpinner_MethodTest_OnEtSearchTextChanged() throws Exception {
        controller.views.etSearch.setText(W_ALL);
        controller.views.listener.onEtSearchTextChanged(null, 0, 0, 0);

        String[] allSpinnerItems = controller
                .getAllSpinnerItems(controller.views.spinner.getAdapter());
        assertNotNull(allSpinnerItems);
        assertTrue(Arrays.asList(allSpinnerItems).contains("Search: " + W_ALL));
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
        controller = null;
    }

}