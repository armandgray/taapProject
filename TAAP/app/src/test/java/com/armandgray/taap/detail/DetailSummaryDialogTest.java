package com.armandgray.taap.detail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
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

import static junit.framework.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DetailSummaryDialogTest {

    private ActivityController<DrillDetailActivity> activityController;
    private DrillDetailActivity activity;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(DrillDetailActivity.class);
        activity = activityController.create().visible().get();
    }

    @Test
    public void hasTemporaryConstructorWithActivity() {
        assertNotNull(new DetailSummaryDialog(activity));
    }

    @Test
    public void canCreateDetailSummaryDialog_TestOnCreateDialog() {
        DetailSummaryDialog dialog = new DetailSummaryDialog(activity);
        Bundle savedInstanceState = new Bundle();
        assertNotNull(dialog.onCreateDialog(savedInstanceState));
    }

    @Test
    public void existsLayout_DetailSummaryDialogLayout() {
        LinearLayout detailSummaryDialogLayout = (LinearLayout) View
                .inflate(activity, R.layout.detail_summary_dialog_layout, null);
        assertNotNull(detailSummaryDialogLayout);
    }

    @Test
    public void doesHaveCustomView_TestOnCreateDialog() {
        DetailSummaryDialog dialog = new DetailSummaryDialog(activity);
        Bundle savedInstanceState = new Bundle();
        AlertDialog resultDialog = (AlertDialog) dialog.onCreateDialog(savedInstanceState);
        ShadowAlertDialog shadowDialog = shadowOf(resultDialog);

        assertNotNull(resultDialog);
        assertNotNull(shadowDialog);
        assertNotNull(shadowDialog.getView());
        assertNotNull(shadowDialog.getView().findViewById(R.id.detailSummaryDialogContainer));
    }

    @Test
    public void doesHavePositiveContinueButton_TestOnCreateDialog() {
        DetailSummaryDialog dialog = new DetailSummaryDialog(activity);
        Bundle savedInstanceState = new Bundle();
        AlertDialog resultDialog = (AlertDialog) dialog.onCreateDialog(savedInstanceState);
        assertNotNull(resultDialog.getButton(DialogInterface.BUTTON_POSITIVE));
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
    }

}