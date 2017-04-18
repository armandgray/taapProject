package com.armandgray.taap.settings;

import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    public void hasLayout_AboutContainer() throws Exception {
        assertNotNull(View.inflate(activity, R.layout.content_settings, null));
    }

    @Test
    public void hasView_AboutContainer_TvRateThisApp() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_settings, null);
        assertNotNull(container.findViewById(R.id.tvRateThisApp));
    }

    @Test
    public void hasView_AboutContainer_TvSeeMore() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_settings, null);
        assertNotNull(container.findViewById(R.id.tvSeeMore));
    }

    @Test
    public void hasDrawable_AboutTextBackground() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_settings, null);
        TextView textView = (TextView) container.findViewById(R.id.tvRateThisApp);
        assertEquals(activity.getResources().getDrawable(R.drawable.about_item_background), textView.getBackground());
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