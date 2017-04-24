package com.armandgray.taap.settings;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;
import com.armandgray.taap.detail.DrillDetailActivity;

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

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DrillDetailResTest {

    private ActivityController<DrillDetailActivity> activityController;
    private DrillDetailActivity activity;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(DrillDetailActivity.class);
        activity = activityController.create().visible().get();
    }

    @Test
    public void hasLayout_AboutContainer() throws Exception {
        assertNotNull(View.inflate(activity, R.layout.content_settings, null));
    }

    @Test
    public void hasView_AboutContainer_FirstTopBorder() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_settings, null);
        assertNotNull(container.findViewById(R.id.firstTopBorder));
    }

    @Test
    public void hasView_AboutContainer_TvRateThisApp() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_settings, null);
        assertNotNull(container.findViewById(R.id.tvRateThisApp));
    }

    @Test
    public void hasDrawable_AboutTextBackground() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_settings, null);
        TextView textView = (TextView) container.findViewById(R.id.tvRateThisApp);
        assertEquals(activity.getResources().getDrawable(R.drawable.about_item_background), textView.getBackground());
    }

    @Test
    public void hasView_IvImage() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_drill_detail, null);
        assertNotNull(container.findViewById(R.id.ivImage));
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
    }

}