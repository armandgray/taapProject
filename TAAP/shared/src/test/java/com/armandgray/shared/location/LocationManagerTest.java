package com.armandgray.shared.location;

import com.armandgray.shared.application.TAAPAppComponent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.annotation.Config;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import io.reactivex.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsInAnyOrder;

@PrepareForTest({LocationManager.class, GoogleApiAvailability.class})
@Config(manifest = Config.NONE)
@RunWith(PowerMockRunner.class)
public class LocationManagerTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    GoogleApiAvailability mockGoogleApiAvailability;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(GoogleApiAvailability.class);
        PowerMockito.when(GoogleApiAvailability.getInstance())
                .thenReturn(mockGoogleApiAvailability);
    }

    @Test
    public void testLocationManager() {
        LocationManager testManager = new LocationManager() {
            @Override
            public Observable<Availability> getAvailability() {
                return null;
            }

            @Override
            public void inject(TAAPAppComponent parentComponent) {

            }
        };

        Assert.assertThat(testManager, is(notNullValue()));
    }

    @Test
    public void testLocationManager_DefinesInterfaceTag() {
        Assert.assertThat(LocationManager.TAG, is(notNullValue()));
    }

    /**
     * Inner Class - State
     */

    @Test
    public void testAvailabilityEnum_Instances() {
        LocationManager.Availability[] values = LocationManager.Availability.values();
        Assert.assertThat(values.length, is(11));
        Assert.assertThat(Arrays.asList(values), containsInAnyOrder(
                LocationManager.Availability.ERROR,
                LocationManager.Availability.FEATURE_DISABLED,
                LocationManager.Availability.MISSING_PERMISSION,
                LocationManager.Availability.MISSING_GPS,
                LocationManager.Availability.ENABLED,

                LocationManager.Availability.SUCCESS,
                LocationManager.Availability.SERVICE_MISSING,
                LocationManager.Availability.SERVICE_UPDATING,
                LocationManager.Availability.SERVICE_VERSION_UPDATE_REQUIRED,
                LocationManager.Availability.SERVICE_DISABLED,
                LocationManager.Availability.SERVICE_INVALID));
    }

    @Test
    public void testAvailabilityEnum_GetAvailability() {
        Assert.assertThat(LocationManager.Availability.getAvailability(ConnectionResult.SUCCESS),
                is(LocationManager.Availability.SUCCESS));
    }

    @Test
    public void testAvailabilityEnum_ConnectionResultValues() {
        Assert.assertThat(LocationManager.Availability.SUCCESS.code, is(0));
        Assert.assertThat(LocationManager.Availability.SERVICE_MISSING.code, is(1));
        Assert.assertThat(LocationManager.Availability.SERVICE_UPDATING.code, is(18));
        Assert.assertThat(LocationManager.Availability.SERVICE_VERSION_UPDATE_REQUIRED.code, is(2));
        Assert.assertThat(LocationManager.Availability.SERVICE_DISABLED.code, is(3));
        Assert.assertThat(LocationManager.Availability.SERVICE_INVALID.code, is(9));
    }

    /**
     * Inner Class - Component
     */

    @SuppressWarnings("Convert2Lambda")
    @Test
    public void testLocationManager_Component() {
        LocationManager.Component component = new LocationManager.Component() {
            @Override
            public void inject(LocationManagerImpl manager) {
            }
        };

        LocationManager.Component.Builder builder = new LocationManager.Component.Builder() {
            @Override
            public LocationManager.Component.Builder locationManager(LocationManager manager) {
                return null;
            }

            @Override
            public LocationManager.Component.Builder managerModule(
                    LocationManager.ManagerModule module) {
                return null;
            }

            @Override
            public LocationManager.Component build() {
                return null;
            }
        };

        Assert.assertThat(component, is(notNullValue()));
        Assert.assertThat(builder, is(notNullValue()));
    }

    @Test
    public void testLocationManager_Scope() {
        LocationManager.LocationScope scope = new LocationManager.LocationScope() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }
        };

        Assert.assertThat(scope, is(notNullValue()));
    }

    /**
     * Inner Class - ManagerModule
     */

    @Test
    public void testManagerModule_ProvideGoogleApiAvailability() {
        Assert.assertThat(new LocationManager.ManagerModule().provideGoogleApiAvailability(),
                is(mockGoogleApiAvailability));
    }

    @After
    public void tearDown() {
    }
}
