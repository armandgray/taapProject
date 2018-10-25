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
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class SettingsActivityControllerTest {

    private ActivityController<SettingsActivity> activityController;
    private SettingsActivity activity;
    private SettingsActivityController controller;

    @Before
    public void setUp() {
        activityController = Robolectric.buildActivity(SettingsActivity.class);
        activity = activityController.create().visible().get();
        controller = activity.controller;
    }

    @Test @Ignore
    public void activityInstanceOfAppCompatActivity_TestConstructor() throws Exception {
        assertEquals("settings.SettingsActivity", controller.activity.getLocalClassName());
    }

    @Test @Ignore
    public void doesCreateViewsHandler_TestConstructor() throws Exception {
        assertNotNull(controller.views);
        assertNotNull(controller.views.activity);
    }

    @After
    public void tearDown() {
        activity.getContentResolver().delete(CONTENT_URI_DELETE_ALL_DATA, null, null);
        activity.finish();
        activityController.pause().stop().destroy();
        activity = null;
        controller = null;
    }

}