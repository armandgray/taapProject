package com.armandgray.taap;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

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
public class MainActivityViewsTest {

    private MainActivity activity;
    private MainActivityViews views;
    private Toolbar toolbar;
    private Menu optionsMenu;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
        views = activity.controller.views;
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        shadowOf(activity).onCreateOptionsMenu(toolbar.getMenu());
        optionsMenu = shadowOf(activity).getOptionsMenu();
    }

    @Test
    public void activityInstanceOfMainActivity_TestConstructor() throws Exception {
        assertEquals("MainActivity", views.activity.getLocalClassName());
    }

    @Test
    public void hasText_ToolBarTitle() throws Exception {
        assertEquals("TAAP", toolbar.getTitle());
    }

    @Test
    public void hasView_ToolBarSpinner() throws Exception {
        Spinner spinner = (Spinner) activity.findViewById(R.id.spDrillsSort);
        assertNotNull(spinner);
    }

    @Test
    public void existsRes_SpinnerLayout() throws Exception {
        TextView tvSpinnerLayout = (TextView) View.inflate(activity, R.layout.spinner_drills_text_layout, null);
        assertNotNull(tvSpinnerLayout);
    }

    @Test
    public void hasOptionsMenuItem_Search() throws Exception {
        assertNotNull(optionsMenu.findItem(R.id.action_search));
    }

    @Test
    public void hasOptionsMenuItem_Log() throws Exception {
        assertNotNull(optionsMenu.findItem(R.id.action_log));
    }

    @Test
    public void hasView_SearchView() throws Exception {
        SearchView searchView = (SearchView) activity.findViewById(R.id.searchView);
        assertNotNull(searchView);
    }

    @Test
    public void hasView_RvDrills() throws Exception {
        RecyclerView rvDrills = (RecyclerView) activity.findViewById(R.id.rvDrills);
        assertNotNull(rvDrills);
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activity = null;
        toolbar = null;
        optionsMenu = null;
    }

}