package com.armandgray.taap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.armandgray.shared.helpers.StringHelper;
import com.armandgray.shared.location.LocationManager;
import com.armandgray.shared.viewModel.LocationViewModel;
import com.armandgray.taap.R;
import com.armandgray.taap.application.WearDelegateActivity;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjection;

public class LocationActivity extends WearDelegateActivity {

    private static final int PLACE_PICKER_REQUEST = 990;

    @Inject
    LocationViewModel locationViewModel;

    private GeoDataClient geoDataClient;
    private PlaceDetectionClient placeDetectionClient;

    private TextView textAvailability;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Dagger Injection
        AndroidInjection.inject(this);

        // Super
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_location);
        super.onSetupContent();

//        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//
//        try {
//            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
//        } catch (GooglePlayServicesRepairableException
//                | GooglePlayServicesNotAvailableException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void assignGlobalFields() {
        super.assignGlobalFields();

        geoDataClient = Places.getGeoDataClient(this);
        placeDetectionClient = Places.getPlaceDetectionClient(this);

        textAvailability = findViewById(R.id.text_availability);
        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    public void setupVisualElements() {
        super.setupVisualElements();
    }

    @Override
    public void setupEventListeners() {
        super.setupEventListeners();
    }

    @Override
    public void setupViewModel() {
        super.setupViewModel();

        locationViewModel.getAvailability().observe(this, this::onAvailabilityChange);
    }

    private void onAvailabilityChange(LocationManager.Availability availability) {
        progressBar.setVisibility(View.INVISIBLE);
        textAvailability.setText(StringHelper.toSpacedUpperCamel(availability.toString()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Module
    public static class ActivityModule
            extends WearDelegateActivity.NavigationModule<LocationActivity> {

        @Provides
        @NonNull
        LocationViewModel provideViewModel(LocationActivity activity) {
            return ViewModelProviders.of(activity).get(LocationViewModel.class);
        }
    }
}
