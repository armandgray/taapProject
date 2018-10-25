package com.armandgray.taap.detail;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.armandgray.taap.R;
import com.armandgray.taap.detail.dialogs.DetailSummaryDialog;
import com.armandgray.taap.models.SessionLog;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDialog;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.armandgray.taap.detail.dialogs.DetailSummaryDialog.DIALOG;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DrillDetailViewsTest {

    private static final String BEAT_THE_PRO_MID_RANGE = "Beat-the-Pro (Mid-Range)";
    private ActivityController<DrillDetailActivity> activityController;
    private DrillDetailActivity activity;
    private Toolbar toolbar;
    private DrillDetailViews views;

    @Before
    public void setUp() {

//        Intent intent = new Intent(RuntimeEnvironment.application, DrillDetailActivity.class);
//        intent.putExtra(SELECTED_DRILL, new Drill("Beat-the-Pro (Mid-Range)",
//                R.drawable.ic_fitness_center_white_24dp,
//                Drill.SHOOTING_ARRAY));
//        activityController = Robolectric.buildActivity(DrillDetailActivity.class).newIntent(intent);
//        activity = activityController.create().visible().get();
//        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
//        views = activity.controller.views;
    }

    @Test @Ignore
    public void activityInstanceOfMainActivity_TestConstructor() throws Exception {
        assertEquals("detail.DrillDetailActivity", views.activity.getLocalClassName());
    }

    @Test @Ignore
    public void doesSetContentView_MethodTest_SetupActivityInitialState() throws Exception {
        assertEquals(R.id.activityDrillDetailLayout, shadowOf(activity).getContentView().getId());
    }

    @Test @Ignore
    public void doesSetHomeAsUpEnabled_MethodTest_SetupActivityInitialState() throws Exception {
        assertNotNull(activity.getSupportActionBar());
        final int displayOptions = activity.getSupportActionBar().getDisplayOptions();
        assertTrue((displayOptions & ActionBar.DISPLAY_SHOW_HOME) != 0);
        assertTrue((displayOptions & ActionBar.DISPLAY_HOME_AS_UP) != 0);
    }

    @Test @Ignore
    public void doesSetupHideToolbarTitle_MethodTest_SetupActivityInitialState() throws Exception {
        ActionBar actionBar = activity.getSupportActionBar();
        assertNotNull(actionBar);
        final int displayOptions = activity.getSupportActionBar().getDisplayOptions();
        assertTrue((displayOptions & ActionBar.DISPLAY_SHOW_TITLE) == 0);
    }

    @Test @Ignore
    public void hasCustomToolbarTitle() throws Exception {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        assertNotNull(tvTitle);
    }

    @Test @Ignore
    public void doesSetCustomToolbarTitleText_MethodTest_SetupActivityInitialState() throws Exception {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        assertEquals(BEAT_THE_PRO_MID_RANGE, tvTitle.getText());
    }

    @Test @Ignore
    public void doesSetupRvPreviousLogs() throws Exception {
        RecyclerView rvPreviousLogs = (RecyclerView) activity.findViewById(R.id.rvPreviousLogs);
        assertNotNull(rvPreviousLogs);
        assertNotNull(rvPreviousLogs.getAdapter());
        assertNotNull(rvPreviousLogs.getLayoutManager());
        assertTrue(rvPreviousLogs.getLayoutManager() instanceof LinearLayoutManager);
    }

    @Test @Ignore
    public void doesSetupRvCurrentLog() throws Exception {
        RecyclerView rvCurrentLog = (RecyclerView) activity.findViewById(R.id.rvCurrentLog);
        assertNotNull(rvCurrentLog);
        assertNotNull(rvCurrentLog.getAdapter());
        assertNotNull(rvCurrentLog.getLayoutManager());
        assertTrue(rvCurrentLog.getLayoutManager() instanceof LinearLayoutManager);
    }

    @Test @Ignore
    public void doesSetNpSetsValues_MethodTest_SetupActivityInitialState() throws Exception {
        NumberPicker npSets = (NumberPicker) activity.findViewById(R.id.npSets);
        assertEquals(1, npSets.getMinValue());
        assertTrue(npSets.getMaxValue() > npSets.getMinValue());
    }

    @Test @Ignore
    public void doesSetNpSetsWrapWheel_MethodTest_SetupActivityInitialState() throws Exception {
        NumberPicker npSets = (NumberPicker) activity.findViewById(R.id.npSets);
        assertTrue(npSets.getWrapSelectorWheel());
    }

    @Test @Ignore
    public void doesSetNpRepsValues_MethodTest_SetupActivityInitialState() throws Exception {
        NumberPicker npReps = (NumberPicker) activity.findViewById(R.id.npReps);
        assertEquals(1, npReps.getMinValue());
        assertTrue(npReps.getMaxValue() > npReps.getMinValue());
    }

    @Test @Ignore
    public void doesSetNpRepsWrapWheel_MethodTest_SetupActivityInitialState() throws Exception {
        NumberPicker npReps = (NumberPicker) activity.findViewById(R.id.npReps);
        assertTrue(npReps.getWrapSelectorWheel());
    }

    @Test @Ignore
    public void doesSetNpSuccessesValues_MethodTest_SetupActivityInitialState() throws Exception {
        NumberPicker npSuccesses = (NumberPicker) activity.findViewById(R.id.npSuccesses);
        assertEquals(0, npSuccesses.getMinValue());
        assertTrue(npSuccesses.getMaxValue() > npSuccesses.getMinValue());
    }

    @Test @Ignore
    public void doesSetNpSuccessesWrapWheel_MethodTest_SetupActivityInitialState() throws Exception {
        NumberPicker npSuccesses = (NumberPicker) activity.findViewById(R.id.npSuccesses);
        assertTrue(npSuccesses.getWrapSelectorWheel());
    }

    @Test @Ignore
    public void doesSetupHideBtnFinishedVisibility_MethodTest_SetupActivityInitialState() throws Exception {
        assertEquals(View.GONE, views.btnFinished.getVisibility());
    }

    @Test @Ignore
    public void canShowBtnFinishedOnFabClick() throws Exception {
        views.fab.performClick();
        Button btnFinished = (Button) activity.findViewById(R.id.btnFinished);
        assertEquals(View.VISIBLE, btnFinished.getVisibility());
    }

    @Test @Ignore
    public void doesShowPauseIconOnFabOddNumClick() throws Exception {
        // TODO add test here
    }

    @Test @Ignore
    public void doesShowPlayIconOnFabEvenNumClick() throws Exception {
        // TODO add test here
    }

    @Test @Ignore
    public void canShowSummaryDialogOnBtnFinishedClick() throws Exception {
        Button btnFinished = (Button) activity.findViewById(R.id.btnFinished);
        btnFinished.setVisibility(View.VISIBLE);
        btnFinished.performClick();
        activityController.start().resume();

        Dialog resultDialog = ShadowDialog.getLatestDialog();
        SessionLog sessionLog = new SessionLog.Builder().create();
        DetailSummaryDialog expectedDialog = DetailSummaryDialog.newInstance(sessionLog);
        expectedDialog.show(activity.getSupportFragmentManager(), DIALOG);
        assertNotNull(resultDialog);
        assertEquals(
                expectedDialog.getDialog().findViewById(R.id.detailSummaryDialogContainer).getId(),
                resultDialog.findViewById(R.id.detailSummaryDialogContainer).getId());
    }

    @Test @Ignore
    public void doesPassSessionLogAndLogHistory_OnBtnFinishedClick() throws Exception {
        Button btnFinished = (Button) activity.findViewById(R.id.btnFinished);
        btnFinished.setVisibility(View.VISIBLE);
        btnFinished.performClick();
        activityController.start().resume();
        activity.getSupportFragmentManager().executePendingTransactions();

        DetailSummaryDialog dialog = (DetailSummaryDialog) activity.getSupportFragmentManager().findFragmentByTag(DIALOG);
        // TODO add assert for each sessionLog data point & HISTORY here getting the Fragment
    }

    @After
    public void tearDown() {

//        activity.finish();
//        activityController.pause().stop().destroy();
//        activity = null;
//        toolbar = null;
//        views = null;
    }

}