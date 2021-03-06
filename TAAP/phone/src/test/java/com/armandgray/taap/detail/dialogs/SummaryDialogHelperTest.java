package com.armandgray.taap.detail.dialogs;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;
import com.armandgray.taap.detail.DrillDetailActivity;
import com.armandgray.taap.models.SessionLog;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_DELETE_ALL_DATA;
import static com.armandgray.taap.detail.dialogs.DetailSummaryDialog.DIALOG;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class SummaryDialogHelperTest {

    private static final String DIALOG_CLASS_NAME = "com.armandgray.taap.detail.dialogs.DetailSummaryDialog";
    private ActivityController<DrillDetailActivity> activityController;
    private DrillDetailActivity activity;
    private DetailSummaryDialog dialog;
    private SummaryDialogHelper helper;

    @Before
    public void setUp() {
        activityController = Robolectric.buildActivity(DrillDetailActivity.class);
        activity = activityController.create().start().resume().visible().get();
        dialog = DetailSummaryDialog.newInstance(new SessionLog.Builder().create());
        dialog.show(activity.getSupportFragmentManager(), DIALOG);
        helper = dialog.helper;
    }

    @Test @Ignore
    public void dialogInstanceOfDialogFragment_TestConstructor() throws Exception {
        assertEquals(DIALOG_CLASS_NAME, helper.dialog.getClass().getName());
    }

    @Test @Ignore
    public void existsView_RvSummary_DetailSummaryDialogLayout() {
        LinearLayout detailSummaryDialogLayout = (LinearLayout) View
                .inflate(activity, R.layout.detail_summary_dialog_layout, null);
        assertNotNull(detailSummaryDialogLayout.findViewById(R.id.rvSummary));
    }

    @Test @Ignore
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

    @Test @Ignore
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
        activity.getContentResolver().delete(CONTENT_URI_DELETE_ALL_DATA, null, null);
        activity.finish();
        activityController.pause().stop().destroy();
        activity = null;
        dialog = null;
        helper = null;
    }

}