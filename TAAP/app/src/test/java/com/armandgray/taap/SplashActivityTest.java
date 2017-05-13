package com.armandgray.taap;

import android.content.Intent;
import android.database.Cursor;

import com.armandgray.taap.db.DrillsTable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_DRILLS;
import static com.armandgray.taap.db.DatabaseContentProviderTest.assertCursorDataEqualsDrill;
import static com.armandgray.taap.utils.DrillsHelper.getDrillsList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SplashActivityTest {

    private ActivityController<SplashActivity> activityController;
    private SplashActivity activity;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(SplashActivity.class);
        activity = activityController.create().visible().get();
    }

    @Test
    public void doesStartMainActivity() throws Exception {
        Intent expectedIntent = new Intent(activity, MainActivity.class);
        assertEquals(expectedIntent.toString(),
                shadowOf(activity).getNextStartedActivity().toString());
    }

    @Test
    public void doesInsertAllDrillsIntoDatabase() throws Exception {
        Cursor cursor = RuntimeEnvironment.application.getContentResolver()
                .query(CONTENT_URI_DRILLS, DrillsTable.ALL_DRILL_COLUMNS,
                        null, null, null);

        int i = 0;
        assertNotNull(cursor);
        assertEquals(getDrillsList().size(), cursor.getCount());
        while (cursor.moveToNext()) {
            assertCursorDataEqualsDrill(cursor, getDrillsList().get(i));
            i++;
        }
        cursor.close();
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activityController.pause().stop().destroy();
        activity = null;
    }

}