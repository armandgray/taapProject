package com.armandgray.taap.log;

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
public class LogActivityResTest {

    private ActivityController<LogActivity> activityController;
    private LogActivity activity;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(LogActivity.class);
        activity = activityController.create().visible().get();
    }

    @Test
    public void hasLayout_DetailContainer() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_log, null);
        assertNotNull(container.findViewById(R.id.detailContainer));
    }

    @Test
    public void hasLayout_TotalSessionTime_DetailContainer() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_log, null);
        LinearLayout detailContainer = (LinearLayout) container.findViewById(R.id.detailContainer);
        assertNotNull(detailContainer.findViewById(R.id.layoutTotalSessionTime));
    }

    @Test
    public void hasLayout_TotalActiveTime_DetailContainer() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_log, null);
        LinearLayout detailContainer = (LinearLayout) container.findViewById(R.id.detailContainer);
        assertNotNull(detailContainer.findViewById(R.id.layoutTotalActiveTime));
    }

    @Test
    public void hasLayout_TotalRestTime_DetailContainer() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_log, null);
        LinearLayout detailContainer = (LinearLayout) container.findViewById(R.id.detailContainer);
        assertNotNull(detailContainer.findViewById(R.id.layoutTotalRestTime));
    }

    @Test
    public void hasLayout_ExercisesCompleted_DetailContainer() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_log, null);
        LinearLayout detailContainer = (LinearLayout) container.findViewById(R.id.detailContainer);
        assertNotNull(detailContainer.findViewById(R.id.layoutExercisesCompleted));
    }

    @Test
    public void hasLayout_RepsCompleted_DetailContainer() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_log, null);
        LinearLayout detailContainer = (LinearLayout) container.findViewById(R.id.detailContainer);
        assertNotNull(detailContainer.findViewById(R.id.layoutRepsCompleted));
    }

    @Test
    public void hasLayout_RecordsContainer() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_log, null);
        assertNotNull(container.findViewById(R.id.recordsContainer));
    }

    @Test
    public void hasView_TvDate_RecordsContainer() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_log, null);
        LinearLayout recordsContainer = (LinearLayout) container.findViewById(R.id.recordsContainer);
        assertNotNull(recordsContainer.findViewById(R.id.tvDate));
    }

    @Test
    public void hasLayout_TvGoalsMet_RecordsContainer() throws Exception {
        LinearLayout container = (LinearLayout) View.inflate(activity, R.layout.content_log, null);
        LinearLayout recordsContainer = (LinearLayout) container.findViewById(R.id.recordsContainer);
        LinearLayout goalsMetContainer = (LinearLayout) recordsContainer
                .findViewById(R.id.goalsMetContainer);
        assertNotNull(goalsMetContainer);
        assertNotNull(recordsContainer.findViewById(R.id.headerGoalsMet));
        assertNotNull(recordsContainer.findViewById(R.id.tvGoalsMet));
        assertNotNull(recordsContainer.findViewById(R.id.ivGoalsMet));
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
    }

}