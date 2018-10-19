package com.armandgray.taap.application;

import com.armandgray.shared.application.TAAPAppModule;
import com.armandgray.shared.db.DatabaseManager;

import dagger.Module;

@Module(subcomponents = {
        DatabaseManager.Component.class
})
class AppModule extends TAAPAppModule {

}
