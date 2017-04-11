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

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DrillActivityTest {

    @Test
    public void testContainsView_ToolBarSpinner() throws Exception {
        Activity activity = Robolectric.buildActivity(DrillActivity.class).create().visible().get();
        assertNotNull(activity);

        Spinner spinner = (Spinner) activity.findViewById(R.id.spDrillsSort);
        assertNotNull(spinner);
    }

    @Test
    public void testContainsView_Fab() throws Exception {
        Activity activity = Robolectric.buildActivity(DrillActivity.class).create().visible().get();
        assertNotNull(activity);

        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        assertNotNull(fab);
    }

    @Test
    public void testContainsView_RvDrills() throws Exception {
        Activity activity = Robolectric.buildActivity(DrillActivity.class).create().visible().get();
        assertNotNull(activity);

        RecyclerView rvDrills = (RecyclerView) activity.findViewById(R.id.rvDrills);
        assertNotNull(rvDrills);
    }

}