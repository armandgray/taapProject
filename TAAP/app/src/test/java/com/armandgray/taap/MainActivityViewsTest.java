package com.armandgray.taap;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.armandgray.taap.models.Drill;
import com.armandgray.taap.settings.SettingsActivity;
import com.armandgray.taap.utils.DrillsRvAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;

import static com.armandgray.taap.models.Drill.SHOOTING;
import static com.armandgray.taap.models.Drill.getQueryResultList;
import static com.armandgray.taap.utils.DrillsHelper.getDrillsList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityViewsTest {

    private static final String WALL = "wall";
    private ActivityController<MainActivity> activityController;
    private MainActivity activity;
    private Toolbar toolbar;
    private MainActivityViews views;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(MainActivity.class);
        activity = activityController.create().visible().get();
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        views = activity.controller.views;
    }

    @Test
    public void activityInstanceOfMainActivity_TestConstructor() throws Exception {
        assertEquals("MainActivity", views.activity.getLocalClassName());
    }

    @Test
    public void doesSetContentView_MethodTest_SetupActivityInitialState() throws Exception {
        assertEquals(R.id.activityMainLayout, shadowOf(activity).getContentView().getId());
    }

    @Test
    public void canGetOptionsMenu_TestOnCreate() throws Exception {
        shadowOf(activity).onCreateOptionsMenu(toolbar.getMenu());
        assertNotNull(shadowOf(activity).getOptionsMenu());
    }

    @Test
    public void canSelectOptionsMenuItem_Settings() throws Exception {
        shadowOf(activity).onCreateOptionsMenu(toolbar.getMenu());
        Menu optionsMenu = shadowOf(activity).getOptionsMenu();
        assertTrue(activity.onOptionsItemSelected(optionsMenu.findItem(R.id.action_settings)));
    }

    @Test
    public void canStartSettingsActivityOnSettingsMenuItemClick() throws Exception {
        shadowOf(activity).onCreateOptionsMenu(toolbar.getMenu());
        Menu optionsMenu = shadowOf(activity).getOptionsMenu();
        assertTrue(activity.onOptionsItemSelected(optionsMenu.findItem(R.id.action_settings)));
        Intent expectedIntent = new Intent(activity, SettingsActivity.class);
        assertEquals(expectedIntent.toString(),
                shadowOf(activity).getNextStartedActivity().toString());
    }

    @Test
    public void canSelectOptionsMenuItem_Log() throws Exception {
        shadowOf(activity).onCreateOptionsMenu(toolbar.getMenu());
        Menu optionsMenu = shadowOf(activity).getOptionsMenu();
        assertTrue(activity.onOptionsItemSelected(optionsMenu.findItem(R.id.action_log)));
    }

    @Test
    public void canStartLogActivityOnLogMenuItemClick() throws Exception {
        shadowOf(activity).onCreateOptionsMenu(toolbar.getMenu());
        Menu optionsMenu = shadowOf(activity).getOptionsMenu();
        assertTrue(activity.onOptionsItemSelected(optionsMenu.findItem(R.id.action_log)));
        Intent expectedIntent = new Intent(activity, LogActivity.class);
        assertEquals(expectedIntent.toString(),
                shadowOf(activity).getNextStartedActivity().toString());
    }

    @Test
    public void doesSetupFABClickListener_MethodTest() throws Exception {
        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        assertNotNull(fab);
    }

    @Test
    public void canClickSpinnerOnSortClick_MethodTest() throws Exception {
        // TODO test
    }

    @Test
    public void doesSetupSearchVisibility_HideSearchView_MethodTest() throws Exception {
        EditText etSearch = (EditText) activity.findViewById(R.id.etSearch);
        assertEquals(View.GONE, etSearch.getVisibility());
    }

    @Test
    public void canFocusEditTextOnIvSearchClick_MethodTest() throws Exception {
        ImageView ivSearch = (ImageView) activity.findViewById(R.id.ivSearch);
        ivSearch.performClick();
        EditText etSearch = (EditText) activity.findViewById(R.id.etSearch);
        assertEquals(View.VISIBLE, etSearch.getVisibility());
        assertTrue(etSearch.isFocusable());
        assertTrue(etSearch.isFocused());
    }

    @Test
    public void canClearFocusOnTouch() throws Exception {
        // TODO test dispatchTouchEvent (WORKING)
    }

    @Test
    public void canHideSpinnerAndFabOnIvSearchClick() throws Exception {
        Spinner spinner = (Spinner) activity.findViewById(R.id.spDrillsSort);
        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        ImageView ivSearch = (ImageView) activity.findViewById(R.id.ivSearch);
        ivSearch.performClick();
        assertEquals(View.GONE, spinner.getVisibility());
        assertEquals(View.GONE, fab.getVisibility());
    }

    @Test
    public void canSelectSpinnerItemToSortDrills() throws Exception {
        views.listener.onSpinnerItemSelected(null, null, 1, 0);
        DrillsRvAdapter adapter = (DrillsRvAdapter) views.rvDrills.getAdapter();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            String[] types = adapter.getItemAtPosition(i).getCategory();
            assertTrue(Arrays.asList(types).contains(SHOOTING));
        }

    }

    @Test
    public void canRestoreInitialUIOnEtSearchLoseFocus() throws Exception {
        Spinner spinner = (Spinner) activity.findViewById(R.id.spDrillsSort);
        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        activity.findViewById(R.id.ivSearch).performClick();
        EditText etSearch = (EditText) activity.findViewById(R.id.etSearch);
        etSearch.clearFocus();
        assertEquals(View.GONE, etSearch.getVisibility());
        assertEquals(View.VISIBLE, spinner.getVisibility());
        assertEquals(View.VISIBLE, fab.getVisibility());
    }

    @Test
    public void canChangeEtSearchTextToQueryDrillList() throws Exception {
        views.etSearch.setText(WALL);
        views.listener.onEtSearchTextChanged(null, 0, 0, 0);

        ArrayList<Drill> expectedList = getQueryResultList(getDrillsList(), WALL);

        DrillsRvAdapter adapter = (DrillsRvAdapter) views.rvDrills.getAdapter();
        ArrayList<Drill> adapterDrillList = new ArrayList<>();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            adapterDrillList.add(adapter.getItemAtPosition(i));
        }

        assertEquals(expectedList.size(), adapterDrillList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertTrue(expectedList.get(i).getTitle().equals(adapterDrillList.get(i).getTitle()));
        }
    }

    @Test
    public void canSelectSpinnerSearchItemToReSortDrills() throws Exception {
        views.etSearch.setText(WALL);
        views.listener.onEtSearchTextChanged(null, 0, 0, 0);

        int spinnerLastIndex = views.spinner.getAdapter().getCount() - 1;
        views.listener.onSpinnerItemSelected(null, null, 1, 0);
        views.listener.onSpinnerItemSelected(null, null, spinnerLastIndex, 0);

        ArrayList<Drill> expectedList = getQueryResultList(getDrillsList(), WALL);
        assertEquals(expectedList.size(), views.rvDrills.getAdapter().getItemCount());
    }

    @Test
    public void doesSetupRvDrills_MethodTest() throws Exception {
        assertNotNull(views.rvDrills);
        assertNotNull(views.rvDrills.getAdapter());
        assertNotNull(views.rvDrills.getLayoutManager());
        assertTrue(views.rvDrills.getLayoutManager() instanceof LinearLayoutManager);
        assertTrue(views.rvDrills.getAdapter().getItemCount() > 0);
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
        toolbar = null;
        views = null;
    }

}