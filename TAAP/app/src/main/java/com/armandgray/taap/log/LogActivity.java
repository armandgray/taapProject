package com.armandgray.taap.log;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LogActivity extends AppCompatActivity {

    public static final String SESSION_LOG = "SESSION_LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new LogActivityController(this);
    }

}
