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

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
    }

}