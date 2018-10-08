package com.armandgray.taap.application;

import android.app.Application;
import android.content.Context;

import com.armandgray.shared.db.DrillDatabase;
import com.google.gson.Gson;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

import static com.armandgray.shared.db.DrillDatabase.DATABASE_NAME;

@Module
class AppModule {

    /** Android APIs */

    @Provides
    @Singleton
    @NonNull
    Context provideContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    @NonNull
    DrillDatabase provideDrillDatabase(Context context) {
        return Room.databaseBuilder(context, DrillDatabase.class, DATABASE_NAME).build();
    }

    /** Util Libs */

    @Provides
    @Singleton
    @NonNull
    Gson provideGson() {
        return new Gson();
    }
}
