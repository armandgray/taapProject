package com.armandgray.taap.detail.dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;
import com.armandgray.taap.detail.DrillDetailActivity;
import com.armandgray.taap.log.LogActivity;
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

import java.util.ArrayList;

import static com.armandgray.taap.detail.dialogs.DetailSummaryDialog.ALL_LOGS;
import static com.armandgray.taap.detail.dialogs.DetailSummaryDialog.DIALOG;
import static com.armandgray.taap.log.LogActivity.SESSION_LOG;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
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
    public void createsSummaryDialogControllerController_TestOnCreate() throws Exception {
        assertNotNull(dialog.helper);
        assertNotNull(dialog.helper.dialog);
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
    public void canCreateDetailSummaryDialog_NewInstanceMethod_TelescopeLogsList() {
        SessionLog sessionLog = new SessionLog.Builder().create();
        ArrayList<SessionLog> logs = new ArrayList<>();
        logs.add(sessionLog);
        DetailSummaryDialog dialog = DetailSummaryDialog.newInstance(sessionLog, logs);
        assertNotNull(dialog);
        assertNotNull(dialog.getArguments());
        assertNotNull(SessionLog.class.cast(dialog.getArguments().getParcelable(SESSION_LOG)));
        assertNotNull(logs.getClass().cast(dialog.getArguments().getParcelableArrayList(ALL_LOGS)));
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
    public void canClickPositiveContinueButtonToStartLogActivity_TestOnCreateDialog() {
        Bundle savedInstanceState = new Bundle();
        AlertDialog resultDialog = (AlertDialog) dialog.onCreateDialog(savedInstanceState);
        resultDialog.show();

        Button btnContinue = resultDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        btnContinue.performClick();
        Intent expectedIntent = new Intent(activity, LogActivity.class);
        expectedIntent.putExtra(SESSION_LOG, new SessionLog.Builder().create());
        assertEquals(expectedIntent.toString(),
                shadowOf(activity).getNextStartedActivity().toString());
        resultDialog.dismiss();
    }

    @Test
    public void canClickNegativeCancelButtonToDismissDialog_TestOnCreateDialog() {
        Bundle savedInstanceState = new Bundle();
        AlertDialog resultDialog = (AlertDialog) dialog.onCreateDialog(savedInstanceState);
        resultDialog.show();

        Button btnCancel = resultDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        btnCancel.performClick();
        assertFalse(resultDialog.isShowing());
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
        dialog = null;
    }

}