package com.armandgray.shared.location;

import android.content.Context;

import com.armandgray.shared.application.TAAPAppComponent;
import com.google.android.gms.common.GoogleApiAvailability;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Observable;

public class LocationManagerImpl implements LocationManager {

    @Inject
    Context context;

    @Inject
    GoogleApiAvailability googleApiAvailability;

    public LocationManagerImpl() {
    }

    @Override
    public void inject(TAAPAppComponent appComponent) {
        LocationManager.Component component = appComponent.locationBuilder()
                .locationManager(this)
                .managerModule(new LocationManager.ManagerModule())
                .build();

        component.inject(this);
    }

    @Override
    public Observable<Availability> getAvailability() {
        return Observable.just(Availability.getAvailability(googleApiAvailability
                .isGooglePlayServicesAvailable(context)));
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }
}
