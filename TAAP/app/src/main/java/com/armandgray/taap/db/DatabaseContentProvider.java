package com.armandgray.taap.db;

import android.net.Uri;
import android.support.annotation.VisibleForTesting;

public class DatabaseContentProvider {

    @VisibleForTesting static final String AUTHORITY = "com.armandgray.seeme.db.notesprovider";
    @VisibleForTesting static final String BASE_PATH_DRILLS = "drills";
    public static final Uri CONTENT_URI_DRILLS = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_DRILLS);
}
