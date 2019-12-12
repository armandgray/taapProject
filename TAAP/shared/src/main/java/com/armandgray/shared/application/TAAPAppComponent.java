package com.armandgray.shared.application;

import com.armandgray.shared.api.NetworkManager;
import com.armandgray.shared.db.DatabaseManager;
import com.armandgray.shared.location.LocationManager;
import com.armandgray.shared.permission.PermissionManager;
import com.armandgray.shared.permission.PermissionViewModel;
import com.armandgray.shared.sensors.GeneralSensorManager;
import com.armandgray.shared.viewModel.DrillViewModel;
import com.armandgray.shared.viewModel.LocationViewModel;
import com.armandgray.shared.viewModel.LogsViewModel;
import com.armandgray.shared.viewModel.PerformanceViewModel;
import com.armandgray.shared.viewModel.PreferencesViewModel;
import com.armandgray.shared.voice.VoiceManager;

public interface TAAPAppComponent {

    DatabaseManager.Component.Builder databaseBuilder();

    LocationManager.Component.Builder locationBuilder();

    PermissionManager.Component.Builder permissionBuilder();

    GeneralSensorManager.Component.Builder sensorBuilder();

    NetworkManager.Component.Builder networkBuilder();

    VoiceManager.Component.Builder voiceBuilder();

    void inject(PerformanceViewModel viewModel);

    void inject(DrillViewModel viewModel);

    void inject(LogsViewModel viewModel);

    void inject(PreferencesViewModel viewModel);

    void inject(LocationViewModel viewModel);

    void inject(PermissionViewModel viewModel);

    interface InjectableSubComponent<P> {

        void inject(P parentComponent);
    }
}
