package com.armandgray.shared.application;

import com.armandgray.shared.db.DatabaseManager;
import com.armandgray.shared.db.DatabaseManagerImpl;
import com.armandgray.shared.viewModel.DrillViewModel;
import com.armandgray.shared.viewModel.LogsViewModel;
import com.armandgray.shared.viewModel.PerformanceViewModel;
import com.armandgray.shared.viewModel.PreferencesViewModel;
import com.armandgray.shared.viewModel.SettingsViewModel;

public interface TAAPAppComponent {

    DatabaseManager.Component.Builder databaseBuilder();

    void inject(PerformanceViewModel viewModel);

    void inject(DrillViewModel viewModel);

    void inject(SettingsViewModel viewModel);

    void inject(LogsViewModel viewModel);

    void inject(PreferencesViewModel viewModel);

    interface InjectableSubComponent<P> {

        void inject(P parentComponent);
    }
}
