package com.armandgray.taap.application;

import com.armandgray.taap.activity.ActiveDrillActivity;
import com.armandgray.taap.activity.CourtActivity;
import com.armandgray.taap.activity.DrillPickerActivity;

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
    @ContributesAndroidInjector(modules = DrillPickerActivity.ActivityModule.class)
    abstract DrillPickerActivity bindDrillPickerActivity();
}
