package com.armandgray.taap.db;

import android.net.Uri;

import org.junit.Test;

import static com.armandgray.taap.db.DatabaseContentProvider.AUTHORITY;
import static com.armandgray.taap.db.DatabaseContentProvider.BASE_PATH_DRILLS;
import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_DRILLS;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class DatabaseContentProviderTest {

    @Test
    public void hasContentUri_Drills() {
        assertNotNull(CONTENT_URI_DRILLS);
        assertEquals(Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_DRILLS), CONTENT_URI_DRILLS);
    }

}