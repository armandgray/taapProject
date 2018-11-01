package com.armandgray.shared.viewModel;

import android.util.Log;

import com.armandgray.shared.application.TAAPRepository;
import com.armandgray.shared.db.DatabaseManager;
import com.armandgray.shared.model.Setting;
import com.armandgray.shared.model.UXPreference;
import com.armandgray.shared.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.ReplaySubject;

@Singleton
class PreferencesRepository extends TAAPRepository {

    @VisibleForTesting
    @NonNull
    final BehaviorSubject<UXPreference> activePreferenceSubject = BehaviorSubject.create();

    @VisibleForTesting
    @NonNull
    final BehaviorSubject<UXPreference.Value> activeValueSubject = BehaviorSubject.create();

    @VisibleForTesting
    @NonNull
    final ReplaySubject<UXPreference> preferenceUpdateSubject = ReplaySubject.create();

    private final DatabaseManager databaseManager;
    private final SchedulerProvider schedulers;

    @Inject
    PreferencesRepository(DatabaseManager databaseManager, SchedulerProvider schedulers) {
        this.databaseManager = databaseManager;
        this.schedulers = schedulers;

        this.databaseManager.stateSubject
                .switchMap(toSettingsList())
                .compose(schedulers.asyncTask())
                .subscribe(onSettingsRetrieved());
    }

    private Function<DatabaseManager.State, ObservableSource<List<Setting>>> toSettingsList() {
        return state -> state == DatabaseManager.State.READY
                ? databaseManager.getSettingsDao().all().toObservable()
                : Observable.just(new ArrayList<Setting>());
    }

    private RepositoryObserver<List<Setting>> onSettingsRetrieved() {
        return new RepositoryObserver<List<Setting>>() {

            @Override
            public void onNext(List<Setting> list) {
                if (list.size() == 0) {
                    Log.d(TAG, "Settings Population: Retrieved Empty List (Check State)");
                    return;
                }

                updateSettingsSubscribers(list);
            }
        };
    }

    @VisibleForTesting
    void updateSettingsSubscribers(List<Setting> list) {
        list.stream().map(Setting::getPreference).forEach(preferenceUpdateSubject::onNext);
    }

    @NonNull
    Observable<UXPreference> getActivePreferenceObservable() {
        return activePreferenceSubject;
    }

    void setActivePreference(UXPreference preference) {
        this.activePreferenceSubject.onNext(preference);
    }

    @NonNull
    Observable<UXPreference.Value> getActiveValueObservable() {
        return activeValueSubject;
    }

    void setActiveValue(UXPreference.Value value) {
        this.activeValueSubject.onNext(value);
    }

    @NonNull
    Observable<UXPreference> getPreferenceUpdateObservable() {
        return preferenceUpdateSubject;
    }

    @NonNull
    Observable<List<Setting>> getSettingsObservable() {
        return databaseManager.getSettingsDao().all().observeOn(schedulers.ui()).toObservable();
    }

    void onPreferenceUpdated() {
        UXPreference preference = activePreferenceSubject.getValue();
        if (preference == null) {
            return;
        }

        preferenceUpdateSubject.onNext(preference);
        storeSetting(preference);
    }

    void onPreferenceTriggered(@NonNull UXPreference.Value value) {
        if (activePreferenceSubject.getValue() == null) {
            return;
        }

        UXPreference preference = activePreferenceSubject.getValue();
        switch (value.getItem()) {
            case RESET:
                onResetPreference(preference);
                break;
        }

        preferenceUpdateSubject.onNext(preference);
    }

    private void onResetPreference(@NonNull UXPreference preference) {
        if (preference.getCategory().equals(UXPreference.Category.DATA)) {
            databaseManager.getPerformanceDao().deleteAll();
            return;
        }

        preference.getValues().forEach(pref -> {
            int defaultValue = pref.getItem().getDefault();
            pref.setValue(defaultValue);
        });

        storeSetting(preference);
    }

    private void storeSetting(@NonNull UXPreference preference) {
        if (!preference.getCategory().isDrillCategory()) {
            updateDatabaseSetting(databaseManager.getSettingsDao().findSetting(preference));
        }
    }

    private void updateDatabaseSetting(Setting setting) {
        databaseManager.getSettingsDao().update(setting).subscribe(
                new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.d(TAG, "Setting Update Success: " + setting);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Setting Update Failed: " + setting);
                    }
                });
    }
}
