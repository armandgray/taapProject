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
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.subjects.BehaviorSubject;

import static com.armandgray.shared.db.GlobalDatabase.DATABASE_NAME;

/**
 * @see DatabaseManagerImpl
 */
public interface DatabaseManager
        extends TAAPAppComponent.InjectableSubComponent<TAAPAppComponent> {

    enum State {
        CREATED, POPULATING, READY;

        private static DatabaseManager.Component databaseComponent;

        static void setDatabaseComponent(@NonNull DatabaseManager.Component component) {
            databaseComponent = component;
        }

        static DatabaseManager.Component databaseComponent() {
            return databaseComponent;
        }
    }

    String TAG = StringHelper.toLogTag(DatabaseManager.class.getSimpleName());

    BehaviorSubject<State> stateSubject = BehaviorSubject.create();

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

        private static final int POPULATE_RETRY_TIMES = 3;

        private GlobalDatabase globalDatabase;

        @Provides
        @DatabaseScope
        @NonNull
        SharedPreferencesDao provideSharedPreferencesDao() {
            return new SharedPreferencesDaoImpl();
        }

        @Provides
        @DatabaseScope
        @NonNull
        GlobalDatabase provideDatabase(Context context, SchedulerProvider schedulers) {
            globalDatabase = Room.databaseBuilder(context, GlobalDatabase.class, DATABASE_NAME)
                    .addCallback(onDatabaseLifecycleChanged(schedulers))
                    .build();

            return globalDatabase;
        }

        private RoomDatabase.Callback onDatabaseLifecycleChanged(
                SchedulerProvider schedulers) {
            return new RoomDatabase.Callback() {

                /**
                 * Method is only called the first time the database is created. Not called on
                 * subsequent launches/ .build() calls unless the underlying database is deleted.
                 *
                 * @param db The Database
                 */
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);

                    stateSubject.onNext(State.CREATED);
                    populateDrillDefaults(schedulers);
                    populateSettingsDefaults(schedulers);
                }

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);

                    synchronized (stateSubject) {
                        if (!stateSubject.hasComplete() && stateSubject.getValue() == null) {
                            stateSubject.onNext(State.READY);
                            stateSubject.onComplete();
                        }
                    }
                }
            };
        }

        @SuppressWarnings("SimplifyStreamApiCallChains")
        private void populateDrillDefaults(SchedulerProvider schedulers) {
            Single.just(Drill.Defaults.getDefaults())
                    .map(list -> list.stream().toArray(Drill[]::new))
                    .subscribeOn(schedulers.io())
                    .retry(POPULATE_RETRY_TIMES)
                    .subscribe(new DatabasePopulationObserver<Drill[]>() {
                        @Override
                        public void onSuccess(Drill[] drills) {
                            globalDatabase.drillDao().insert(drills)
                                    .doFinally(onPopulated())
                                    .subscribe();
                        }
                    });
        }

        @SuppressWarnings("SimplifyStreamApiCallChains")
        private void populateSettingsDefaults(SchedulerProvider schedulers) {
            Single.just(Setting.Defaults.getDefaults())
                    .map(list -> list.stream().toArray(Setting[]::new))
                    .subscribeOn(schedulers.io())
                    .retry(POPULATE_RETRY_TIMES)
                    .subscribe(new DatabasePopulationObserver<Setting[]>() {
                        @Override
                        public void onSuccess(Setting[] settings) {
                            globalDatabase.settingsDao().insert(settings)
                                    .doFinally(onPopulated())
                                    .subscribe();
                        }
                    });
        }

        private Action onPopulated() {
            return () -> {
                synchronized (stateSubject) {
                    if (stateSubject.getValue() == State.POPULATING) {
                        stateSubject.onNext(State.READY);
                        stateSubject.onComplete();
                    } else {
                        stateSubject.onNext(State.POPULATING);
                    }
                }
            };
        }
    }

    abstract class DatabasePopulationObserver<T> implements SingleObserver<T> {

        private static final CompositeDisposable disposables = new CompositeDisposable();

        @Override
        public void onSubscribe(Disposable d) {
            disposables.add(d);
        }

        @Override
        public void onError(Throwable e) {
            Log.e(DatabaseManager.TAG, String.format("Population Failed: %s", e.getMessage()));
        }
    }
}
