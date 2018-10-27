package com.armandgray.shared.location;

import com.armandgray.shared.application.TAAPAppComponent;
import com.armandgray.shared.helpers.StringHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

import javax.inject.Scope;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import dagger.BindsInstance;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import io.reactivex.Observable;

/**
 * @see LocationManagerImpl
 */
public interface LocationManager
        extends TAAPAppComponent.InjectableSubComponent<TAAPAppComponent> {

    String TAG = StringHelper.toLogTag(LocationManager.class.getSimpleName());

    Observable<Availability> getAvailability();

    enum Availability {
        ERROR(-1),
        FEATURE_DISABLED(-1),
        MISSING_PERMISSION(-1),
        MISSING_GPS(-1),
        ENABLED(-1),

        // Connection Result
        SUCCESS(ConnectionResult.SUCCESS),
        SERVICE_MISSING(ConnectionResult.SERVICE_MISSING),
        SERVICE_UPDATING(ConnectionResult.SERVICE_UPDATING),
        SERVICE_VERSION_UPDATE_REQUIRED(ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED),
        SERVICE_DISABLED(ConnectionResult.SERVICE_DISABLED),
        SERVICE_INVALID(ConnectionResult.SERVICE_INVALID);

        @VisibleForTesting
        final int code;

        Availability(int code) {
            this.code = code;
        }

        @NonNull
        public static Availability getAvailability(int code) {
            return Arrays.stream(Availability.values())
                    .filter(availability -> equals(availability, code))
                    .findFirst()
                    .orElse(ERROR);
        }

        private static boolean equals(Availability availability, int code) {
            return availability != null && availability.code == code;
        }
    }

    @LocationScope
    @Subcomponent(modules = ManagerModule.class)
    interface Component {

        @Subcomponent.Builder
        interface Builder {

            @BindsInstance
            Component.Builder locationManager(LocationManager manager);

            @BindsInstance
            Component.Builder managerModule(ManagerModule module);

            Component build();
        }

        void inject(LocationManagerImpl manager);
    }

    @Scope
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface LocationScope {
    }

    @Module
    class ManagerModule {

        @Provides
        @LocationScope
        @NonNull
        GoogleApiAvailability provideGoogleApiAvailability() {
            return GoogleApiAvailability.getInstance();
        }
    }
}
