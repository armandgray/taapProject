package com.armandgray.taap.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    SettingsActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new SettingsActivityController(this);
    }

}
