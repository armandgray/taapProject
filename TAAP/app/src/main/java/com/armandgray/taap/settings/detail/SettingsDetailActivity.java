package com.armandgray.taap.settings.detail;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.armandgray.taap.R;

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
            setHomeAsUpIndicatorColor();
        }
    }

    private void setSupportActionBarAsToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.tvTitle))
                .setText(getIntent().getStringExtra(SELECTED_ITEM));
        setSupportActionBar(toolbar);
    }

    private void setHomeAsUpIndicatorColor() {
        @SuppressWarnings("deprecation") final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        //noinspection deprecation
        upArrow.setColorFilter(getResources().getColor(R.color.colorDarkGray), PorterDuff.Mode.SRC_ATOP);
        if (getSupportActionBar() == null) { return; }
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private void setTvContentsText() {
        TextView tvContents = (TextView) findViewById(R.id.tvContents);
        switch (getIntent().getStringExtra(SELECTED_ITEM)) {
            case TERMS_AND_CONDITIONS:
                tvContents.setText(R.string.none);
                break;
            case SOFTWARE_LICENSES:
                tvContents.setText(R.string.none);
                break;
        }
    }

}
