package com.armandgray.taap.log;

import android.content.Intent;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;
import com.armandgray.taap.models.Drill;
import com.armandgray.taap.models.SessionLog;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.util.Date;

import static com.armandgray.taap.log.LogActivity.SESSION_LOG;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LogActivityControllerTest {

    private static final String W_ALL = "wAll";
    private static final long TIME_IN_MILLIS = 1494179392802L;
    private static final Drill TEST_DRILL = new Drill(
            "5 Spots Shooting (Mid-Range)",
            R.drawable.ic_account_multiple_outline_white_48dp,
            Drill.SHOOTING_ARRAY);

    private static final SessionLog TEST_SESSION_LOG = new SessionLog.Builder()
            .sessionLength(new Date(TIME_IN_MILLIS))
            .sessionGoal("")
            .activeWork(new Date(TIME_IN_MILLIS + 555555))
            .restTime(new Date(TIME_IN_MILLIS + 111111))
            .setsCompleted(4)
            .repsCompleted(3)
            .successRate(0.23)
            .successRecord(0.55)
            .drill(TEST_DRILL)
            .create();

    private ActivityController<LogActivity> activityController;
    private LogActivity activity;
    private LogActivityController controller;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(LogActivity.class);
        Intent intent = new Intent(RuntimeEnvironment.application, LogActivity.class);
        intent.putExtra(SESSION_LOG, TEST_SESSION_LOG);
        activityController.newIntent(intent);
        activity = activityController.create().visible().get();
        controller = activity.controller;
    }

    @Test
    public void activityInstanceOfAppCompatActivity_TestConstructor() throws Exception {
        assertEquals("log.LogActivity", controller.activity.getLocalClassName());
    }

    @Test
    public void doesCreateViewsHandler_TestConstructor() throws Exception {
        assertNotNull(controller.views);
    }

    @Test
    public void doesGetSessionLogFromIntent() throws Exception {
        SessionLog expectedLog = activity.getIntent().getParcelableExtra(SESSION_LOG);

        assertNotNull(controller.sessionLog);
        assertEquals(expectedLog, controller.sessionLog);
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
        controller = null;
    }

}