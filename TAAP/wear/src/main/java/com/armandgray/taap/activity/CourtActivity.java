package com.armandgray.taap.activity;

import android.os.Bundle;

import com.armandgray.taap.R;
import com.armandgray.taap.navigation.WearNavigationActivity;
import com.otaliastudios.zoom.ZoomImageView;

import dagger.Module;
import dagger.android.AndroidInjection;

public class CourtActivity extends WearNavigationActivity {

    private ZoomImageView zoomImageCourt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Dagger Injection
        AndroidInjection.inject(this);

        // Super
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_court);
        super.onSetupContent();
    }

    @Override
    public void assignGlobalFields() {
        super.assignGlobalFields();

        zoomImageCourt = findViewById(R.id.zoom_image_court);
    }

    @Override
    public void setupVisualElements() {
        super.setupVisualElements();

        zoomImageCourt.setImageDrawable(
                getDrawable(R.drawable.ncaa_basketball_full_court_hardwood));
    }

    @Override
    public void setupEventListeners() {
        super.setupEventListeners();
    }

    @Override
    public void setupViewModel() {
        super.setupViewModel();
    }

    @Module
    public static class ActivityModule
            extends WearNavigationActivity.NavigationModule<CourtActivity> {
    }
}
