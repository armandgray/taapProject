package com.armandgray.taap.detail;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlertDialog;

import static com.armandgray.taap.detail.DetailSummaryDialog.DIALOG;
import static junit.framework.Assert.assertNotNull;
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
        activityController = Robolectric.buildActivity(DrillDetailActivity.class);
        activity = activityController.create().visible().get();
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
        LinearLayout TimerDialogLayout = (LinearLayout) View
                .inflate(activity, R.layout.timer_dialog_layout, null);
        assertNotNull(TimerDialogLayout);
    }

    @Test
    public void existsView_Chronometer_TimerDialogLayout() {
        LinearLayout TimerDialogLayout = (LinearLayout) View
                .inflate(activity, R.layout.timer_dialog_layout, null);
        assertNotNull(TimerDialogLayout.findViewById(R.id.chronometer));
    }

    @Test
    public void doesHaveCustomView_TestOnCreateDialog() {
        Bundle savedInstanceState = new Bundle();
        AlertDialog resultDialog = (AlertDialog) dialog.onCreateDialog(savedInstanceState);
        ShadowAlertDialog shadowDialog = shadowOf(resultDialog);

        assertNotNull(resultDialog);
        assertNotNull(shadowDialog);
        assertNotNull(shadowDialog.getView());
        assertNotNull(shadowDialog.getView().findViewById(R.id.timerDialogContainer));
    }

    @Test
    public void doesStartTimer_TestOnCreateDialog() {
        Bundle savedInstanceState = new Bundle();
        AlertDialog resultDialog = (AlertDialog) dialog.onCreateDialog(savedInstanceState);
        ShadowAlertDialog shadowDialog = shadowOf(resultDialog);
        Chronometer chronometer = (Chronometer) shadowDialog.getView()
                .findViewById(R.id.chronometer);

        // TODO add test for chronometer started
        assertNotNull(chronometer);
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
        dialog = null;
    }

}