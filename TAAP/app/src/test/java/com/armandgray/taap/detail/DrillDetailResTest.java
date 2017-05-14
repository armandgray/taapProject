package com.armandgray.taap.detail;

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
        LinearLayout detailContainer = (LinearLayout) container.findViewById(R.id.detailContainer);
        assertNotNull(detailContainer.findViewById(R.id.rvCurrentLog));
    }

    @Test
    public void hasView_RvLastLog() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_drill_detail, null);
        LinearLayout detailContainer = (LinearLayout) container.findViewById(R.id.detailContainer);
        assertNotNull(detailContainer.findViewById(R.id.rvPreviousLogs));
    }

    @Test
    public void hasView_TvGoal() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_drill_detail, null);
        LinearLayout detailContainer = (LinearLayout) container.findViewById(R.id.detailContainer);
        assertNotNull(detailContainer.findViewById(R.id.tvGoal));
    }

    @Test
    public void hasLayout_SetsContainer() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_drill_detail, null);
        assertNotNull(container.findViewById(R.id.setsContainer));
    }

    @Test
    public void hasView_TvSets() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_drill_detail, null);
        LinearLayout setsContainer = (LinearLayout) container.findViewById(R.id.setsContainer);
        assertNotNull(setsContainer.findViewById(R.id.tvSets));
    }

    @Test
    public void hasView_NpSets() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_drill_detail, null);
        LinearLayout setsContainer = (LinearLayout) container.findViewById(R.id.setsContainer);
        assertNotNull(setsContainer.findViewById(R.id.npSets));
    }

    @Test
    public void hasView_TvReps() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_drill_detail, null);
        LinearLayout setsContainer = (LinearLayout) container.findViewById(R.id.setsContainer);
        assertNotNull(setsContainer.findViewById(R.id.tvReps));
    }

    @Test
    public void hasView_NpReps() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_drill_detail, null);
        LinearLayout setsContainer = (LinearLayout) container.findViewById(R.id.setsContainer);
        assertNotNull(setsContainer.findViewById(R.id.npReps));
    }

    @Test
    public void hasView_TvSuccesses() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_drill_detail, null);
        LinearLayout setsContainer = (LinearLayout) container.findViewById(R.id.setsContainer);
        assertNotNull(setsContainer.findViewById(R.id.tvSuccesses));
    }

    @Test
    public void hasView_NpSuccesses() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_drill_detail, null);
        LinearLayout setsContainer = (LinearLayout) container.findViewById(R.id.setsContainer);
        assertNotNull(setsContainer.findViewById(R.id.npSuccesses));
    }

    @Test
    public void hasView_BtnFinished() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_drill_detail, null);
        assertNotNull(container.findViewById(R.id.btnFinished));
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
    }

}