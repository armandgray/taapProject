package com.armandgray.taap.settings;

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
import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class SettingsActivityTest {

    private ActivityController<SettingsActivity> activityController;
    private SettingsActivity activity;

    @Before
    public void setUp() {
        if (activity == null) {
            activityController = Robolectric.buildActivity(SettingsActivity.class);
            activity = activityController.create().visible().get();
        }
    }

    @Test @Ignore
    public void createsSettingsActivityController_TestOnCreate() throws Exception {
        assertNotNull(activity.controller);
        assertNotNull(activity.controller.activity);
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