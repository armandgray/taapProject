package com.armandgray.shared.viewModel;

import com.armandgray.shared.application.TAAPRepository;
import com.armandgray.shared.db.DatabaseManager;
import com.armandgray.shared.location.LocationManager;
import com.armandgray.shared.model.UXPreference;
import com.armandgray.shared.permission.DangerousPermission;
import com.armandgray.shared.permission.PermissionManager;
import com.armandgray.shared.rx.SchedulerProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

import static com.armandgray.shared.location.LocationManager.Availability;

@Singleton
class LocationRepository extends TAAPRepository {

    @Inject
    DatabaseManager databaseManager;

    @Inject
    SchedulerProvider schedulers;

    @Inject
    LocationManager locationManager;

    @Inject
    PermissionManager permissionManager;

    private Availability preferenceAvailability;

    @Inject
    LocationRepository(PreferencesRepository preferencesRepository) {
        disposables.add(preferencesRepository.getPreferenceUpdateObservable()
                .subscribe(this::preferenceConsumer));
    }

    private void preferenceConsumer(UXPreference preference) {
        if (preference.getCategory() != UXPreference.Category.LOCATION) {
            return;
        }

        preferenceAvailability = getPreferenceAvailability(preference);
    }

    private Availability getPreferenceAvailability(UXPreference preference) {
        if (!preference.isEnabled(UXPreference.Item.GYM_LOCATION)) {
            return Availability.FEATURE_DISABLED;

        } else {
            return Availability.ENABLED;
        }
    }

    Observable<Availability> getAvailability() {
        if (preferenceAvailability != Availability.ENABLED) {
            return Observable.just(preferenceAvailability);
        }

        return permissionManager.usePermission(DangerousPermission.LOCATION)
                .zipWith(locationManager.getAvailability(), zipPermissionOverApi());
    }

    private BiFunction<Boolean, Availability, Availability> zipPermissionOverApi() {
        return (permission, api) -> permission ? api : Availability.MISSING_PERMISSION;
    }
}
