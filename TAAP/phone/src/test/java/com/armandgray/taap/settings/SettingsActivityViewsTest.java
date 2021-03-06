package com.armandgray.taap.settings;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;
import com.armandgray.taap.settings.detail.SettingsDetailActivity;

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
import org.robolectric.shadows.ShadowAlertDialog;

import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_DELETE_ALL_DATA;
import static com.armandgray.taap.detail.dialogs.DetailSummaryDialog.DIALOG;
import static com.armandgray.taap.settings.SettingsActivityController.ARMANDGRAY_COM;
import static com.armandgray.taap.settings.SettingsActivityController.GOOGLE_PLAY_STORE_TAAP;
import static com.armandgray.taap.settings.SettingsActivityController.SELECTED_ITEM;
import static com.armandgray.taap.settings.SettingsActivityController.SOFTWARE_LICENSES;
import static com.armandgray.taap.settings.SettingsActivityController.TERMS_AND_CONDITIONS;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class SettingsActivityViewsTest {

    private ActivityController<SettingsActivity> activityController;
    private SettingsActivity activity;
    private SettingsActivityViews views;

    @Before
    public void setUp() {
        activityController = Robolectric.buildActivity(SettingsActivity.class);
        activity = activityController.create().visible().get();
        views = activity.controller.views;
    }

    @Test @Ignore
    public void activityInstanceOfMainActivity_TestConstructor() throws Exception {
        assertEquals("settings.SettingsActivity", views.activity.getLocalClassName());
    }

    @Test @Ignore
    public void doesSetContentView_MethodTest_SetupActivityInitialState() throws Exception {
        assertEquals(R.id.activitySettingsLayout, shadowOf(activity).getContentView().getId());
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
        assertEquals("Settings", tvTitle.getText());
    }

    @Test @Ignore
    public void doesSetCustomToolbarUpArrow_MethodTest_SetupActivityInitialState() throws Exception {
        // TODO add test
    }

    @Test @Ignore
    public void doesSetupRateThisAppClickListener_MethodTest_SetupActivityInitialState() throws Exception {
        TextView tvRateThisApp = (TextView) activity.findViewById(R.id.tvRateThisApp);
        tvRateThisApp.performClick();
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_STORE_TAAP));
        assertEquals(expectedIntent.toString(),
                shadowOf(activity).getNextStartedActivity().toString());
    }

    @Test @Ignore
    public void doesSetupSeeMoreClickListener_MethodTest_SetupActivityInitialState() throws Exception {
        TextView tvSeeMore = (TextView) activity.findViewById(R.id.tvSeeMore);
        tvSeeMore.performClick();
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ARMANDGRAY_COM));
        assertEquals(expectedIntent.toString(),
                shadowOf(activity).getNextStartedActivity().toString());
    }

    @Test @Ignore
    public void canShowSummaryDialogSetupOnClearDataClick_MethodTest_SetupActivityInitialState() throws Exception {
        activityController.start().resume();
        TextView tvClearData = (TextView) activity.findViewById(R.id.tvClearData);
        tvClearData.performClick();

        ShadowAlertDialog resultDialog = shadowOf(RuntimeEnvironment.application)
                .getLatestAlertDialog();
        ConfirmClearDataDialog expectedDialog = new ConfirmClearDataDialog();
        expectedDialog.show(activity.getSupportFragmentManager(), DIALOG);
        ShadowAlertDialog shadowExpected = shadowOf(RuntimeEnvironment.application)
                .getLatestAlertDialog();
        assertNotNull(resultDialog);
        assertEquals(shadowExpected.getView(), resultDialog.getView());
    }

    @Test @Ignore
    public void doesSetupTermsAndConditionsClickListener_MethodTest_SetupActivityInitialState() throws Exception {
        TextView tvTermsConditions = (TextView) activity.findViewById(R.id.tvTermsConditions);
        tvTermsConditions.performClick();
        Intent expectedIntent = (new Intent(activity, SettingsDetailActivity.class))
                .putExtra(SELECTED_ITEM, TERMS_AND_CONDITIONS);
        Intent actualIntent = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.toString(), actualIntent.toString());
        assertEquals(TERMS_AND_CONDITIONS, actualIntent.getStringExtra(SELECTED_ITEM));
    }

    @Test @Ignore
    public void doesSetupSoftwareLicensesClickListener_MethodTest_SetupActivityInitialState() throws Exception {
        TextView tvSoftwareLicenses = (TextView) activity.findViewById(R.id.tvSoftwareLicenses);
        tvSoftwareLicenses.performClick();
        Intent expectedIntent = (new Intent(activity, SettingsDetailActivity.class))
                .putExtra(SELECTED_ITEM, SOFTWARE_LICENSES);
        Intent actualIntent = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.toString(), actualIntent.toString());
        assertEquals(SOFTWARE_LICENSES, actualIntent.getStringExtra(SELECTED_ITEM));
    }

    @After
    public void tearDown() {
        activity.getContentResolver().delete(CONTENT_URI_DELETE_ALL_DATA, null, null);
        activity.finish();
        activityController.pause().stop().destroy();
        activity = null;
        views = null;
    }

}