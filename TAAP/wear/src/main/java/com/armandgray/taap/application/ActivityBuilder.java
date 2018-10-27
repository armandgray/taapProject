package com.armandgray.taap.application;

import com.armandgray.taap.activity.ActiveDrillActivity;
import com.armandgray.taap.activity.CourtActivity;
import com.armandgray.taap.activity.LogsActivity;
import com.armandgray.taap.activity.LocationActivity;
import com.armandgray.taap.activity.SettingsActivity;
import com.armandgray.taap.permission.PermissionRationaleDialog;
import com.armandgray.taap.ui.DrillPickerDialog;
import com.armandgray.taap.ui.PreferenceSeekBarDialog;
import com.armandgray.taap.ui.PreferenceToggleDialog;
import com.armandgray.taap.ui.PreferencesDialog;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBuilder {

    @SuppressWarnings("unused")
    @ContributesAndroidInjector(modules = ActiveDrillActivity.ActivityModule.class)
    abstract ActiveDrillActivity bindActiveDrillActivity();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector(modules = CourtActivity.ActivityModule.class)
    abstract CourtActivity bindCourtActivity();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector(modules = LocationActivity.ActivityModule.class)
    abstract LocationActivity bindLocationActivity();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector(modules = SettingsActivity.ActivityModule.class)
    abstract SettingsActivity bindSettingsActivity();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector(modules = LogsActivity.ActivityModule.class)
    abstract LogsActivity bindLogsActivity();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector(modules = PreferencesDialog.ActivityModule.class)
    abstract PreferencesDialog bindTargetsActivity();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector(modules = DrillPickerDialog.ActivityModule.class)
    abstract DrillPickerDialog bindDrillPickerActivity();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector(modules = PreferenceSeekBarDialog.ActivityModule.class)
    abstract PreferenceSeekBarDialog bindPreferenceSeekBarDialog();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector(modules = PreferenceToggleDialog.ActivityModule.class)
    abstract PreferenceToggleDialog bindPreferenceToggleDialog();

    @SuppressWarnings("unused")
    @ContributesAndroidInjector(modules = PermissionRationaleDialog.ActivityModule.class)
    abstract PermissionRationaleDialog bindPermissionRationaleDialog();
}
