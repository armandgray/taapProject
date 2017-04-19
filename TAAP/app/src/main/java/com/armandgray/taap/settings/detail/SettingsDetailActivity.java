package com.armandgray.taap.settings.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.armandgray.taap.R;

import static com.armandgray.taap.settings.SettingsActivityController.SELECTED_ITEM;

public class SettingsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.tvTitle))
                .setText(getIntent().getStringExtra(SELECTED_ITEM));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

}
