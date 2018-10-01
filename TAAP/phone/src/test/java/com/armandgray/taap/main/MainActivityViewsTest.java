package com.armandgray.taap.main;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;
import com.armandgray.taap.main.MainActivityViews.MainViewsListener;
import com.armandgray.taap.models.Drill;
import com.armandgray.taap.utils.ActivitySetupHelper;
import com.armandgray.taap.utils.DrillsRvAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;
import static com.armandgray.taap.db.DrillsDataHelper.getDrillsList;
import static com.armandgray.taap.models.Drill.getQueryResultList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class MainActivityViewsTest {

    private static final Context CONTEXT = RuntimeEnvironment.application;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private FloatingActionButton mockFab;

    private MainActivityViews views;
    private View testRootView;
    private boolean[] testFlags;
    private MainViewsListener testViewsListener = new MainViewsListener() {
        @Override
        public void onFabClick() {
            testFlags[0] = true;
        }

        @Override
        public void onSortClick() {
            testFlags[1] = true;
        }

        @Override
        public void onSpinnerItemSelected(String drillType) {
            testFlags[2] = true;
        }

        @Override
        public void onSearchClick() {
            testFlags[3] = true;
        }

        @Override
        public void onEtSearchFocusChange(boolean hasFocus) {
            testFlags[4] = true;
        }

        @Override
        public void onEtSearchTextChanged(String s) {
            testFlags[5] = true;
        }

        @Override
        public void onRvDrillsItemTouch(Drill drill) {
            testFlags[6] = true;
        }
    };

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        testFlags = new boolean[7];
        LayoutInflater inflater = LayoutInflater.from(RuntimeEnvironment.application);
        testRootView = inflater.inflate(R.layout.content_main, null);

        Mockito.when(mockFab.performClick()).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                views.listener.onFabClick();
                return null;
            }
        });

        views = new MainActivityViews(testRootView, mockFab, CONTEXT);
        views.setListener(testViewsListener);
    }

    @Test @Ignore
    @SuppressWarnings("all")
    public void doesImplementActivityViewsInterface_TestConstructor() throws Exception {
        assertTrue(views instanceof ActivitySetupHelper.ActivityViewsInterface);
    }

    @Test @Ignore
    public void doesSetRootView_TestConstructor() throws Exception {
        assertNotNull(views);
        assertNotNull(views.rootView);
        assertEquals(views.rootView, testRootView);
    }

    @Test @Ignore
    @SuppressWarnings("all")
    public void canSetListener_MethodTest() throws Exception {
        assertNotNull(views.listener);
        assertTrue(views.listener instanceof MainViewsListener);
    }

    @Test @Ignore
    public void doesSetupFABClickListener_MethodTest_setupActivityCoordinatorWidgets() throws Exception {
        views.setupActivityInitialState();
        views.fab.performClick();
        Mockito.verify(mockFab, Mockito.times(1)).performClick();
        assertTrue(testFlags[0]);
    }

    @Test @Ignore
    public void doesSetupIvSortClickListener_MethodTest() throws Exception {
        views.setupActivityInitialState();
        testRootView.findViewById(R.id.ivSort).performClick();
        assertTrue(testFlags[1]);
    }

    @Test @Ignore
    public void doesSetupSpinnerClickListener_MethodTest() throws Exception {
        views.setupActivityInitialState();
        Spinner spinner = (Spinner) testRootView.findViewById(R.id.spDrillsSort);
        spinner.onClick(new DialogInterface() {
            @Override
            public void cancel() {}

            @Override
            public void dismiss() {}
        }, 1);
        assertTrue(testFlags[2]);
    }

    @Test @Ignore
    public void doesSetupSearchVisibility_HideSearchView_MethodTest() throws Exception {
        views.setupActivityInitialState();
        EditText etSearch = (EditText) testRootView.findViewById(R.id.etSearch);
        assertTrue(etSearch.isFocusable());
        assertEquals(View.GONE, etSearch.getVisibility());
    }

    @Test @Ignore
    public void doesSetupIvSearchClickListener_MethodTest() throws Exception {
        views.setupActivityInitialState();
        testRootView.findViewById(R.id.ivSearch).performClick();
        assertTrue(testFlags[3]);
    }

    @Test @Ignore
    public void doesSetupEtSearchFocusChangeListener_MethodTest() throws Exception {
        views.setupActivityInitialState();
        // TODO
    }

    @Test @Ignore
    public void doesSetupEtSearchTextChangeListener_MethodTest() throws Exception {
        views.setupActivityInitialState();
        EditText etSearch = (EditText) testRootView.findViewById(R.id.etSearch);
        etSearch.setText("WALL");
        assertTrue(testFlags[5]);
    }

    @Test @Ignore
    public void doesSetupRvDrills_MethodTest() throws Exception {
        views.setupActivityInitialState();
        RecyclerView rvDrills = (RecyclerView) testRootView.findViewById(R.id.rvDrills);
        DrillsRvAdapter adapter = (DrillsRvAdapter) rvDrills.getAdapter();
        assertEquals(0, adapter.getItemCount());
    }

    @Test @Ignore
    public void doesSetupRvDrillsClickListener_MethodTest() throws Exception {
        views.setupActivityInitialState();
        views.updateData(getQueryResultList(getDrillsList(), "WALL"));

        RecyclerView rvDrills = (RecyclerView) testRootView.findViewById(R.id.rvDrills);
        assertNotNull(rvDrills);
        MotionEvent motionEvent1 = MotionEvent.obtain(200, 300, MotionEvent.ACTION_DOWN, 0.001f, 0.001f, 0);
        MotionEvent motionEvent2 = MotionEvent.obtain(200, 300, MotionEvent.ACTION_MOVE, 0.001f, 0.0012f, 0);
        MotionEvent motionEvent3 = MotionEvent.obtain(200, 300, MotionEvent.ACTION_MOVE, 0.0012f, 0.001f, 0);
        MotionEvent motionEvent4 = MotionEvent.obtain(200, 300, MotionEvent.ACTION_UP, 0.001f, 0.001f, 0);
        rvDrills.onInterceptTouchEvent(motionEvent1);
        rvDrills.onInterceptTouchEvent(motionEvent2);
        rvDrills.onInterceptTouchEvent(motionEvent3);
        rvDrills.onInterceptTouchEvent(motionEvent4);

        assertTrue(testFlags[6]);
    }

    @Test @Ignore
    public void canSetMenuItemColor_MethodTest_UpdateData() throws Exception {
        views.setupActivityInitialState();
        Menu menu = new PopupMenu(CONTEXT, null).getMenu();
        menu.add(0, 0, 0, "Option1").setShortcut('3', 'c');
        MenuItem menuItem = menu.getItem(0);
        menuItem.setIcon(R.drawable.ic_add_alarm_white_24dp);
        assertNotNull(menuItem);
        assertNotNull(menuItem.getIcon());
        assertNull(menuItem.getIcon().getColorFilter());

        views.updateData(menuItem);
        assertNotNull(menuItem);
        assertNotNull(menuItem.getIcon());
        assertNotNull(menuItem.getIcon().getColorFilter());
    }

    @Test @Ignore
    public void canPassEditTextToClearFocus_MethodTest_UpdateData() throws Exception {
        views.setupActivityInitialState();
        views.updateData(MainActivityViews.ON_SEARCH_CLICK);

        EditText etSearch = (EditText) testRootView.findViewById(R.id.etSearch);
        assertEquals(VISIBLE, etSearch.getVisibility());
        assertTrue(etSearch.isFocusable());
        assertTrue(etSearch.isFocused());

        views.updateData(etSearch);

        // TODO test dispatchTouchEvent (WORKING)
    }

    @Test @Ignore
    public void canPassListToUpdateDrills_MethodTest_UpdateData() throws Exception {
        views.setupActivityInitialState();

        List<Drill> drillList = getQueryResultList(getDrillsList(), "WALL");
        RecyclerView rvDrills = (RecyclerView) testRootView.findViewById(R.id.rvDrills);
        assertEquals(0, rvDrills.getAdapter().getItemCount());

        views.updateData(drillList);

        DrillsRvAdapter adapter = (DrillsRvAdapter) rvDrills.getAdapter();
        assertEquals(drillList.size(), adapter.getItemCount());
        for (int i = 0; i < adapter.getItemCount(); i++) {
            assertTrue(drillList.get(i).getTitle().equals(adapter.getItemAtPosition(i).getTitle()));
        }
    }

    @Test @Ignore
    public void canPassEmptyListToUpdateDrills_MethodTest_UpdateData() throws Exception {
        views.setupActivityInitialState();

        RecyclerView rvDrills = (RecyclerView) testRootView.findViewById(R.id.rvDrills);
        assertEquals(0, rvDrills.getAdapter().getItemCount());

        views.updateData(getQueryResultList(getDrillsList(), "WALL"));
        assertTrue(rvDrills.getAdapter().getItemCount() > 0);

        views.updateData(new ArrayList<>());

        DrillsRvAdapter adapter = (DrillsRvAdapter) rvDrills.getAdapter();
        assertEquals(0, adapter.getItemCount());
    }

    @Test @Ignore
    public void cannotPassNonDrillListToUpdateDrills_MethodTest_UpdateData() throws Exception {
        views.setupActivityInitialState();

        RecyclerView rvDrills = (RecyclerView) testRootView.findViewById(R.id.rvDrills);
        assertEquals(0, rvDrills.getAdapter().getItemCount());

        List<String> list = new ArrayList<>();
        list.add("Test");
        views.updateData(list);

        assertEquals(0, rvDrills.getAdapter().getItemCount());
    }

    @Test @Ignore
    public void canPassSpinnerDrillTypeToUpdateDrills_MethodTest_UpdateData() throws Exception {
        views.setupActivityInitialState();

        List<Drill> drillList = getDrillsList();
        RecyclerView rvDrills = (RecyclerView) testRootView.findViewById(R.id.rvDrills);
        DrillsRvAdapter adapter = (DrillsRvAdapter) rvDrills.getAdapter();
        views.updateData(drillList);
        assertEquals(drillList.size(), adapter.getItemCount());



        assertEquals(drillList.size(), adapter.getItemCount());
        for (int i = 0; i < adapter.getItemCount(); i++) {
            assertTrue(drillList.get(i).getTitle().equals(adapter.getItemAtPosition(i).getTitle()));
        }
    }

    @Test @Ignore
    public void canPassActionOnSortClickToShowSpinner_MethodTest_UpdateData() throws Exception {
        views.setupActivityInitialState();
        views.updateData(MainActivityViews.ON_SORT_CLICK);

        Spinner spDrillsSort = (Spinner) testRootView.findViewById(R.id.spDrillsSort);
        assertEquals(VISIBLE, spDrillsSort.getVisibility());
        assertTrue(spDrillsSort.isFocusable());
        assertTrue(spDrillsSort.hasFocus());
    }

    @Test @Ignore
    public void canPassActionOnFabClickToShowToast_MethodTest_UpdateData() throws Exception {
        views.setupActivityInitialState();
        views.updateData(MainActivityViews.ON_FAB_CLICK);

        assertEquals("Feature Coming Soon!", ShadowToast.getTextOfLatestToast());
    }

    @Test @Ignore
    public void canFocusEditTextForActionOnSearchClick_MethodTest_UpdateData() throws Exception {
        views.setupActivityInitialState();
        EditText etSearch = (EditText) testRootView.findViewById(R.id.etSearch);
        views.updateData(MainActivityViews.ON_SEARCH_CLICK);

        Mockito.verify(mockFab, Mockito.times(1)).setVisibility(View.GONE);
        assertEquals(View.GONE, testRootView.findViewById(R.id.spDrillsSort).getVisibility());
        assertEquals(VISIBLE, etSearch.getVisibility());
        assertTrue(etSearch.isFocusable());
        assertTrue(etSearch.isFocused());
    }

    @Test @Ignore
    public void canPassActionOnEtSearchFocusChangeToShowRestoreInitialUIState_MethodTest_UpdateData() throws Exception {
        views.setupActivityInitialState();

        EditText etSearch = (EditText) testRootView.findViewById(R.id.etSearch);
        views.updateData(MainActivityViews.ON_SEARCH_CLICK);

        Mockito.verify(mockFab, Mockito.times(1)).setVisibility(View.GONE);
        assertEquals(View.GONE, testRootView.findViewById(R.id.spDrillsSort).getVisibility());
        assertEquals(VISIBLE, etSearch.getVisibility());
        assertTrue(etSearch.isFocusable());
        assertTrue(etSearch.isFocused());

        views.updateData(MainActivityViews.ON_ET_SEARCH_FOCUS_CHANGE);

        assertEquals(View.GONE, etSearch.getVisibility());
        assertFalse(etSearch.isFocused());
        assertEquals(View.VISIBLE, testRootView.findViewById(R.id.spDrillsSort).getVisibility());
        Mockito.verify(mockFab, Mockito.times(1)).setVisibility(VISIBLE);
    }

    @Test @Ignore
    public void canPassStringToQueryDrills_MethodTest_UpdateData() throws Exception {
        views.setupActivityInitialState();
        String query = "WALL";

        ArrayList<Drill> allDrills = getDrillsList();
        views.updateData(allDrills);

        List<Drill> drillList = getQueryResultList(allDrills, query);
        RecyclerView rvDrills = (RecyclerView) testRootView.findViewById(R.id.rvDrills);
        assertEquals(allDrills.size(), rvDrills.getAdapter().getItemCount());

        views.updateData(query);

        DrillsRvAdapter adapter = (DrillsRvAdapter) rvDrills.getAdapter();
        assertEquals(drillList.size(), adapter.getItemCount());
        for (int i = 0; i < adapter.getItemCount(); i++) {
            assertTrue(drillList.get(i).getTitle().equals(adapter.getItemAtPosition(i).getTitle()));
        }
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        mockFab = null;
        views = null;
        testRootView = null;
        testFlags = null;
        testViewsListener = null;
    }

}