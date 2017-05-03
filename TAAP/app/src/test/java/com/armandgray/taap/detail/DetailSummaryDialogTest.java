package com.armandgray.taap.detail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.LogActivity;
import com.armandgray.taap.R;
import com.armandgray.taap.models.SessionLog;

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
import static com.armandgray.taap.detail.DetailSummaryDialog.SESSION_LOG;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DetailSummaryDialogTest {

    private ActivityController<DrillDetailActivity> activityController;
    private DrillDetailActivity activity;
    private DetailSummaryDialog dialog;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(DrillDetailActivity.class);
        activity = activityController.create().visible().get();
        dialog = DetailSummaryDialog.newInstance(new SessionLog.Builder().create());
        dialog.show(activity.getSupportFragmentManager(), DIALOG);
    }

    @Test
    public void canCreateDetailSummaryDialog() {
        assertNotNull(new DetailSummaryDialog());
    }

    @Test
    public void canCreateDetailSummaryDialog_NewInstanceMethod() {
        SessionLog sessionLog = new SessionLog.Builder().create();
        DetailSummaryDialog dialog = DetailSummaryDialog.newInstance(sessionLog);
        assertNotNull(dialog);
        assertNotNull(dialog.getArguments());
        assertNotNull(SessionLog.class.cast(dialog.getArguments().get(SESSION_LOG)));
    }

    @Test
    public void canCreateDetailSummaryDialog_TestOnCreateDialog() {
        Bundle savedInstanceState = new Bundle();
        assertNotNull(dialog.onCreateDialog(savedInstanceState));
    }

    @Test
    public void doesImplementDialogFragment() {
        DialogFragment dialogFragment = dialog;
        assertNotNull(dialogFragment);
    }

    @Test
    public void existsLayout_DetailSummaryDialogLayout() {
        LinearLayout detailSummaryDialogLayout = (LinearLayout) View
                .inflate(activity, R.layout.detail_summary_dialog_layout, null);
        assertNotNull(detailSummaryDialogLayout);
    }

    @Test
    public void existsView_RvSummary_DetailSummaryDialogLayout() {
        LinearLayout detailSummaryDialogLayout = (LinearLayout) View
                .inflate(activity, R.layout.detail_summary_dialog_layout, null);
        assertNotNull(detailSummaryDialogLayout.findViewById(R.id.rvSummary));
    }

    @Test
    public void doesHaveCustomView_TestOnCreateDialog() {
        Bundle savedInstanceState = new Bundle();
        AlertDialog resultDialog = (AlertDialog) dialog.onCreateDialog(savedInstanceState);
        ShadowAlertDialog shadowDialog = shadowOf(resultDialog);

        assertNotNull(resultDialog);
        assertNotNull(shadowDialog);
        assertNotNull(shadowDialog.getView());
        assertNotNull(shadowDialog.getView().findViewById(R.id.detailSummaryDialogContainer));
    }

    @Test
    public void doesAssignActivityAsListener() {
        assertNotNull(dialog.listener);
    }

    @Test
    public void canClickNeutralContinueButtonToStartLogActivity_TestOnCreateDialog() {
        Bundle savedInstanceState = new Bundle();
        AlertDialog resultDialog = (AlertDialog) dialog.onCreateDialog(savedInstanceState);
        resultDialog.show();

        Button btnContinue = resultDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        btnContinue.performClick();
        Intent expectedIntent = new Intent(activity, LogActivity.class);
        assertEquals(expectedIntent.toString(),
                shadowOf(activity).getNextStartedActivity().toString());
        resultDialog.dismiss();
    }

    @Test
    public void canCancelDialogToStartLogActivity_TestOnCreateDialog() {
        Bundle savedInstanceState = new Bundle();
        AlertDialog resultDialog = (AlertDialog) dialog.onCreateDialog(savedInstanceState);
        resultDialog.show();
        resultDialog.cancel();

        Intent expectedIntent = new Intent(activity, LogActivity.class);
        // TODO verify test with assert
//        assertEquals(expectedIntent.toString(),
//                shadowOf(activity).getNextStartedActivity().toString());
    }

    @Test
    public void doesSetupRvSummary() throws Exception {
        Bundle savedInstanceState = new Bundle();
        AlertDialog resultDialog = (AlertDialog) dialog.onCreateDialog(savedInstanceState);
        resultDialog.show();

        RecyclerView rvSummary = (RecyclerView) resultDialog.findViewById(R.id.rvSummary);
        assertNotNull(rvSummary);
        assertNotNull(rvSummary.getAdapter());
        assertNotNull(rvSummary.getLayoutManager());
        assertTrue(rvSummary.getLayoutManager() instanceof GridLayoutManager);
        assertTrue(rvSummary.getAdapter().getItemCount() > 0);
        resultDialog.dismiss();
    }

    @Test
    public void doesSetHeaderSpanSize_TestMethod_SetupRvDrills() throws Exception {
        Bundle savedInstanceState = new Bundle();
        AlertDialog resultDialog = (AlertDialog) dialog.onCreateDialog(savedInstanceState);
        resultDialog.show();

        RecyclerView rvSummary = (RecyclerView) resultDialog.findViewById(R.id.rvSummary);
        GridLayoutManager gridLayoutManager = (GridLayoutManager) rvSummary.getLayoutManager();
        assertEquals(2, gridLayoutManager.getSpanSizeLookup().getSpanSize(0));
        resultDialog.dismiss();
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
        dialog = null;
    }

}