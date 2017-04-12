package com.armandgray.taap;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
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

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DrillActivityViewsTest {

    private static DrillActivity activity;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        if (activity == null) {
            activity = Robolectric.buildActivity(DrillActivity.class).create().visible().get();
        }
    }

    @Test
    public void testContainsView_ToolBar() throws Exception {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        assertNotNull(toolbar);
    }

    @Test
    public void testContainsView_ToolBarSpinner() throws Exception {
        Spinner spinner = (Spinner) activity.findViewById(R.id.spDrillsSort);
        assertNotNull(spinner);
    }

    @Test
    public void testContainsView_ToolBarSearch() throws Exception {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        shadowOf(activity).onCreateOptionsMenu(toolbar.getMenu());
        Menu optionsMenu = shadowOf(activity).getOptionsMenu();
        assertNotNull(optionsMenu.findItem(R.id.action_search));
    }

    @Test
    public void testExistsRes_SpinnerEntries() throws Exception {
        String[] drillsArray = activity.getResources().getStringArray(R.array.drills_array);
        assertTrue(drillsArray.length > 0);
    }

    @Test
    public void testExistsRes_SpinnerLayout() throws Exception {
        TextView tvSpinnerLayout = (TextView) View.inflate(activity, R.layout.spinner_drills_text_layout, null);
        assertNotNull(tvSpinnerLayout);
    }

    @Test
    public void testSpinnerHasEntries_ToolBarSpinner() throws Exception {
        Spinner spinner = (Spinner) activity.findViewById(R.id.spDrillsSort);
        assertNotNull(spinner);
        assertTrue(spinner.getCount() > 0);
    }

    @Test
    public void testContainsView_Fab() throws Exception {
        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        assertNotNull(fab);
    }

    @Test
    public void testContainsView_RvDrills() throws Exception {
        RecyclerView rvDrills = (RecyclerView) activity.findViewById(R.id.rvDrills);
        assertNotNull(rvDrills);
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activity = null;
    }

}