package com.armandgray.shared.viewModel;

import android.util.Log;

import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.application.TAAPViewModel;
import com.armandgray.shared.helpers.StringHelper;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;
import com.armandgray.shared.model.UXPreference;
import com.armandgray.shared.sensors.LinearAccelerationAction;
import com.armandgray.shared.voice.VoiceEvent;

import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class PerformanceViewModel extends TAAPViewModel {

    private MutableLiveData<Drill> activeDrillLiveData;
    private MutableLiveData<Performance> performanceLiveData;
    private MutableLiveData<Performance> completionLiveData;
    private MutableLiveData<FeatureStatus> preferenceLiveData;

    private MutableLiveData<LinearAccelerationAction> autoTrackLiveData = new MutableLiveData<>();
    private MutableLiveData<VoiceEvent> voiceEventLiveData = new MutableLiveData<>();

    @Inject
    DrillRepository drillRepository;

    @Inject
    PreferencesRepository preferencesRepository;

    private Disposable autoTrackingDisposable;
    private Disposable voiceRecognitionDisposable;

    PerformanceViewModel() {
        TAAPApplication.getAppComponent().inject(this);
    }

    public final LiveData<Drill> getActiveDrill() {
        if (activeDrillLiveData == null) {
            activeDrillLiveData = new MutableLiveData<>();

            drillRepository.getActiveDrillObservable().subscribe(new ViewModelObserver<Drill>() {
                @Override
                public void onNext(@NonNull Drill drill) {
                    activeDrillLiveData.setValue(drill);
                }
            });
        }

        return activeDrillLiveData;
    }

    public final LiveData<Performance> getPerformance() {
        if (performanceLiveData == null) {
            performanceLiveData = new MutableLiveData<>();

            drillRepository.getPerformanceObservable().subscribe(
                    new ViewModelObserver<Performance>() {
                        @Override
                        public void onNext(@NonNull Performance performance) {
                            performanceLiveData.setValue(performance);
                        }
                    });
        }

        return performanceLiveData;
    }

    public final LiveData<Performance> getCompletionObserver() {
        if (completionLiveData == null) {
            completionLiveData = new MutableLiveData<>();

            drillRepository.getCompletionObservable().subscribe(
                    new ViewModelObserver<Performance>() {
                        @Override
                        public void onNext(@NonNull Performance performance) {
                            completionLiveData.setValue(performance);
                        }
                    });
        }

        return completionLiveData;
    }

    public final LiveData<FeatureStatus> getFeatureStatus() {
        if (preferenceLiveData == null) {
            preferenceLiveData = new MutableLiveData<>();

            preferencesRepository.getPreferenceUpdateObservable().subscribe(
                    new ViewModelObserver<UXPreference>() {
                        @Override
                        public void onNext(@NonNull UXPreference preference) {
                            preferenceLiveData.setValue(FeatureStatus.from(preference));
                        }
                    });
        }

        return preferenceLiveData;
    }

    public final LiveData<LinearAccelerationAction> getAutoTrackingEvent() {
        return autoTrackLiveData;
    }

    private void registerAutoTracking() {
        drillRepository.getAutoTrackingObservable().subscribe(
                new ViewModelObserver<LinearAccelerationAction>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        super.onSubscribe(d);

                        unregisterAutoTracking();
                        autoTrackingDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull LinearAccelerationAction gesture) {
                        autoTrackLiveData.setValue(gesture);
                    }
                });
    }

    private void unregisterAutoTracking() {
        if (autoTrackingDisposable != null && !autoTrackingDisposable.isDisposed()) {
            autoTrackingDisposable.dispose();
        }
    }

    public final LiveData<VoiceEvent> getVoiceEvent() {
        return voiceEventLiveData;
    }

    public final void registerVoiceRecognition(boolean triggered) {
        if (voiceRecognitionDisposable != null) {
            return;
        }

        voiceEventObservable(triggered).subscribe(new ViewModelObserver<VoiceEvent>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                super.onSubscribe(d);

                voiceRecognitionDisposable = d;
            }

            @Override
            public void onNext(VoiceEvent voiceEvent) {
                if (triggered && voiceEvent != VoiceEvent.ACTIVE && voiceEvent != VoiceEvent.INACTIVE) {
                    unregisterVoiceRecognition();
                }

                voiceEventLiveData.setValue(voiceEvent);
            }
        });
    }

    private Observable<VoiceEvent> voiceEventObservable(boolean triggered) {
        return triggered
                ? drillRepository.getVoiceEventObservable()
                : drillRepository.getTriggeredVoiceEventObservable();
    }

    public void unregisterVoiceRecognition() {
        if (voiceRecognitionDisposable != null && !voiceRecognitionDisposable.isDisposed()) {
            voiceRecognitionDisposable.dispose();
        }
    }

    public void onResume() {
        registerAutoTracking();
    }

    public void onPause() {
        unregisterAutoTracking();
    }

    public void onPlusClick() {
        drillRepository.addMake();
    }

    public void onMinusClick() {
        drillRepository.addMiss();
    }

    public void onSingleInputClick() {
        drillRepository.addMake();
    }

    public void onDoubleInputClick() {
        drillRepository.addMiss();
    }

    public void onSingleClap() {
        drillRepository.addMiss();
    }

    public void onDoubleClap() {
        drillRepository.addMake();
    }

    public void clearPerformance() {
        drillRepository.clearPerformance();
    }

    public static class FeatureStatus {

        private final String TAG = StringHelper.toLogTag(getClass().getSimpleName());

        private static UXPreference.Category category;

        private static boolean clearEnabled;
        private static boolean iconsEnabled;
        private static boolean screenTapsEnabled;
        private static int vibrationLength;

        private static boolean clapEnabled;
        private static boolean callOutEnabled;
        private static boolean voiceEnabled;

        @NonNull
        static FeatureStatus from(@NonNull UXPreference preference) {
            return new FeatureStatus(preference);
        }

        private FeatureStatus(@NonNull UXPreference preference) {
            category = preference.getCategory();

            switch (preference.getCategory()) {
                case WORKOUT:
                    clearEnabled = preference.isEnabled(UXPreference.Item.CLEAR);
                    iconsEnabled = preference.isEnabled(UXPreference.Item.ICONS);
                    screenTapsEnabled = preference.isEnabled(UXPreference.Item.SCREEN_TAPS);
                    vibrationLength = preference.getValue(UXPreference.Item.VIBRATE, true);
                    return;

                case VOICE:
                    clapEnabled = preference.isEnabled(UXPreference.Item.CLAP);
                    callOutEnabled = preference.isEnabled(UXPreference.Item.CALL_OUT);
                    voiceEnabled = clapEnabled || callOutEnabled;
                    return;

                default:
                    Log.d(TAG, "Unhandled Preference Received");
            }
        }

        public boolean isClearEnabled() {
            return clearEnabled;
        }

        public boolean isIconsEnabled() {
            return iconsEnabled;
        }

        public boolean isScreenTapsEnabled() {
            return screenTapsEnabled;
        }

        public int getVibrationLength() {
            return vibrationLength;
        }

        public boolean isVoice() {
            return category == UXPreference.Category.VOICE;
        }

        public boolean isVoiceEnabled() {
            return voiceEnabled;
        }

        @NonNull
        @Override
        public String toString() {
            return String.format(Locale.getDefault(),
                    "FeatureStatus{clear: %b, icons: %b, taps: %b, vibration: %d}",
                    clearEnabled, iconsEnabled, screenTapsEnabled, vibrationLength);
        }
    }
}
