package com.armandgray.shared.viewModel;

import android.util.Log;

import com.armandgray.shared.application.TAAPRepository;
import com.armandgray.shared.db.DatabaseManager;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;
import com.armandgray.shared.model.UXPreference;
import com.armandgray.shared.permission.DangerousPermission;
import com.armandgray.shared.permission.PermissionManager;
import com.armandgray.shared.rx.SchedulerProvider;
import com.armandgray.shared.sensors.GeneralSensorManager;
import com.armandgray.shared.sensors.LinearAccelerationAction;
import com.armandgray.shared.voice.VoiceEvent;
import com.armandgray.shared.voice.VoiceManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

@Singleton
class DrillRepository extends TAAPRepository {

    private static int singleSetTimeout = UXPreference.Item.TIMEOUT.getDefault(true);
    private static boolean enableAutoTracking = UXPreference.Item.AUTO.isDefaultEnabled();
    private static boolean enableCallOut = UXPreference.Item.CALL_OUT.isDefaultEnabled();
    private static boolean enableClap = UXPreference.Item.CLAP.isDefaultEnabled();
    private static int voiceTimeout = UXPreference.Item.VOICE_TIMEOUT.getDefault(true);

    @Inject
    GeneralSensorManager generalSensorManager;

    @Inject
    VoiceManager voiceManager;

    @Inject
    PermissionManager permissionManager;

    @VisibleForTesting
    final BehaviorSubject<List<Drill>> drillsSubject = BehaviorSubject.create();

    @VisibleForTesting
    final BehaviorSubject<Drill> activeDrillSubject = BehaviorSubject.create();

    @VisibleForTesting
    final BehaviorSubject<Performance> performanceSubject = BehaviorSubject.create();

    @VisibleForTesting
    final PublishSubject<Performance> completionSubject = PublishSubject.create();

    private final DatabaseManager databaseManager;
    private final SchedulerProvider schedulers;

    @Nullable
    private Disposable setTimeoutDisposable;

    @SuppressWarnings("ConstantConditions")
    @Inject
    DrillRepository(PreferencesRepository preferencesRepository,
                    DatabaseManager databaseManager, SchedulerProvider schedulers) {
        this.databaseManager = databaseManager;
        this.schedulers = schedulers;

        super.disposables.add(preferencesRepository.getPreferenceUpdateObservable()
                .subscribe(this::preferenceConsumer));

        DatabaseManager.Wrapper.getDatabaseReady()
                .andThen(databaseManager.getDrillDao().all())
                .toObservable()
                .compose(schedulers.asyncTask())
                .subscribe(onDrillsRetrieved());
    }

    private void preferenceConsumer(UXPreference preference) {
        switch (preference.getCategory()) {
            case WORKOUT:
                singleSetTimeout = preference.getValue(UXPreference.Item.TIMEOUT, true);
                enableAutoTracking = preference.isEnabled(UXPreference.Item.AUTO);
                return;

            case VOICE:
                enableCallOut = preference.isEnabled(UXPreference.Item.CALL_OUT);
                enableClap = preference.isEnabled(UXPreference.Item.CLAP);
                voiceTimeout = preference.getValue(UXPreference.Item.VOICE_TIMEOUT, true);

            default:
                onDrillPreferenceChanged(preference);
        }
    }

