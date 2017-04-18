package com.armandgray.taap.settings.detail;

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

import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SettingsDetailActivityTest {

    private ActivityController<SettingsDetailActivity> activityController;
    private SettingsDetailActivity activity;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(SettingsDetailActivity.class);
        activity = activityController.create().visible().get();
    }

    @Test
    public void hasView_TvContents() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_settings_detail, null);
        assertNotNull(container.findViewById(R.id.tvContents));
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
    }

}