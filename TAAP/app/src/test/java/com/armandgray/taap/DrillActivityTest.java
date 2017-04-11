package com.armandgray.taap;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.widget.Spinner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DrillActivityTest {

    @Test
    public void testContainsView_ToolBarSpinner() throws Exception {
        Activity activity = Robolectric.buildActivity(DrillActivity.class).create().visible().get();
        Spinner spinner = (Spinner) activity.findViewById(R.id.spDrillsSort);
        assertNotNull(spinner);
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