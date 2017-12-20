package com.armandgray.taap.main;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_DELETE_ALL_DATA;
import static java.security.AccessController.getContext;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityResTest {

    private static final Context context = RuntimeEnvironment.application;

    private Menu menuMain;
    private RelativeLayout container;

    @Before
    @SuppressWarnings("ConstantConditions")
    public void setUp() {
        System.out.println("Running Set Up!");
        LayoutInflater inflater = LayoutInflater.from(context);
        container = (RelativeLayout) inflater.inflate(R.layout.content_main, null);
        menuMain = new PopupMenu(context, null).getMenu();
        MenuInflater menuInflater = new MenuInflater(context);
        menuInflater.inflate(R.menu.menu_main, menuMain);
    }

    @Test
    public void hasView_Spinner() throws Exception {
        Spinner spinner = (Spinner) container.findViewById(R.id.spDrillsSort);
        assertNotNull(spinner);
    }

    @Test
    public void hasOptionsMenuItem_Settings() throws Exception {
        assertNotNull(menuMain.findItem(R.id.action_settings));
    }

    @Test
    public void hasOptionsMenuItem_Log() throws Exception {
        assertNotNull(menuMain.findItem(R.id.action_log));
    }

    @Test
    public void existsRes_SortContainer() throws Exception {
        RelativeLayout sortContainer = (RelativeLayout) container.findViewById(R.id.sortContainer);
        assertNotNull(sortContainer);
    }

    @Test
    public void existsView_SortContainer_FirstChildIcSort() throws Exception {
        RelativeLayout sortContainer = (RelativeLayout) container.findViewById(R.id.sortContainer);
        assertNotNull(sortContainer.getChildAt(0));
        assertTrue(sortContainer.getChildAt(0) instanceof ImageView);
    }

    @Test
    public void existsRes_ArrayStrings_DrillTypesArray() throws Exception {
        assertNotNull(container.getResources().getStringArray(R.array.drill_types));
    }

    @Test
    public void existsView_SortContainer_Spinner() throws Exception {
        RelativeLayout sortContainer = (RelativeLayout) container.findViewById(R.id.sortContainer);
        assertNotNull(sortContainer.findViewById(R.id.spDrillsSort));
        assertTrue(((Spinner) sortContainer.findViewById(R.id.spDrillsSort)).getCount() > 0);
    }

    @Test
    public void existsView_SortContainer_LastChildIcSearch() throws Exception {
        RelativeLayout sortContainer = (RelativeLayout) container.findViewById(R.id.sortContainer);
        assertNotNull(sortContainer.getChildAt(sortContainer.getChildCount() - 1));
        assertTrue(sortContainer.getChildAt(sortContainer.getChildCount() - 1) instanceof ImageView);
    }

    @Test
    public void hasView_EditTextSearch() throws Exception {
        EditText etSearch = (EditText) container.findViewById(R.id.etSearch);
        assertNotNull(etSearch);
    }

    @Test
    public void hasView_RvDrills() throws Exception {
        RecyclerView rvDrills = (RecyclerView) container.findViewById(R.id.rvDrills);
        assertNotNull(rvDrills);
    }

    @Test
    public void existsLayout_DrillListItem() throws Exception {
        LinearLayout drillListItem = (LinearLayout) View.inflate(context, R.layout.drill_listitem, null);
        assertNotNull(drillListItem);
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        menuMain = null;
        container = null;
    }

}
