package com.armandgray.taap.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;
import com.armandgray.shared.model.UXPreference;
import com.armandgray.shared.sensors.LinearAccelerationAction;
import com.armandgray.shared.util.VibrateUtil;
import com.armandgray.shared.viewModel.DrillViewModel;
import com.armandgray.shared.viewModel.PerformanceViewModel;
import com.armandgray.shared.voice.VoiceEvent;
import com.armandgray.taap.R;
import com.armandgray.taap.application.WearDelegateActivity;
import com.armandgray.taap.navigation.Destination;
import com.armandgray.taap.ui.MultiInputClickListener;

import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.wear.activity.ConfirmationActivity;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjection;

public class ActiveDrillActivity extends WearDelegateActivity {

    public static final int RESET_DISPLAY_DELAY = 1000;
    @Inject
    PerformanceViewModel performanceViewModel;

    @Inject
    DrillViewModel drillViewModel;

    @Inject
    VibrateUtil vibrator;

    private ConstraintLayout rootView;
    private TextView textDrill;
    private ImageButton buttonMinus;
    private TextView textRate;
    private ImageButton buttonPlus;
    private ImageButton buttonClear;
    private ImageView imageBackgroundFeature;
    private TextView textBackgroundFeature;
    private View loadingMask;
    private ProgressBar progressBar;

    private boolean enableScreenTaps;
    private int vibrationLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Dagger Injection
        AndroidInjection.inject(this);

