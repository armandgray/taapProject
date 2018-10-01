package com.armandgray.taap.splash;

import android.content.Intent;
import android.database.Cursor;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.db.DrillsTable;
import com.armandgray.taap.main.MainActivity;
import com.armandgray.taap.models.Drill;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_DELETE_ALL_DATA;
import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_DRILLS;
import static com.armandgray.taap.db.DrillsDataHelper.getDrillsList;
import static com.armandgray.taap.utils.StringHelper.getStringAsArray;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class SplashActivityTest {

    private ActivityController<SplashActivity> activityController;
    private SplashActivity activity;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        activityController = Robolectric.buildActivity(SplashActivity.class);
        activity = activityController.create().visible().get();
    }

    @Test @Ignore
    public void doesStartMainActivity() throws Exception {
        Intent expectedIntent = new Intent(activity, MainActivity.class);
        assertEquals(expectedIntent.toString(),
                shadowOf(activity).getNextStartedActivity().toString());
    }

    @Test @Ignore
    public void doesInsertAllDrillsIntoDatabase() throws Exception {
        Cursor cursor = RuntimeEnvironment.application.getContentResolver()
                .query(CONTENT_URI_DRILLS, DrillsTable.ALL_DRILL_COLUMNS,
                        null, null, null);

        int i = 0;
        assertNotNull(cursor);
        assertEquals(getDrillsList().size(), cursor.getCount());
        assertEquals(DrillsTable.ALL_DRILL_COLUMNS.length, cursor.getColumnCount());

        while (cursor.moveToNext()) {
            Drill drill = getDrillsList().get(i);
            assertEquals(drill.getTitle(),
                    cursor.getString(cursor.getColumnIndex(DrillsTable.COLUMN_TITLE)));
            assertEquals(drill.getImageId(),
                    cursor.getInt(cursor.getColumnIndex(DrillsTable.COLUMN_IMAGE_ID)));
            assertThat(drill.getCategory(), is(getStringAsArray(cursor.getString(
                    cursor.getColumnIndex(DrillsTable.COLUMN_CATEGORY)))));
            i++;
        }
        cursor.close();
    }

    @Test @Ignore
    public void doesCheckIfDrillsExistInDatabase() throws Exception {
        Cursor cursor = RuntimeEnvironment.application.getContentResolver()
                .query(CONTENT_URI_DRILLS, DrillsTable.ALL_DRILL_COLUMNS,
                        null, null, null);

        int i = 0;
        assertNotNull(cursor);
        assertEquals(getDrillsList().size(), cursor.getCount());
        assertEquals(DrillsTable.ALL_DRILL_COLUMNS.length, cursor.getColumnCount());

        while (cursor.moveToNext()) {
            Drill drill = getDrillsList().get(i);
            assertEquals(drill.getTitle(),
                    cursor.getString(cursor.getColumnIndex(DrillsTable.COLUMN_TITLE)));
            assertEquals(drill.getImageId(),
                    cursor.getInt(cursor.getColumnIndex(DrillsTable.COLUMN_IMAGE_ID)));
            assertThat(drill.getCategory(), is(getStringAsArray(cursor.getString(
                    cursor.getColumnIndex(DrillsTable.COLUMN_CATEGORY)))));
            i++;
        }
        cursor.close();

        activity.insertAllDrillsToDatabase();

        cursor = RuntimeEnvironment.application.getContentResolver()
                .query(CONTENT_URI_DRILLS, DrillsTable.ALL_DRILL_COLUMNS,
                        null, null, null);

        i = 0;
        assertNotNull(cursor);
        assertEquals(getDrillsList().size(), cursor.getCount());
        assertEquals(DrillsTable.ALL_DRILL_COLUMNS.length, cursor.getColumnCount());

        while (cursor.moveToNext()) {
            Drill drill = getDrillsList().get(i);
            assertEquals(drill.getTitle(),
                    cursor.getString(cursor.getColumnIndex(DrillsTable.COLUMN_TITLE)));
            assertEquals(drill.getImageId(),
                    cursor.getInt(cursor.getColumnIndex(DrillsTable.COLUMN_IMAGE_ID)));
            assertThat(drill.getCategory(), is(getStringAsArray(cursor.getString(
                    cursor.getColumnIndex(DrillsTable.COLUMN_CATEGORY)))));
            i++;
        }
        cursor.close();
    }

    @Test @Ignore
    public void doesRetrieveHttpData() throws Exception {

    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        activity.getContentResolver().delete(CONTENT_URI_DELETE_ALL_DATA, null, null);
        activity.finish();
        activityController.pause().stop().destroy();
        activity = null;
    }

}