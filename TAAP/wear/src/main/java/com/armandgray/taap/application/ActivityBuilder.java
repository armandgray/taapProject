package com.armandgray.taap.application;

import com.armandgray.taap.activity.ActiveDrillActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = ActiveDrillActivity.ActivityModule.class)
    abstract ActiveDrillActivity bindActiveDrillActivity();
}
