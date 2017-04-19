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
import static com.armandgray.taap.utils.StringHelper.getFormattedHeaderTextString;

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
                String[] headers = {"Evox Images", "LibphoneNumber"};
                String[] text = new String[2];
                text[0] = "Evox Images (C) 2014.\n\nhttp://www.evoximages.com";
                text[1] = "Evox Images (C) 2014.\n\nhttp://www.evoximages.com";
                tvContents.setText(getFormattedHeaderTextString(headers, text));
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
