package com.armandgray.taap.detail.dialogs;

import android.app.AlertDialog;
import android.database.Cursor;
import android.net.Uri;
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
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static com.armandgray.taap.db.DatabaseContentProvider.ALL_TABLE_COLUMNS;
import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_ALL;
import static com.armandgray.taap.db.DatabaseContentProvider.insertDrillToDatabase;
import static com.armandgray.taap.db.DatabaseContentProvider.insertLogToDatabase;
import static com.armandgray.taap.db.DatabaseContentProviderTest.TEST_SESSION_LOG;
import static com.armandgray.taap.db.DatabaseContentProviderTest.assertCursorDataEqualsLogWithAllTableColumns;
import static com.armandgray.taap.detail.dialogs.DetailSummaryDialog.DIALOG;
import static com.armandgray.taap.utils.CursorDataHelper.addAllLogsData;
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
        insertDrillToDatabase(TEST_SESSION_LOG.getDrill(), RuntimeEnvironment.application);
        insertLogToDatabase(TEST_SESSION_LOG, RuntimeEnvironment.application);

        int drillId = TEST_SESSION_LOG.getDrill().getDrillId();
        String[] selectionArgs = {String.valueOf(drillId)};
        Uri uri = Uri.parse(CONTENT_URI_ALL + "/" + drillId);
        Cursor cursor = RuntimeEnvironment.application.getContentResolver()
                .query(uri, ALL_TABLE_COLUMNS, null, selectionArgs, null);

        List<SessionLog> listAllLogs = new ArrayList<>();
        addAllLogsData(cursor, listAllLogs);
        dialog.helper.sessionLog = new SessionLog.Builder()
                .successRate(1.00)
                .create();

        double avg = 0.0;
        for (SessionLog log : listAllLogs) { avg += log.getSuccessRate(); }
        avg += dialog.helper.sessionLog.getSuccessRate();
        avg /= listAllLogs.size() + 1;
        avg = Math.floor(avg * 100) / 100;

        assertCursorDataEqualsLogWithAllTableColumns(cursor, TEST_SESSION_LOG);
        assertNotNull(dialog.helper.sessionLog);
        assertNotNull(cursor);
        assertEquals(1, cursor.getCount());
        assertEquals(1, listAllLogs.size());
        assertEquals(TEST_SESSION_LOG.getSuccessRate(), listAllLogs.get(0).getSuccessRate());
        assertEquals(1.00, dialog.helper.sessionLog.getSuccessRate());
        assertEquals(avg, dialog.helper.sessionLog.getSuccessRecord());
        cursor.close();
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