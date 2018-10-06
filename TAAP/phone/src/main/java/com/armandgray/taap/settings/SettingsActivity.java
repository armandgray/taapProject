package com.armandgray.taap.settings;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity
        implements ConfirmClearDataDialog.ClearDataListener {

    SettingsActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new SettingsActivityController(this);
    }

    @Override
    public void onPositiveClearDataClick() {
        controller.onPositiveClearDataClick();
    }
}
