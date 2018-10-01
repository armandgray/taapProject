package com.armandgray.taap.db;

import android.content.Context;
import android.database.Cursor;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.models.SessionLog;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import static com.armandgray.taap.db.DatabaseContentProvider.ALL_TABLE_COLUMNS;
import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_ALL;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by armandgray on 12/27/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class LogsDataModelTest {

    private static Context context = RuntimeEnvironment.application;

    @Test @Ignore
    public void canRetrieveAllLogs() throws Exception {
        Cursor cursor = context.getContentResolver().query(CONTENT_URI_ALL, ALL_TABLE_COLUMNS,
                null, null, null);

        List<SessionLog> allLogs = LogsDataModel.getAllLogs(context);
        assertNotNull(allLogs);
        assertTrue(allLogs.size() == 0);
        LogsDataModel.retrieveAllLogs(context);
        allLogs = LogsDataModel.getAllLogs(context);
        assertNotNull(allLogs);
        assertNotNull(cursor);
        assertEquals(allLogs.size(), cursor.getCount());
    }

    @Test @Ignore
    public void canGetAllLogs() throws Exception {
        List<SessionLog> allLogs = LogsDataModel.getAllLogs(context);
        assertNotNull(allLogs);
    }

}