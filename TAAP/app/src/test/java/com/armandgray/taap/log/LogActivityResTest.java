package com.armandgray.taap.log;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LogActivityResTest {

    private LinearLayout container;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        LayoutInflater inflater = LayoutInflater.from(RuntimeEnvironment.application);
        ScrollView layout = (ScrollView) inflater.inflate(R.layout.content_log, null);
        container = (LinearLayout) layout.findViewById(R.id.logContainer);
    }

    @Test
    public void hasLayout_DetailContainer() throws Exception {
        assertNotNull(container.findViewById(R.id.detailContainer));
    }

    @Test
    public void hasLayout_TotalSessionTime_DetailContainer() throws Exception {
        LinearLayout detailContainer = (LinearLayout) container.findViewById(R.id.detailContainer);
        assertNotNull(detailContainer.findViewById(R.id.layoutTotalSessionTime));
    }

    @Test
    public void hasLayout_TotalActiveTime_DetailContainer() throws Exception {
        LinearLayout detailContainer = (LinearLayout) container.findViewById(R.id.detailContainer);
        assertNotNull(detailContainer.findViewById(R.id.layoutTotalActiveTime));
    }

    @Test
    public void hasLayout_TotalRestTime_DetailContainer() throws Exception {
        LinearLayout detailContainer = (LinearLayout) container.findViewById(R.id.detailContainer);
        assertNotNull(detailContainer.findViewById(R.id.layoutTotalRestTime));
    }

    @Test
    public void hasLayout_ExercisesCompleted_DetailContainer() throws Exception {
        LinearLayout detailContainer = (LinearLayout) container.findViewById(R.id.detailContainer);
        assertNotNull(detailContainer.findViewById(R.id.layoutExercisesCompleted));
    }

    @Test
    public void hasLayout_RepsCompleted_DetailContainer() throws Exception {
        LinearLayout detailContainer = (LinearLayout) container.findViewById(R.id.detailContainer);
        assertNotNull(detailContainer.findViewById(R.id.layoutRepsCompleted));
    }

    @Test
    public void hasLayout_RecordsContainer() throws Exception {
        assertNotNull(container.findViewById(R.id.recordsContainer));
    }

    @Test
    public void hasView_TvDate_RecordsContainer() throws Exception {
        LinearLayout recordsContainer = (LinearLayout) container.findViewById(R.id.recordsContainer);
        assertNotNull(recordsContainer.findViewById(R.id.tvDate));
    }

    @Test
    public void hasLayout_TvGoalsMet_RecordsContainer() throws Exception {
        LinearLayout recordsContainer = (LinearLayout) container.findViewById(R.id.recordsContainer);
        LinearLayout goalsMetContainer = (LinearLayout) recordsContainer
                .findViewById(R.id.goalsMetContainer);
        assertNotNull(goalsMetContainer);
        assertNotNull(recordsContainer.findViewById(R.id.headerGoalsMet));
        assertNotNull(recordsContainer.findViewById(R.id.tvGoalsMet));
        assertNotNull(recordsContainer.findViewById(R.id.ivGoalsMet));
    }

    @Test
    public void hasLayout_Fundamentals_RecordsContainer() throws Exception {
        LinearLayout recordsContainer = (LinearLayout) container.findViewById(R.id.recordsContainer);
        assertNotNull(recordsContainer.findViewById(R.id.layoutFundamentals));
    }

    @Test
    public void hasLayout_Defense_RecordsContainer() throws Exception {
        LinearLayout recordsContainer = (LinearLayout) container.findViewById(R.id.recordsContainer);
        assertNotNull(recordsContainer.findViewById(R.id.layoutDefense));
    }

    @Test
    public void hasLayout_OffBallOffense_RecordsContainer() throws Exception {
        LinearLayout recordsContainer = (LinearLayout) container.findViewById(R.id.recordsContainer);
        assertNotNull(recordsContainer.findViewById(R.id.layoutOffense));
    }

    @Test
    public void hasLayout_Conditioning_RecordsContainer() throws Exception {
        LinearLayout recordsContainer = (LinearLayout) container.findViewById(R.id.recordsContainer);
        assertNotNull(recordsContainer.findViewById(R.id.layoutConditioning));
    }

    @Test
    public void hasLayout_Shooting_RecordsContainer() throws Exception {
        LinearLayout recordsContainer = (LinearLayout) container.findViewById(R.id.recordsContainer);
        assertNotNull(recordsContainer.findViewById(R.id.layoutShooting));
    }

    @Test
    public void hasLayout_BallHandling_RecordsContainer() throws Exception {
        LinearLayout recordsContainer = (LinearLayout) container.findViewById(R.id.recordsContainer);
        assertNotNull(recordsContainer.findViewById(R.id.layoutBallHandling));
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
    }

}