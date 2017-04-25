package com.armandgray.taap.detail;

import android.os.Bundle;

import com.armandgray.taap.BuildConfig;

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
public class DetailSummaryDialogTest {

    private ActivityController<DrillDetailActivity> activityController;
    private DrillDetailActivity activity;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(DrillDetailActivity.class);
        activity = activityController.create().visible().get();
    }

    @Test
    public void hasTemporaryConstructorWithActivity() {
        assertNotNull(new DetailSummaryDialog(activity));
    }

    @Test
    public void canCreateDetailSummaryDialog_TestOnCreateDialog() {
        DetailSummaryDialog dialog = new DetailSummaryDialog(activity);
        Bundle savedInstanceState = new Bundle();
        assertNotNull(dialog.onCreateDialog(savedInstanceState));
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
    }

}