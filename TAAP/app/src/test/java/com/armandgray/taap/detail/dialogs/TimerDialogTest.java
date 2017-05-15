package com.armandgray.taap.detail.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;
import com.armandgray.taap.detail.DrillDetailActivity;
import com.armandgray.taap.models.Drill;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowDialog;

import static com.armandgray.taap.MainActivity.SELECTED_DRILL;
import static com.armandgray.taap.detail.dialogs.DetailSummaryDialog.DIALOG;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class TimerDialogTest {

    private ActivityController<DrillDetailActivity> activityController;
    private DrillDetailActivity activity;
    private TimerDialog dialog;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        Intent intent = new Intent(RuntimeEnvironment.application, DrillDetailActivity.class);
        intent.putExtra(SELECTED_DRILL, new Drill("Beat-the-Pro (Mid-Range)",
                R.drawable.ic_fitness_center_white_24dp,
                Drill.SHOOTING_ARRAY));
        activityController = Robolectric.buildActivity(DrillDetailActivity.class).withIntent(intent);
        activity = activityController.create().start().resume().visible().get();
        dialog = new TimerDialog();
        dialog.show(activity.getSupportFragmentManager(), DIALOG);
    }

    @Test
    public void canTimerDialog() {
        assertNotNull(new TimerDialog());
    }

    @Test
    public void canCreateTimerDialog_TestOnCreateDialog() {
        Bundle savedInstanceState = new Bundle();
        assertNotNull(dialog.onCreateDialog(savedInstanceState));
    }

    @Test
    public void doesImplementDialogFragment() {
        DialogFragment dialogFragment = dialog;
        assertNotNull(dialogFragment);
    }

    @Test
    public void existsLayout_TimerDialogLayout() {
        CoordinatorLayout TimerDialogLayout = (CoordinatorLayout) View
                .inflate(activity, R.layout.timer_dialog_layout, null);
        assertNotNull(TimerDialogLayout);
    }

    @Test
    public void existsView_Chronometer_TimerDialogLayout() {
        CoordinatorLayout TimerDialogLayout = (CoordinatorLayout) View
                .inflate(activity, R.layout.timer_dialog_layout, null);
        assertNotNull(TimerDialogLayout.findViewById(R.id.chronometer));
    }

    @Test
    public void existsView_Fab_TimerDialogLayout() {
        CoordinatorLayout TimerDialogLayout = (CoordinatorLayout) View
                .inflate(activity, R.layout.timer_dialog_layout, null);
        assertNotNull(TimerDialogLayout.findViewById(R.id.fab));
    }

    @Test
    public void doesFillEntireScreen_DialogGetWindow() {
        Window window = dialog.getDialog().getWindow();

        // TODO add test for window layout params & background
        assertNotNull(window);
        assertNotNull(window.getAttributes());
    }

    @Test
    public void doesHaveCustomView_TestOnCreateDialog() {
        Window window = dialog.getDialog().getWindow();

        assertNotNull(window);
        assertNotNull(window.findViewById(R.id.timerDialogContainer));
    }

    @Test
    public void doesStartTimer_TestOnCreateDialog() {
        Bundle savedInstanceState = new Bundle();
        AlertDialog resultDialog = (AlertDialog) dialog.onCreateDialog(savedInstanceState);
        ShadowAlertDialog shadowDialog = shadowOf(resultDialog);

        // TODO add test for chronometer started
    }

    @Test
    public void canClickFabToDismissDialog() throws Exception {
        Dialog dialog = ShadowDialog.getLatestDialog();
        assertNotNull(dialog);
        assertTrue(dialog.isShowing());

        FloatingActionButton fab = (FloatingActionButton) dialog.findViewById(R.id.fab);
        assertNotNull(fab);
        fab.performClick();

        assertFalse(dialog.isShowing());
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        dialog.dismiss();
        activityController.pause().stop().destroy();
        activity = null;
        dialog = null;
    }

}