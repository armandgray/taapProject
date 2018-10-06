package com.armandgray.taap.main;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;
import com.armandgray.taap.detail.DrillDetailActivity;
import com.armandgray.taap.log.LogActivity;
import com.armandgray.taap.models.Drill;
import com.armandgray.taap.settings.SettingsActivity;
import com.armandgray.taap.utils.ActivitySetupHelper;

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

import java.util.Arrays;
import java.util.List;

import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_DELETE_ALL_DATA;
import static com.armandgray.taap.models.Drill.BALL_HANDLING;
import static com.armandgray.taap.models.Drill.FUNDAMENTALS;
import static com.armandgray.taap.models.Drill.SHOOTING;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class MainActivityControllerTest {

    private static final Context CONTEXT = RuntimeEnvironment.application;
    private static final String[] TEST_SPINNER_CATEGORIES = {FUNDAMENTALS, BALL_HANDLING, SHOOTING};

    @Mock
    private ActionBar mockActionBar;

    @Mock
    private Context mockContext;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private MainActivityController controller;
    private Intent answeredIntent;
    private boolean[] testFlags;
    private boolean[] updateDataFlags;
    private final ActivitySetupHelper.ActivityViewsInterface testViewsInterface =
            new ActivitySetupHelper.ActivityViewsInterface() {
                @Test @Ignore
                public void setListener(Object object) {
                    testFlags[0] = object instanceof MainActivityViews.MainViewsListener;
                }

                @Test @Ignore
                public void setupActivityCoordinatorWidgets() {
                    testFlags[1] = true;
                }

                @Test @Ignore
                public void setupActivityInitialState() {
                    testFlags[2] = true;
                }

                @Test @Ignore
                public void updateData(Object object) {
                    if (object instanceof MenuItem) {
                        updateDataFlags[0] = true;
                        return;
                    }

                    if (object instanceof EditText) {
                        updateDataFlags[1] = true;
                        return;
                    }

                    if (object instanceof List) {
                        testFlags[3] = true;
                        return;
                    }

                    if (object instanceof String) {
                        String s = (String) object;
                        if (Arrays.asList(TEST_SPINNER_CATEGORIES).contains(s)) {
                            updateDataFlags[2] = true;
                        } else if (Arrays.asList(MainActivityViews.ACTIONS).contains(s)) {
                            switch (s) {
                                case MainActivityViews.ON_FAB_CLICK:
                                    updateDataFlags[3] = true;
                                    return;

                                case MainActivityViews.ON_SORT_CLICK:
                                    updateDataFlags[4] = true;
                                    return;

                                case MainActivityViews.ON_SEARCH_CLICK:
                                    updateDataFlags[5] = true;
                                    return;

                                case MainActivityViews.ON_ET_SEARCH_FOCUS_CHANGE:
                                    updateDataFlags[6] = true;
                                    return;
                            }
                        } else {
                            updateDataFlags[7] = true;
                        }
                    }
                }
            };

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        testFlags = new boolean[4];
        updateDataFlags = new boolean[8];
        controller = new MainActivityController(CONTEXT, mockActionBar, testViewsInterface);
        Mockito.doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                answeredIntent = (Intent) args[0];
                return null;
            }
        }).when(mockContext).startActivity((Intent) Mockito.anyObject());
    }

    @Test @Ignore
    @SuppressWarnings("all")
    public void doesImplementActivityControllerInterface_TestConstructor() throws Exception {
        assertTrue(controller instanceof ActivitySetupHelper.ActivityControllerInterface);
    }

    @Test @Ignore
    public void doesSetupActivityViewController_TestConstructor() throws Exception {
        assertNotNull(controller.viewsInterface);
        assertEquals(testViewsInterface, controller.viewsInterface);
        for (int i = 0; i < testFlags.length; i++) {
            assertTrue(testFlags[i]);
        }

        Mockito.verifyNoMoreInteractions(mockActionBar);
    }

    @Test @Ignore
    public void doesUpdateViewsData_MethodTest_OnFabClick() {
        controller.onFabClick();
        assertTrue(updateDataFlags[3]);
    }

    @Test @Ignore
    public void doesUpdateViewsData_MethodTest_OnSortClick() {
        controller.onSortClick();
        assertTrue(updateDataFlags[4]);
    }

    @Test @Ignore
    public void doesUpdateViewsData_MethodTest_OnSpinnerItemSelected() {
        controller.onSpinnerItemSelected(BALL_HANDLING);
        assertTrue(updateDataFlags[2]);
    }

    @Test @Ignore
    public void doesUpdateViewsData_MethodTest_OnSearchClick() {
        controller.onSearchClick();
        assertTrue(updateDataFlags[5]);
    }

    @Test @Ignore
    public void doesUpdateViewsDataIfNotHasFocus_MethodTest_OnEtSearchFocusChange() {
        controller.onEtSearchFocusChange(false);
        assertTrue(updateDataFlags[6]);
    }

    @Test @Ignore
    public void doesNotUpdateViewsDataIfHasFocus_MethodTest_OnEtSearchFocusChange() {
        controller.onEtSearchFocusChange(true);
        assertFalse(updateDataFlags[6]);
    }

    @Test @Ignore
    public void doesUpdateViewsData_MethodTest_OnEtSearchTextChanged() {
        controller.onEtSearchTextChanged("wall");
        assertTrue(updateDataFlags[7]);
    }

    @Test @Ignore
    public void doesStartActivity_MethodTest_OnRvDrillsItemTouch() {
        Drill testDrill = new Drill("hello", 0, new String[]{"test"});
        controller.context = mockContext;
        controller.onRvDrillsItemTouch(testDrill);

        Mockito.verify(mockContext, Mockito.times(1))
                .startActivity(new Intent(mockContext, DrillDetailActivity.class));
        assertNotNull(answeredIntent);
        // TODO assert answeredIntent Class & extra
    }

    @Test @Ignore
    public void doesUpdateViewsData_MethodTest_OnCreateOptionsMenu() {
        Menu menu = new PopupMenu(CONTEXT, null).getMenu();
        menu.add(0, R.id.action_log, 0, "Log");
        controller.onCreateOptionsMenu(menu);
        assertTrue(updateDataFlags[0]);
    }

    @Test @Ignore
    public void doesUpdateViewsData_ActionSettings_MethodTest_OnOptionsItemSelected() {
        Menu menu = new PopupMenu(CONTEXT, null).getMenu();
        menu.add(0, R.id.action_settings, 0, "Settings");
        MenuItem menuItem = menu.getItem(0);
        assertEquals(R.id.action_settings, menuItem.getItemId());

        controller.context = mockContext;
        controller.onOptionsItemSelected(menuItem);

        Mockito.verify(mockContext, Mockito.times(1))
                .startActivity(new Intent(mockContext, SettingsActivity.class));
        assertNotNull(answeredIntent);
        // TODO assert answeredIntent Class
    }

    @Test @Ignore
    public void doesUpdateViewsData_ActionLog_MethodTest_OnOptionsItemSelected() {
        Menu menu = new PopupMenu(CONTEXT, null).getMenu();
        menu.add(0, R.id.action_log, 0, "Log");
        MenuItem menuItem = menu.getItem(0);
        assertEquals(R.id.action_log, menuItem.getItemId());

        controller.context = mockContext;
        controller.onOptionsItemSelected(menuItem);

        Mockito.verify(mockContext, Mockito.times(1))
                .startActivity(new Intent(mockContext, LogActivity.class));
        assertNotNull(answeredIntent);
        // TODO assert answeredIntent Class
    }

    @Test @Ignore
    public void doesUpdateViewsData_MethodTest__DispatchTouchEvent() {
        EditText editText = new EditText(CONTEXT);
        MotionEvent ev = MotionEvent.obtain(200, 300, MotionEvent.ACTION_DOWN, 0.0f, 0.0f, 0);
        controller.dispatchTouchEvent(editText, ev);

        assertTrue(updateDataFlags[1]);
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        CONTEXT.getContentResolver().delete(CONTENT_URI_DELETE_ALL_DATA, null, null);
        testFlags = null;
        updateDataFlags = null;
        controller = null;
    }

}
