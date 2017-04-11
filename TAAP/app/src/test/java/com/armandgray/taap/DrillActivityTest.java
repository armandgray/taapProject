package com.armandgray.taap;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Spinner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DrillActivityTest {

    @Test
    public void testContainsView_ToolBar() throws Exception {
        Activity activity = Robolectric.buildActivity(DrillActivity.class).create().visible().get();
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        assertNotNull(toolbar);
    }

    @Test
    public void testContainsView_ToolBarSpinner() throws Exception {
        Activity activity = Robolectric.buildActivity(DrillActivity.class).create().visible().get();
        Spinner spinner = (Spinner) activity.findViewById(R.id.spDrillsSort);
        assertNotNull(spinner);
    }

    @Test
    public void testContainsView_ToolBarSearch() throws Exception {
        Activity activity = Robolectric.buildActivity(DrillActivity.class).create().visible().get();
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        shadowOf(activity).onCreateOptionsMenu(toolbar.getMenu());
        Menu optionsMenu = shadowOf(activity).getOptionsMenu();
        assertNotNull(optionsMenu);
        assertNotNull(optionsMenu.findItem(R.id.action_settings));
    }

    @Test
    public void testExistsRes_SpinnerEntries() throws Exception {
        Activity activity = Robolectric.buildActivity(DrillActivity.class).create().visible().get();
        String[] drillsArray = activity.getResources().getStringArray(R.array.drills_array);
        assertTrue(drillsArray.length > 0);
    }

    @Test
    public void testSpinnerHasEntries_ToolBarSpinner() throws Exception {
        Activity activity = Robolectric.buildActivity(DrillActivity.class).create().visible().get();
        Spinner spinner = (Spinner) activity.findViewById(R.id.spDrillsSort);
        assertNotNull(spinner);
        assertTrue(spinner.getCount() > 0);
    }

    @Test
    public void testContainsView_Fab() throws Exception {
        Activity activity = Robolectric.buildActivity(DrillActivity.class).create().visible().get();
        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        assertNotNull(fab);
    }

    @Test
    public void testContainsView_RvDrills() throws Exception {
        Activity activity = Robolectric.buildActivity(DrillActivity.class).create().visible().get();
        RecyclerView rvDrills = (RecyclerView) activity.findViewById(R.id.rvDrills);
        assertNotNull(rvDrills);
    }

}