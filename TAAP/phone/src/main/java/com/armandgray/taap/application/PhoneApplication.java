package com.armandgray.taap.application;

import android.app.Activity;

import com.armandgray.shared.application.TAAPApplication;

import dagger.android.AndroidInjector;

public class PhoneApplication extends TAAPApplication {
    
    @Override
    public AndroidInjector<Activity> activityInjector() {
        return null;
    }
}
