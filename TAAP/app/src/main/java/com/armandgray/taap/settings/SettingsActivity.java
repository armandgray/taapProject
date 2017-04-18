package com.armandgray.taap.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.armandgray.taap.R;

public class SettingsActivity extends AppCompatActivity {

    SettingsActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        controller = new SettingsActivityController(this);
    }
}
