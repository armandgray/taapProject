package com.armandgray.taap.detail;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;
import com.armandgray.taap.models.Drill;
import com.armandgray.taap.models.SessionLog;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDialog;

import static com.armandgray.taap.MainActivity.SELECTED_DRILL;
import static com.armandgray.taap.detail.DetailSummaryDialog.DIALOG;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DrillDetailViewsTest {

    private static final String BEAT_THE_PRO_MID_RANGE = "Beat-the-Pro (Mid-Range)";
    private ActivityController<DrillDetailActivity> activityController;
    private DrillDetailActivity activity;
    private Toolbar toolbar;
    private DrillDetailViews views;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        Intent intent = new Intent(RuntimeEnvironment.application, DrillDetailActivity.class);
        intent.putExtra(SELECTED_DRILL, new Drill("Beat-the-Pro (Mid-Range)",
                R.drawable.ic_fitness_center_white_24dp,
                Drill.SHOOTING_ARRAY));
        activityController = Robolectric.buildActivity(DrillDetailActivity.class).withIntent(intent);
        activity = activityController.create().visible().get();
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        views = activity.controller.views;
    }

    @Test
    public void activityInstanceOfMainActivity_TestConstructor() throws Exception {
        assertEquals("detail.DrillDetailActivity", views.activity.getLocalClassName());
    }

    @Test
    public void doesSetContentView_MethodTest_SetupActivityInitialState() throws Exception {
        assertEquals(R.id.activityDrillDetailLayout, shadowOf(activity).getContentView().getId());
    }

    @Test
    public void doesSetHomeAsUpEnabled_MethodTest_SetupActivityInitialState() throws Exception {
        assertNotNull(activity.getSupportActionBar());
        final int displayOptions = activity.getSupportActionBar().getDisplayOptions();
        assertTrue((displayOptions & ActionBar.DISPLAY_SHOW_HOME) != 0);
        assertTrue((displayOptions & ActionBar.DISPLAY_HOME_AS_UP) != 0);
    }

    @Test
    public void doesSetupHideToolbarTitle_MethodTest_SetupActivityInitialState() throws Exception {
        ActionBar actionBar = activity.getSupportActionBar();
        assertNotNull(actionBar);
        final int displayOptions = activity.getSupportActionBar().getDisplayOptions();
        assertTrue((displayOptions & ActionBar.DISPLAY_SHOW_TITLE) == 0);
    }

    @Test
    public void hasCustomToolbarTitle() throws Exception {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        assertNotNull(tvTitle);
    }

    @Test
    public void doesSetCustomToolbarTitleText_MethodTest_SetupActivityInitialState() throws Exception {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        assertEquals(BEAT_THE_PRO_MID_RANGE, tvTitle.getText());
    }

    @Test
    public void doesSetNpSetsValues_MethodTest_SetupActivityInitialState() throws Exception {
        NumberPicker npSets = (NumberPicker) activity.findViewById(R.id.npSets);
        assertEquals(1, npSets.getMinValue());
        assertTrue(npSets.getMaxValue() > npSets.getMinValue());
    }

    @Test
    public void doesSetNpSetsWrapWheel_MethodTest_SetupActivityInitialState() throws Exception {
        NumberPicker npSets = (NumberPicker) activity.findViewById(R.id.npSets);
        assertTrue(npSets.getWrapSelectorWheel());
    }

    @Test
    public void doesSetNpRepsValues_MethodTest_SetupActivityInitialState() throws Exception {
        NumberPicker npReps = (NumberPicker) activity.findViewById(R.id.npReps);
        assertEquals(0, npReps.getMinValue());
        assertTrue(npReps.getMaxValue() > npReps.getMinValue());
    }

    @Test
    public void doesSetNpRepsWrapWheel_MethodTest_SetupActivityInitialState() throws Exception {
        NumberPicker npReps = (NumberPicker) activity.findViewById(R.id.npReps);
        assertTrue(npReps.getWrapSelectorWheel());
    }

    @Test
    public void doesSetNpSuccessesValues_MethodTest_SetupActivityInitialState() throws Exception {
        NumberPicker npSuccesses = (NumberPicker) activity.findViewById(R.id.npSuccesses);
        assertEquals(0, npSuccesses.getMinValue());
        assertTrue(npSuccesses.getMaxValue() > npSuccesses.getMinValue());
    }

    @Test
    public void doesSetNpSuccessesWrapWheel_MethodTest_SetupActivityInitialState() throws Exception {
        NumberPicker npSuccesses = (NumberPicker) activity.findViewById(R.id.npSuccesses);
        assertTrue(npSuccesses.getWrapSelectorWheel());
    }

    @Test
    public void doesSetupHideBtnFinishedVisibility_MethodTest_SetupActivityInitialState() throws Exception {
        assertEquals(View.GONE, views.btnFinished.getVisibility());
    }

    @Test
    public void canShowBtnFinishedOnFabClick() throws Exception {
        views.fab.performClick();
        Button btnFinished = (Button) activity.findViewById(R.id.btnFinished);
        assertEquals(View.VISIBLE, btnFinished.getVisibility());
    }

    @Test
    public void doesShowPauseIconOnFabOddNumClick() throws Exception {
        // TODO add test here
    }

    @Test
    public void doesShowPlayIconOnFabEvenNumClick() throws Exception {
        // TODO add test here
    }

    @Test
    public void canShowSummaryDialogOnBtnFinishedClick() throws Exception {
        Button btnFinished = (Button) activity.findViewById(R.id.btnFinished);
        btnFinished.setVisibility(View.VISIBLE);
        btnFinished.performClick();
        activityController.start().resume();

        SessionLog sessionLog = new SessionLog.Builder().create();
        DetailSummaryDialog expectedDialog = DetailSummaryDialog.newInstance(sessionLog);
        expectedDialog.show(activity.getSupportFragmentManager(), DIALOG);
        Dialog resultDialog = ShadowDialog.getLatestDialog();
        assertNotNull(resultDialog);
        assertEquals(expectedDialog.getDialog(), resultDialog);
    }

    @Test
    public void doesPassSessionLog_OnBtnFinishedClick() throws Exception {
        Button btnFinished = (Button) activity.findViewById(R.id.btnFinished);
        btnFinished.setVisibility(View.VISIBLE);
        btnFinished.performClick();
        activityController.start().resume();
        activity.getSupportFragmentManager().executePendingTransactions();

        DetailSummaryDialog dialog = (DetailSummaryDialog) activity.getSupportFragmentManager().findFragmentByTag(DIALOG);
        // TODO add assert for each sessionLog data point here getting the Fragment
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