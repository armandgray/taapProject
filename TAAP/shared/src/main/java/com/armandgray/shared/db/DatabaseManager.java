package com.armandgray.shared.db;

import android.content.Context;
import android.util.Log;

import com.armandgray.shared.application.TAAPAppComponent;
import com.armandgray.shared.model.Drill;
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
import io.reactivex.subjects.BehaviorSubject;

import static com.armandgray.shared.db.DrillDatabase.DATABASE_NAME;

public interface DatabaseManager
        extends TAAPAppComponent.InjectableSubComponent<TAAPAppComponent> {

    enum State {CREATED, OPEN, POPULATED}

    String TAG = "DATABASE_MANAGER";

    BehaviorSubject<State> stateSubject = BehaviorSubject.create();

    DrillDao getDrillDao();

    PerformanceDao getPerformanceDao();

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
    }

    @Scope
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface DatabaseScope {
    }

    @Module
    class ManagerModule {

        private static final int POPULATE_RETRY_TIMES = 3;

        private final CompositeDisposable disposables = new CompositeDisposable();
        private DrillDatabase drillDatabase;

        @Provides
        @DatabaseScope
        @NonNull
        DrillDatabase provideDatabase(Context context, SchedulerProvider schedulers) {
            drillDatabase = Room.databaseBuilder(context, DrillDatabase.class, DATABASE_NAME)
                    .addCallback(onDatabaseLifecycleChanged(schedulers))
                    .build();

            return drillDatabase;
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
                    populateDefaults(schedulers);
                }

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);

                    synchronized (stateSubject) {
                        if (!stateSubject.hasComplete() && stateSubject.getValue() == null) {
                            stateSubject.onNext(State.OPEN);
                            stateSubject.onComplete();
                        }
                    }
                }
            };
        }

        @SuppressWarnings("SimplifyStreamApiCallChains")
        private void populateDefaults(SchedulerProvider schedulers) {
            Single.just(Drill.Defaults.getDefaults())
                    .map(list -> list.stream().toArray(Drill[]::new))
                    .subscribeOn(schedulers.io())
                    .retry(POPULATE_RETRY_TIMES)
                    .subscribe(new SingleObserver<Drill[]>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposables.add(d);
                        }

                        @Override
                        public void onSuccess(Drill[] drills) {
                            drillDatabase.drillDao()
                                    .insert(drills)
                                    .doFinally(() -> {
                                        synchronized (stateSubject) {
                                            stateSubject.onNext(State.POPULATED);
                                            stateSubject.onComplete();
                                        }
                                    })
                                    .subscribe();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(DatabaseManager.TAG, String.format("Drill Population Failed: %s",
                                    e.getMessage()));
                        }
                    });
        }
    }
}
