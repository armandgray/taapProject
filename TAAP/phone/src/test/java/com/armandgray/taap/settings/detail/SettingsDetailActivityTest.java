package com.armandgray.taap.settings.detail;

import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;

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

import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_DELETE_ALL_DATA;
import static com.armandgray.taap.settings.SettingsActivityController.SELECTED_ITEM;
import static com.armandgray.taap.settings.SettingsActivityController.TERMS_AND_CONDITIONS;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class SettingsDetailActivityTest {

    private ActivityController<SettingsDetailActivity> activityController;
    private SettingsDetailActivity activity;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        Intent intent = new Intent(RuntimeEnvironment.application, SettingsDetailActivity.class);
        intent.putExtra(SELECTED_ITEM, TERMS_AND_CONDITIONS);
        activityController = Robolectric.buildActivity(SettingsDetailActivity.class).newIntent(intent);
        activity = activityController.create().visible().get();
    }

    @Test @Ignore
    public void hasView_TvContents() throws Exception {
        ScrollView container = (ScrollView) View.inflate(activity, R.layout.content_settings_detail, null);
        assertNotNull(container.findViewById(R.id.tvContents));
    }

    @Test @Ignore
    public void doesSetHomeAsUpEnabled() throws Exception {
        assertNotNull(activity.getSupportActionBar());
        final int displayOptions = activity.getSupportActionBar().getDisplayOptions();
        assertTrue((displayOptions & ActionBar.DISPLAY_SHOW_HOME) != 0);
        assertTrue((displayOptions & ActionBar.DISPLAY_HOME_AS_UP) != 0);
    }

    @Test @Ignore
    public void doesSetupHideToolbarTitle() throws Exception {
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
    public void doesSetCustomToolbarTitleText() throws Exception {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        assertEquals(TERMS_AND_CONDITIONS, tvTitle.getText());
    }

    @Test @Ignore
    public void doesSetCustomToolbarUpArrow_MethodTest_SetupActivityInitialState() throws Exception {
        // TODO add test
    }

    @Test @Ignore
    public void doesSetTvContentText() throws Exception {
        TextView tvContents = (TextView) activity.findViewById(R.id.tvContents);
        assertTrue(tvContents.getText() != "");
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activity.getContentResolver().delete(CONTENT_URI_DELETE_ALL_DATA, null, null);
        activity.finish();
        activityController.pause().stop().destroy();
        activity = null;
    }

}