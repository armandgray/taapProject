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
    public void hasView_IvImage() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_drill_detail, null);
        assertNotNull(container.findViewById(R.id.ivImage));
    }

    @Test
    public void hasLayout_DetailContainer() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_drill_detail, null);
        assertNotNull(container.findViewById(R.id.detailContainer));
    }

    @Test
    public void hasView_RvCurrentLog() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_drill_detail, null);
        LinearLayout detailContainer = container.findViewById(R.id.detailContainer);
        assertNotNull(detailContainer.findViewById(R.id.rvCurrentLog));
    }

    @Test
    public void hasView_RvLastLog() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_drill_detail, null);
        LinearLayout detailContainer = container.findViewById(R.id.detailContainer);
        assertNotNull(detailContainer.findViewById(R.id.rvLastLog));
    }

    @Test
    public void hasView_TvGoal() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_drill_detail, null);
        LinearLayout detailContainer = container.findViewById(R.id.detailContainer);
        assertNotNull(detailContainer.findViewById(R.id.tvGoal));
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
    }

}