    private void onDrillPreferenceChanged(UXPreference preference) {
        if (!preference.getCategory().isDrillCategory() || activeDrillSubject.getValue() == null) {
            return;
        }

        Performance performance = performanceSubject.getValue();
        Drill drill = activeDrillSubject.getValue();
        Performance update = new Performance(drill);

        databaseManager.getDrillDao().update(drill).subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onSuccess(Integer integer) {
                Log.d(TAG, "Drill Update Success: " + drill);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Drill Update Failed: " + drill);
            }
        });

        if (performance != null) {
            update.setCount(performance.getCount());
            update.setTotal(performance.getTotal());
            update.setStartTime(performance.getStartTime());
        }

        setPerformanceValue(update);
    }

    private RepositoryObserver<List<Drill>> onDrillsRetrieved() {
        return new RepositoryObserver<List<Drill>>() {
            @Override
            public void onNext(List<Drill> list) {
                updateDrillSubscribers(list);
            }
        };
    }

    @VisibleForTesting
    void updateDrillSubscribers(List<Drill> list) {
        @SuppressWarnings("ConstantConditions")
        Drill drill = list.stream().filter(Drill.Defaults.getDefault()::equals).findFirst().get();
        drillsSubject.onNext(list);
        activeDrillSubject.onNext(drill);
        performanceSubject.onNext(new Performance(drill));
    }

    Observable<List<Drill>> getDrillsObservable() {
        return drillsSubject;
    }

    Observable<Drill> getActiveDrillObservable() {
        return activeDrillSubject;
    }

    Observable<Performance> getPerformanceObservable() {
        return performanceSubject;
    }

    Observable<Performance> getCompletionObservable() {
        return completionSubject;
    }

    Observable<LinearAccelerationAction> getAutoTrackingObservable() {
        // TODO Fix enabling race condition with preference update
        return enableAutoTracking
                ? generalSensorManager.getAutoTrackingObservable()
                : Observable.just(LinearAccelerationAction.NONE);
    }

    Observable<VoiceEvent> getVoiceEventObservable() {
        // Disable Non-Triggered Events while Auto Tracking
        return !enableAutoTracking
                ? getTriggeredVoiceEventObservable()
                : Observable.just(VoiceEvent.NONE);
    }

    Observable<VoiceEvent> getTriggeredVoiceEventObservable() {
        if (!enableCallOut && !enableClap) {
            return Observable.just(VoiceEvent.NONE);
        }

        DangerousPermission permission = DangerousPermission.MICROPHONE;
        Observable<Boolean> permissionObservable = permissionManager.usePermission(permission);

        if (enableClap && enableCallOut) {
            // TODO implement amb for first event
            return permissionObservable
                    .concatMap(concatPermissionOverVoiceEvent(voiceManager.getClapObservable()))
                    .compose(schedulers.asyncTask());
        } else if (enableCallOut) {
            return permissionObservable
                    .concatMap(concatPermissionOverVoiceEvent(voiceManager.getCallOutObservable()))
                    .compose(schedulers.asyncTask());
        } else {
            return permissionObservable
                    .concatMap(concatPermissionOverVoiceEvent(voiceManager.getClapObservable()))
                    .subscribeOn(schedulers.looper())
                    .observeOn(schedulers.ui());
        }
    }

    private ObservableTransformer<VoiceEvent, VoiceEvent> applyVoiceTimeout() {
        return observable -> observable
                .timeout(voiceTimeout, TimeUnit.MILLISECONDS)
                .onErrorReturnItem(VoiceEvent.TIMEOUT);
    }

    private Function<Boolean, ObservableSource<VoiceEvent>> concatPermissionOverVoiceEvent(
            Observable<VoiceEvent> observable) {
        return hasPermission -> {
            VoiceEvent missingPermission = VoiceEvent.MISSING_PERMISSION;
            return hasPermission ? observable : Observable.just(missingPermission);
        };
    }

    void addMake() {
        Performance performance = this.performanceSubject.getValue();
        if (performance == null) {
            return;
        }

        performance.raiseCount();
        performance.raiseTotal();

        updateSetTimeout();
        setPerformanceValue(performance);
    }

    void addMiss() {
        Performance performance = this.performanceSubject.getValue();
        if (performance == null) {
            return;
        }

        performance.raiseTotal();

        updateSetTimeout();
        setPerformanceValue(performance);
    }

    private void updateSetTimeout() {
        if (setTimeoutDisposable != null) {
            setTimeoutDisposable.dispose();
        }

        setTimeoutDisposable = Observable.timer(singleSetTimeout, TimeUnit.MILLISECONDS)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(timeout -> handleSetCompletion(performanceSubject.getValue()));
    }

    void clearPerformance() {
        Performance performance = this.performanceSubject.getValue();
        if (performance == null) {
            return;
        }

        performance.clear();
        setPerformanceValue(performance);
    }

    private void setPerformanceValue(Performance performance) {
        performanceSubject.onNext(performance);
        if (performance.getTotal() >= performance.getReps()) {
            handleSetCompletion(performance);
        }
    }

    void setActiveDrill(@NonNull Drill drill) {
        activeDrillSubject.onNext(drill);
        if (performanceSubject.getValue() == null) {
            performanceSubject.onNext(new Performance(drill));
        } else {
            handleSetCompletion(performanceSubject.getValue());
        }

    }

    private void handleSetCompletion(Performance performance) {
        //noinspection ConstantConditions
        performanceSubject.onNext(new Performance(activeDrillSubject.getValue()));
        if (performance.getTotal() > 0) {
            performance.captureEndTime();
            completionSubject.onNext(performance);
            storePerformance(performance);
        }
    }

    private void storePerformance(Performance performance) {
        databaseManager.getPerformanceDao().insert(performance).subscribe(new SingleObserver<List<Long>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onSuccess(List<Long> rowIds) {
                Log.d(TAG, "Performance Insertion Success: " + performance);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Performance Insertion Failed: " + performance);
            }
        });
    }
}