        // Super
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_active_drill);
        super.onSetupContent();
    }

    @Override
    protected void onResume() {
        super.onResume();

        performanceViewModel.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        performanceViewModel.onPause();
    }

    @Override
    public void assignGlobalFields() {
        super.assignGlobalFields();

        rootView = findViewById(R.id.root_view);
        textDrill = findViewById(R.id.text_drill);
        buttonMinus = findViewById(R.id.button_minus);
        textRate = findViewById(R.id.text_rate);
        buttonPlus = findViewById(R.id.button_plus);
        buttonClear = findViewById(R.id.button_clear);
        imageBackgroundFeature = findViewById(R.id.image_background_feature);
        textBackgroundFeature = findViewById(R.id.text_background_feature);
        loadingMask = findViewById(R.id.loading_mask);
        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    public void setupVisualElements() {
        super.setupVisualElements();

        super.wearableActionDrawer.getController().peekDrawer();
        super.wearableActionDrawer.setVisibility(View.VISIBLE);

        textDrill.setBackgroundResource(R.drawable.bg_round_corners_outline);
        buttonMinus.setImageResource(R.drawable.ic_remove_white_24dp);
        buttonPlus.setImageResource(R.drawable.ic_add_white_24dp);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void setupEventListeners() {
        super.setupEventListeners();

        rootView.setOnTouchListener(onMultiInputClick());
        textDrill.setOnClickListener(view -> navigationViewModel
                .onNavigate(Destination.DRILL_PICKER_DIALOG));
        buttonMinus.setOnClickListener(view -> performanceViewModel.onMinusClick());
        buttonPlus.setOnClickListener(view -> performanceViewModel.onPlusClick());
        buttonClear.setOnClickListener(view -> performanceViewModel.clearPerformance());
    }

    @NonNull
    private MultiInputClickListener onMultiInputClick() {
        return new MultiInputClickListener(new MultiInputClickListener.OnMultiInputClickListener() {
            @Override
            public void onSingleInputClick(View view) {
                if (enableScreenTaps) {
                    performanceViewModel.onSingleInputClick();
                }
            }

            @Override
            public void onDoubleInputClick(View view) {
                if (enableScreenTaps) {
                    performanceViewModel.onDoubleInputClick();
                }
            }
        });
    }

    @Override
    public void setupViewModel() {
        super.setupViewModel();

        performanceViewModel.getActiveDrill().observe(this, this::onDrillChanged);
        performanceViewModel.getPerformance().observe(this, this::onPerformanceChange);
        performanceViewModel.getCompletionObserver().observe(this, this::onSetCompletion);
        performanceViewModel.getFeatureStatus().observe(this, this::onFeatureStatusChanged);

        performanceViewModel.getAutoTrackingEvent().observe(this, this::onAutoTrackingEvent);
        performanceViewModel.getVoiceEvent().observe(this, this::onVoiceEvent);
    }

    private void onDrillChanged(@Nullable Drill drill) {
        if (drill == null) {
            return;
        }

        textDrill.setText(drill.getTitle());
        loadingMask.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void onPerformanceChange(@Nullable Performance performance) {
        if (performance != null) {
            textRate.setText(getPerformanceText(performance));
        }
    }

    private String getPerformanceText(@NonNull Performance performance) {
        return String.format(Locale.getDefault(), "%d/%d",
                performance.getCount(), performance.getTotal());
    }

    private void onSetCompletion(@Nullable Performance performance) {
        if (performance == null) {
            return;
        }

        vibrator.vibrate(vibrationLength);

        int animation = performance.isSuccess()
                ? ConfirmationActivity.SUCCESS_ANIMATION
                : ConfirmationActivity.FAILURE_ANIMATION;

        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, animation);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, getPerformanceText(performance));
        startActivity(intent);
    }

    private void onFeatureStatusChanged(PerformanceViewModel.FeatureStatus featureStatus) {
        Log.d(TAG, "onFeatureStatusChanged: " + featureStatus);
        buttonClear.setVisibility(featureStatus.isClearEnabled() ? View.VISIBLE : View.GONE);
        buttonMinus.setVisibility(featureStatus.isIconsEnabled() ? View.VISIBLE : View.GONE);
        buttonPlus.setVisibility(featureStatus.isIconsEnabled() ? View.VISIBLE : View.GONE);
        enableScreenTaps = featureStatus.isScreenTapsEnabled();
        vibrationLength = featureStatus.getVibrationLength();

        if (featureStatus.isVoice()) {
            if (featureStatus.isVoiceEnabled()) {
                // Attempt Voice Registration (Should do nothing if disabled)
                performanceViewModel.registerVoiceRecognition(false);
            } else {
                onVoiceEvent(VoiceEvent.INACTIVE);
                performanceViewModel.unregisterVoiceRecognition();
            }
        }
    }

    private void onAutoTrackingEvent(@Nullable LinearAccelerationAction gesture) {
        switch (gesture != null ? gesture : LinearAccelerationAction.NONE) {
            case ACTIVE:
                onAutoTrackingActive();
                return;

            case INACTIVE:
                onAutoTrackingInactive();
                return;

            case MISSING_HARDWARE:
                onAutoTrackingMissingHardware();
                return;

            case HORIZONTAL_FLING_AWAY:
                onAutoTrackingHorizontalFlingAway();
                return;

            default:
                Log.d(TAG, "onAutoTrackingEvent: Unhandled Gesture: " + gesture);
        }
    }

    private void onAutoTrackingActive() {
        imageBackgroundFeature.setImageResource(UXPreference.Item.AUTO.getImageResId());
    }

    private void onAutoTrackingInactive() {
        imageBackgroundFeature.setImageResource(android.R.color.transparent);
    }

    private void onAutoTrackingMissingHardware() {
        textBackgroundFeature.setText(getString(R.string.missing_hardware));
    }

    private void onAutoTrackingHorizontalFlingAway() {
        textBackgroundFeature.setText(getString(R.string.shot));
        imageBackgroundFeature.setImageResource(R.drawable.ic_settings_voice_white_24dp);
        performanceViewModel.registerVoiceRecognition(true);
    }

    private void onVoiceEvent(@Nullable VoiceEvent voiceEvent) {
        switch (voiceEvent != null ? voiceEvent : VoiceEvent.NONE) {
            case ACTIVE:
                onVoiceEventActive();
                return;

            case MISSING_PERMISSION:
                onVoiceEventMissingPermission();
                return;

            // Break
            case TIMEOUT:
                onVoiceEventTimeout();
                break;

            case INACTIVE:
                onVoiceEventInactive();
                break;

            case CLAP:
                onVoiceEventClap();
                break;

            case DOUBLE_CLAP:
                onVoiceEventDoubleClap();
                break;

            case SPEECH:
                onVoiceEventSpeech();
                break;

            default:
                Log.d(TAG, "onVoiceEvent: Unhandled VoiceEvent: " + voiceEvent);
        }

        // Attempt Voice Registration (Should do nothing if disabled)
        performanceViewModel.registerVoiceRecognition(false);
    }

    private void onVoiceEventMissingPermission() {
        imageBackgroundFeature.setImageResource(R.drawable.ic_settings_voice_white_24dp);
        textBackgroundFeature.setText(getString(R.string.permission));
    }

    private void onVoiceEventTimeout() {
        textBackgroundFeature.setText(getString(R.string.timeout));
    }

    private void onVoiceEventActive() {
        imageBackgroundFeature.setImageResource(R.drawable.ic_settings_voice_white_24dp);
        textBackgroundFeature.setText("");
    }

    private void onVoiceEventInactive() {
        imageBackgroundFeature.setImageResource(android.R.color.transparent);
    }

    private void onVoiceEventClap() {
        textBackgroundFeature.setText(getString(R.string.clap));
        performanceViewModel.onSingleClap();
        resetDisplayWithDelay();
    }

    private void onVoiceEventDoubleClap() {
        textBackgroundFeature.setText(getString(R.string.double_clap));
        performanceViewModel.onDoubleClap();
        resetDisplayWithDelay();
    }

    private void onVoiceEventSpeech() {
        textBackgroundFeature.setText(getString(R.string.speech));
        resetDisplayWithDelay();
    }

    private void resetDisplayWithDelay() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> textBackgroundFeature.setText(""), RESET_DISPLAY_DELAY);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_targets:
                Drill drill = drillViewModel.getActiveDrill().getValue();
                if (drill == null) {
                    return false;
                }

                preferencesViewModel.setActivePreference(drill.getPreference());
                navigationViewModel.onNavigate(Destination.PREFERENCES_DIALOG);
                return true;
        }

        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }

    @Module
    public static class ActivityModule
            extends WearDelegateActivity.NavigationModule<ActiveDrillActivity> {

        @Provides
        @NonNull
        PerformanceViewModel providePerformanceViewModel(ActiveDrillActivity activity) {
            return ViewModelProviders.of(activity).get(PerformanceViewModel.class);
        }

        @Provides
        @NonNull
        DrillViewModel provideDrillViewModel(ActiveDrillActivity activity) {
            return ViewModelProviders.of(activity).get(DrillViewModel.class);
        }
    }
}
