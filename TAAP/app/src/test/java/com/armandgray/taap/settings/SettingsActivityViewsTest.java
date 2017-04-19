package com.armandgray.taap.settings;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;
import com.armandgray.taap.settings.detail.SettingsDetailActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static com.armandgray.taap.settings.SettingsActivityController.ARMANDGRAY_COM;
import static com.armandgray.taap.settings.SettingsActivityController.COPYRIGHT;
import static com.armandgray.taap.settings.SettingsActivityController.GOOGLE_PLAY_STORE_TAAP;
import static com.armandgray.taap.settings.SettingsActivityController.SELECTED_ITEM;
import static com.armandgray.taap.settings.SettingsActivityController.SOFTWARE_LICENSES;
import static com.armandgray.taap.settings.SettingsActivityController.TERMS_AND_CONDITIONS;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SettingsActivityViewsTest {

    private ActivityController<SettingsActivity> activityController;
    private SettingsActivity activity;
    private Toolbar toolbar;
    private SettingsActivityViews views;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(SettingsActivity.class);
        activity = activityController.create().visible().get();
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        views = activity.controller.views;
    }

    @Test
    public void activityInstanceOfMainActivity_TestConstructor() throws Exception {
        assertEquals("settings.SettingsActivity", views.activity.getLocalClassName());
    }

    @Test
    public void doesSetContentView_MethodTest_SetupActivityInitialState() throws Exception {
        assertEquals(R.id.activitySettingsLayout, shadowOf(activity).getContentView().getId());
    }

    @Test
    public void doesSetHomeAsUpEnabled_MethodTest_SetupActivityInitialState() throws Exception {
        assertNotNull(activity.getSupportActionBar());
        final int displayOptions = activity.getSupportActionBar().getDisplayOptions();
        assertTrue((displayOptions & ActionBar.DISPLAY_SHOW_HOME) != 0);
        assertTrue((displayOptions & ActionBar.DISPLAY_HOME_AS_UP) != 0);
    }

    @Test
    public void doesSetupHideToolbarTitle_MethodTest_SetupActivityInitialState() throws Exception {
        ActionBar actionBar = activity.getSupportActionBar();
        assertNotNull(actionBar);
        final int displayOptions = activity.getSupportActionBar().getDisplayOptions();
        assertTrue((displayOptions & ActionBar.DISPLAY_SHOW_TITLE) == 0);
    }

    @Test
    public void hasCustomToolbarTitle() throws Exception {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        assertNotNull(tvTitle);
    }

    @Test
    public void doesSetCustomToolbarTitleText_MethodTest_SetupActivityInitialState() throws Exception {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        assertEquals(COPYRIGHT, tvTitle.getText());
    }

    @Test
    public void doesSetupRateThisAppClickListener_MethodTest_SetupActivityInitialState() throws Exception {
        TextView tvRateThisApp = (TextView) activity.findViewById(R.id.tvRateThisApp);
        tvRateThisApp.performClick();
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_STORE_TAAP));
        assertEquals(expectedIntent.toString(),
                shadowOf(activity).getNextStartedActivity().toString());
    }

    @Test
    public void doesSetupSeeMoreClickListener_MethodTest_SetupActivityInitialState() throws Exception {
        TextView tvSeeMore = (TextView) activity.findViewById(R.id.tvSeeMore);
        tvSeeMore.performClick();
        Intent expectedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ARMANDGRAY_COM));
        assertEquals(expectedIntent.toString(),
                shadowOf(activity).getNextStartedActivity().toString());
    }

    @Test
    public void doesSetupCopyrightClickListener_MethodTest_SetupActivityInitialState() throws Exception {
        TextView tvCopyright = (TextView) activity.findViewById(R.id.tvCopyright);
        tvCopyright.performClick();
        Intent expectedIntent = (new Intent(activity, SettingsDetailActivity.class))
                .putExtra(SELECTED_ITEM, COPYRIGHT);
        Intent actualIntent = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.toString(), actualIntent.toString());
        assertEquals(COPYRIGHT, actualIntent.getStringExtra(SELECTED_ITEM));
    }

    @Test
    public void doesSetupTermsAndConditionsClickListener_MethodTest_SetupActivityInitialState() throws Exception {
        TextView tvTermsConditions = (TextView) activity.findViewById(R.id.tvTermsConditions);
        tvTermsConditions.performClick();
        Intent expectedIntent = (new Intent(activity, SettingsDetailActivity.class))
                .putExtra(SELECTED_ITEM, TERMS_AND_CONDITIONS);
        Intent actualIntent = shadowOf(activity).getNextStartedActivity();
        assertEquals(expectedIntent.toString(), actualIntent.toString());
        assertEquals(TERMS_AND_CONDITIONS, actualIntent.getStringExtra(SELECTED_ITEM));
    }

    @Test
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
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
        toolbar = null;
        views = null;
    }

}