package com.armandgray.taap.log;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

public class LogActivity extends AppCompatActivity {

    public static final String SESSION_LOG = "SESSION_LOG";

    @VisibleForTesting LogActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        controller = new LogActivityController(this);
    }

}
