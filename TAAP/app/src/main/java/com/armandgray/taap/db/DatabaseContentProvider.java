package com.armandgray.taap.db;

import android.net.Uri;
import android.support.annotation.VisibleForTesting;

public class DatabaseContentProvider {

    public static final Uri CONTENT_URI_DRILLS;
    @VisibleForTesting static final String AUTHORITY = "com.armandgray.taap.db.provider";
    @VisibleForTesting static final String BASE_PATH_DRILLS = "drills";

    static { CONTENT_URI_DRILLS = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_DRILLS); }
}
