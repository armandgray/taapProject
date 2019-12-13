package com.armandgray.shared.db;

import android.content.Context;
import android.util.Log;

import com.armandgray.shared.application.TAAPAppComponent;
import com.armandgray.shared.helpers.StringHelper;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Setting;
import com.armandgray.shared.rx.SchedulerProvider;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import dagger.BindsInstance;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

import static com.armandgray.shared.db.GlobalDatabase.DATABASE_NAME;
import static java.util.Objects.requireNonNull;

/**
 * @see DatabaseManagerImpl
 */
public interface DatabaseManager
        extends TAAPAppComponent.InjectableSubComponent<TAAPAppComponent> {

    final class Wrapper {

        private static Completable databaseReady = Completable.complete();

        private static DatabaseManager.Component databaseComponent;

        public static Completable getDatabaseReady() {
            return databaseReady;
        }

        static void setDatabaseComponent(@NonNull DatabaseManager.Component component) {
            databaseComponent = component;
        }

        static DatabaseManager.Component databaseComponent() {
            return databaseComponent;
        }
    }

    String TAG = StringHelper.toLogTag(DatabaseManager.class.getSimpleName());

    SharedPreferencesDao getSharedPreferencesDao();

    DrillDao getDrillDao();

    PerformanceDao getPerformanceDao();

    SettingsDao getSettingsDao();

    @DatabaseScope
    @Subcomponent(modules = ManagerModule.class)
    interface Component {

        @Subcomponent.Builder
        interface Builder {

            @BindsInstance
            Builder databaseManager(DatabaseManager manager);

            @BindsInstance
            Builder managerModule(ManagerModule module);

            DatabaseManager.Component build();
        }

        void inject(DatabaseManagerImpl manager);

        void inject(SharedPreferencesDaoImpl impl);
    }

    @Scope
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface DatabaseScope {
    }

    @Module
    class ManagerModule {

        private GlobalDatabase globalDatabase;

        @Provides
        @DatabaseScope
        @NonNull
        SharedPreferencesDao provideSharedPreferencesDao() {
            SharedPreferencesDaoImpl sharedPreferencesDao = new SharedPreferencesDaoImpl();
            Wrapper.databaseComponent().inject(sharedPreferencesDao);
            return sharedPreferencesDao;
        }

        @Provides
        @DatabaseScope
        @NonNull
        GlobalDatabase provideDatabase(Context context, SchedulerProvider schedulers) {
            globalDatabase = Room.databaseBuilder(context, GlobalDatabase.class, DATABASE_NAME)
                    .addCallback(onDatabaseLifecycleChanged(schedulers))
                    .build();

            // Hack DB open by forcing an initial access.
            requireNonNull(globalDatabase.drillDao()
                    .all()
                    .subscribeOn(Schedulers.io())
                    .subscribe(drills -> Log.d(TAG, drills.toString())));

            return globalDatabase;
        }

        private RoomDatabase.Callback onDatabaseLifecycleChanged(
                SchedulerProvider schedulers) {
            return new RoomDatabase.Callback() {

                /**
                 * Method is only called the first time the database is created. Not called on
                 * subsequent launches/ .build() calls unless the underlying database is deleted.
                 */
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);

                    Wrapper.databaseReady = Wrapper.databaseReady
                            .andThen(globalDatabase.drillDao()
                                    .insert(Drill.Defaults.getDefaults().toArray(new Drill[0]))
                                    .subscribeOn(schedulers.io())
                                    .ignoreElement());
                    Wrapper.databaseReady = Wrapper.databaseReady
                            .andThen(globalDatabase.settingsDao()
                                    .insert(Setting.Defaults.getDefaults().toArray(new Setting[0]))
                                    .subscribeOn(schedulers.io())
                                    .ignoreElement());
                }
            };
        }
    }
}
