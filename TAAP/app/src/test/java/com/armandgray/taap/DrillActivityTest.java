package com.armandgray.taap;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Spinner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DrillActivityTest {

    private static DrillActivity activity;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        if (activity == null) {
            activity = Robolectric.buildActivity(DrillActivity.class).create().visible().get();
        }
    }

    @Test
    public void hasField_DrillsArray() throws Exception {
        Field field = DrillActivity.class.getDeclaredField("drillsArray");
        field.setAccessible(true);
        assertNotNull(field);
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

    @Test
    public void canGetOptionsMenu_TestOnCreate() throws Exception {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        shadowOf(activity).onCreateOptionsMenu(toolbar.getMenu());
        assertNotNull(shadowOf(activity).getOptionsMenu());
    }

    @Test
    public void canSelectOptionsMenuItem_Search() throws Exception {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        shadowOf(activity).onCreateOptionsMenu(toolbar.getMenu());
        Menu optionsMenu = shadowOf(activity).getOptionsMenu();
        assertTrue(activity.onOptionsItemSelected(optionsMenu.findItem(R.id.action_search)));
    }

    @Test
    public void canShowSearchViewOnMenuItemClick() throws Exception {
        SearchView searchView = (SearchView) activity.findViewById(R.id.searchView);
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        shadowOf(activity).onCreateOptionsMenu(toolbar.getMenu());
        Menu optionsMenu = shadowOf(activity).getOptionsMenu();
        assertTrue(activity.onOptionsItemSelected(optionsMenu.findItem(R.id.action_search)));
        assertEquals(View.VISIBLE, searchView.getVisibility());
    }

    @Test
    public void canHideSpinnerAndFabOnMenuItemClick() throws Exception {
        Spinner spinner = (Spinner) activity.findViewById(R.id.spDrillsSort);
        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        shadowOf(activity).onCreateOptionsMenu(toolbar.getMenu());
        Menu optionsMenu = shadowOf(activity).getOptionsMenu();
        assertTrue(activity.onOptionsItemSelected(optionsMenu.findItem(R.id.action_search)));
        assertEquals(View.GONE, spinner.getVisibility());
        assertEquals(View.GONE, fab.getVisibility());
    }

    @Test
    public void canSelectOptionsMenuItem_Log() throws Exception {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        shadowOf(activity).onCreateOptionsMenu(toolbar.getMenu());
        Menu optionsMenu = shadowOf(activity).getOptionsMenu();
        assertTrue(activity.onOptionsItemSelected(optionsMenu.findItem(R.id.action_log)));
    }

    @Test
    public void canStartLogActivityOnMenuItemClick() throws Exception {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        shadowOf(activity).onCreateOptionsMenu(toolbar.getMenu());
        Menu optionsMenu = shadowOf(activity).getOptionsMenu();
        assertTrue(activity.onOptionsItemSelected(optionsMenu.findItem(R.id.action_log)));
        Intent expectedIntent = new Intent(activity, LogActivity.class);
        assertEquals(expectedIntent.toString(),
                shadowOf(activity).getNextStartedActivity().toString());
    }

    @Test
    public void hasViewFab_TestOnCreate() throws Exception {
        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        assertNotNull(fab);
    }

    @Test
    public void testSpinnerHasEntries_ToolBarSpinner() throws Exception {
        Spinner spinner = (Spinner) activity.findViewById(R.id.spDrillsSort);
        assertNotNull(spinner);
        assertTrue(spinner.getCount() > 0);
    }

    @Test
    public void doesHideSearchView_TestOnCreate() throws Exception {
        SearchView searchView = (SearchView) activity.findViewById(R.id.searchView);
        assertEquals(View.GONE, searchView.getVisibility());
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activity = null;
    }

}