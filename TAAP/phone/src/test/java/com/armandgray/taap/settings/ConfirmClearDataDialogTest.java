package com.armandgray.taap.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.widget.Button;

import com.armandgray.taap.R;
import com.armandgray.taap.db.DrillsTable;
import com.armandgray.taap.db.LogsTable;
import com.armandgray.taap.splash.SplashActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;

import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_DRILLS;
import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_LOGS;
import static com.armandgray.taap.db.DatabaseContentProvider.insertDrillToDatabase;
import static com.armandgray.taap.db.DatabaseContentProvider.insertLogToDatabase;
import static com.armandgray.taap.db.DatabaseContentProviderTest.TEST_SESSION_LOG;
import static com.armandgray.taap.detail.dialogs.DetailSummaryDialog.DIALOG;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ConfirmClearDataDialogTest {

    private ActivityController<SettingsActivity> activityController;
    private SettingsActivity activity;
    private ConfirmClearDataDialog dialog;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(SettingsActivity.class);
        activity = activityController.create().visible().get();
        dialog = new ConfirmClearDataDialog();
        dialog.show(activity.getSupportFragmentManager(), DIALOG);
    }

    @Test @Ignore
    public void canCreateConfirmClearDataDialog() {
        assertNotNull(new ConfirmClearDataDialog());
    }

    @Test @Ignore
    public void canCreateConfirmClearDataDialog_TestOnCreateDialog() {
        Bundle savedInstanceState = new Bundle();
        assertNotNull(dialog.onCreateDialog(savedInstanceState));
    }

    @Test @Ignore
    public void doesExtendDialogFragment() {
        DialogFragment dialogFragment = dialog;
        assertNotNull(dialogFragment);
    }

    @Test @Ignore
    public void doesContainConfirmationMessage() {
        activityController.start().resume().visible();
        ShadowAlertDialog shadowAlertDialog = shadowOf(RuntimeEnvironment.application)
                .getLatestAlertDialog();

        assertNotNull(shadowAlertDialog);
        assertNotNull(shadowAlertDialog.getMessage());
        assertEquals(activity.getString(R.string.clear_data_message),
                shadowAlertDialog.getMessage());
    }

    @Test @Ignore
    public void canClickPositiveButtonToClearData_TestOnCreateDialog() {
        insertDrillToDatabase(TEST_SESSION_LOG.getDrill(), RuntimeEnvironment.application);
        insertDrillToDatabase(TEST_SESSION_LOG.getDrill(), RuntimeEnvironment.application);
        insertDrillToDatabase(TEST_SESSION_LOG.getDrill(), RuntimeEnvironment.application);
        insertLogToDatabase(TEST_SESSION_LOG, RuntimeEnvironment.application);
        insertLogToDatabase(TEST_SESSION_LOG, RuntimeEnvironment.application);
        insertLogToDatabase(TEST_SESSION_LOG, RuntimeEnvironment.application);

        Bundle savedInstanceState = new Bundle();
        AlertDialog resultDialog = (AlertDialog) dialog.onCreateDialog(savedInstanceState);
        resultDialog.show();

        Button btnPositive = resultDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        btnPositive.performClick();
        resultDialog.dismiss();

        Cursor drillCursor = RuntimeEnvironment.application.getContentResolver()
                .query(CONTENT_URI_DRILLS, DrillsTable.ALL_DRILL_COLUMNS,
                        null, null, null);
        Cursor logCursor = RuntimeEnvironment.application.getContentResolver()
                .query(CONTENT_URI_LOGS, LogsTable.ALL_LOG_COLUMNS,
                        null, null, null);

        assertNotNull(drillCursor);
        assertNotNull(logCursor);
        assertEquals(0, drillCursor.getCount());
        assertEquals(0, logCursor.getCount());
        drillCursor.close();
        logCursor.close();
    }

    @Test @Ignore
    public void doesStartSplashActivity_OnPositiveButtonClick() throws Exception {
        Bundle savedInstanceState = new Bundle();
        AlertDialog resultDialog = (AlertDialog) dialog.onCreateDialog(savedInstanceState);
        resultDialog.show();

        Button btnPositive = resultDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        btnPositive.performClick();

        Intent expectedIntent = new Intent(activity, SplashActivity.class);
        ShadowActivity shadowActivity = shadowOf(activity);
        assertNotNull(shadowActivity);
        Intent nextStartedActivity = shadowActivity.getNextStartedActivity();
        assertNotNull(nextStartedActivity);
        assertEquals(expectedIntent.toString(), nextStartedActivity.toString());
    }

    @Test @Ignore
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