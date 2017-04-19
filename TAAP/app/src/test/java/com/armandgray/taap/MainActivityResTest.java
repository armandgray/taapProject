package com.armandgray.taap;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

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
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityResTest {

    private ActivityController<MainActivity> activityController;
    private MainActivity activity;
    private MainActivityViews views;
    private Toolbar toolbar;
    private Menu optionsMenu;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(MainActivity.class);
        activity = activityController.create().visible().get();
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        shadowOf(activity).onCreateOptionsMenu(toolbar.getMenu());
        optionsMenu = shadowOf(activity).getOptionsMenu();
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
    public void hasOptionsMenuItem_Settings() throws Exception {
        assertNotNull(optionsMenu.findItem(R.id.action_settings));
    }

    @Test
    public void hasOptionsMenuItem_Log() throws Exception {
        assertNotNull(optionsMenu.findItem(R.id.action_log));
    }

    @Test
    public void existsRes_SortContainer() throws Exception {
        RelativeLayout contentMain = (RelativeLayout) View.inflate(activity, R.layout.content_main, null);
        RelativeLayout sortContainer = (RelativeLayout) contentMain.findViewById(R.id.sortContainer);
        assertNotNull(sortContainer);
    }

    @Test
    public void existsView_SortContainer_FirstChildIcSort() throws Exception {
        RelativeLayout sortContainer = (RelativeLayout) View.inflate(activity, R.layout.sort_container_layout, null);
        assertNotNull(sortContainer.getChildAt(0));
        assertTrue(sortContainer.getChildAt(0) instanceof ImageView);
    }

    @Test
    public void existsView_SortContainer_Spinner() throws Exception {
        RelativeLayout sortContainer = (RelativeLayout) View.inflate(activity, R.layout.sort_container_layout, null);
        assertNotNull(sortContainer.findViewById(R.id.spDrillsSort));
        assertTrue(((Spinner) sortContainer.findViewById(R.id.spDrillsSort))
                .getCount() > 0);
    }

    @Test
    public void existsView_SortContainer_LastChildIcSearch() throws Exception {
        RelativeLayout sortContainer = (RelativeLayout) View.inflate(activity, R.layout.sort_container_layout, null);
        assertNotNull(sortContainer.getChildAt(sortContainer.getChildCount() - 1));
        assertTrue(sortContainer.getChildAt(sortContainer.getChildCount() - 1) instanceof ImageView);
    }

    @Test
    public void hasView_EditTextSearch() throws Exception {
        EditText etSearch = (EditText) activity.findViewById(R.id.etSearch);
        assertNotNull(etSearch);
    }

    @Test
    public void hasView_RvDrills() throws Exception {
        RecyclerView rvDrills = (RecyclerView) activity.findViewById(R.id.rvDrills);
        assertNotNull(rvDrills);
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
        toolbar = null;
        optionsMenu = null;
    }

}