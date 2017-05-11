package com.armandgray.taap.detail.dialogs;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;
import com.armandgray.taap.detail.DrillDetailActivity;
import com.armandgray.taap.models.SessionLog;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static com.armandgray.taap.detail.dialogs.DetailSummaryDialog.DIALOG;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SummaryDialogHelperTest {

    private static final String DIALOG_CLASS_NAME = "com.armandgray.taap.detail.dialogs.DetailSummaryDialog";
    private ActivityController<DrillDetailActivity> activityController;
    private DrillDetailActivity activity;
    private DetailSummaryDialog dialog;
    private SummaryDialogHelper helper;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(DrillDetailActivity.class);
        activity = activityController.create().start().resume().visible().get();
        dialog = DetailSummaryDialog.newInstance(new SessionLog.Builder().create());
        dialog.show(activity.getSupportFragmentManager(), DIALOG);
        helper = dialog.helper;
    }

    @Test
    public void dialogInstanceOfDialogFragment_TestConstructor() throws Exception {
        assertEquals(DIALOG_CLASS_NAME, helper.dialog.getClass().getName());
    }

    @Test
    public void existsView_RvSummary_DetailSummaryDialogLayout() {
        LinearLayout detailSummaryDialogLayout = (LinearLayout) View
                .inflate(activity, R.layout.detail_summary_dialog_layout, null);
        assertNotNull(detailSummaryDialogLayout.findViewById(R.id.rvSummary));
    }

    @Test
    public void doesSetSessionLogSuccessRecord() throws Exception {
//        assertNotNull(dialog.helper.sessionLog);
//        assertNotNull(cursor);
//        assertEquals(1, cursor.getCount());
//
//        assertEquals(0.0, dialog.helper.sessionLog.getSuccessRecord());
//        cursor.close();
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
    public void doesSetHeaderSpanSize_TestMethod_SetupRvSummary() throws Exception {
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
        helper = null;
    }

}