package com.armandgray.taap.settings.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.armandgray.taap.R;

import static com.armandgray.taap.settings.SettingsActivityController.COPYRIGHT;
import static com.armandgray.taap.settings.SettingsActivityController.SELECTED_ITEM;
import static com.armandgray.taap.settings.SettingsActivityController.SOFTWARE_LICENSES;
import static com.armandgray.taap.settings.SettingsActivityController.TERMS_AND_CONDITIONS;

public class SettingsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_detail);
        setupToolbar();
        setTvContentsText();
    }

    private void setupToolbar() {
        setSupportActionBarAsToolbar();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setSupportActionBarAsToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.tvTitle))
                .setText(getIntent().getStringExtra(SELECTED_ITEM));
        setSupportActionBar(toolbar);
    }

    private void setTvContentsText() {
        TextView tvContents = (TextView) findViewById(R.id.tvContents);
        switch (getIntent().getStringExtra(SELECTED_ITEM)) {
            case COPYRIGHT:
                tvContents.setText(COPYRIGHT);
                break;
            case TERMS_AND_CONDITIONS:
                tvContents.setText(TERMS_AND_CONDITIONS);
                break;
            case SOFTWARE_LICENSES:
                tvContents.setText(SOFTWARE_LICENSES);
                break;
        }
    }

}
