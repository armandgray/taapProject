package com.armandgray.taap.log;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.view.View;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_DELETE_ALL_DATA;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class LogActivityTest {

    private ActivityController<LogActivity> activityController;
    private LogActivity activity;

    @Before
    public void setUp() {

        if (activity == null) {
            activityController = Robolectric.buildActivity(LogActivity.class);
            activity = activityController.create().visible().get();
        }
    }

    @Test @Ignore
    @SuppressWarnings("all")
    public void completesActivitySetup_TestOnCreate() throws Exception {
        View decorView = activity.findViewById(android.R.id.content).getRootView();
        CoordinatorLayout root = (CoordinatorLayout) decorView.findViewById(R.id.activityLogLayout);
        assertNotNull(activity);
        assertEquals(R.id.activityLogLayout, root.getId());
        assertNotNull(activity.views);
        assertTrue(activity.views instanceof LogActivityViews);
        assertNotNull(activity.controller);
        assertTrue(activity.controller instanceof LogActivityController);
    }

    @After
    public void tearDown() {

        activity.getContentResolver().delete(CONTENT_URI_DELETE_ALL_DATA, null, null);
        activity.finish();
        activityController.pause().stop().destroy();
        activityController = null;
        activity = null;
    }

}