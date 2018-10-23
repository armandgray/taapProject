package com.armandgray.shared.viewModel;

import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.application.TAAPViewModel;

import javax.inject.Inject;

public class SettingsViewModel extends TAAPViewModel {

    @Inject
    DrillRepository repository;

    SettingsViewModel() {
        TAAPApplication.getAppComponent().inject(this);
    }
}
