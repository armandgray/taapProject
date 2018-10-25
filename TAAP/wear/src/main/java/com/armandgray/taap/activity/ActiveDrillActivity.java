package com.armandgray.taap.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;
import com.armandgray.shared.model.UXPreference;
import com.armandgray.shared.viewModel.DrillViewModel;
import com.armandgray.shared.viewModel.PerformanceViewModel;
import com.armandgray.taap.R;
import com.armandgray.taap.navigation.Destination;
import com.armandgray.taap.navigation.WearNavigationActivity;
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

public class ActiveDrillActivity extends WearNavigationActivity {

    @Inject
    PerformanceViewModel performanceViewModel;

    @Inject
    DrillViewModel drillViewModel;

    private ConstraintLayout rootView;
    private TextView textDrill;
    private ImageButton buttonMinus;
    private TextView textRate;
    private ImageButton buttonPlus;
    private ImageButton buttonClear;
    private View loadingMask;
    private ProgressBar progressBar;

    private boolean enableScreenTaps;

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
    public void assignGlobalFields() {
        super.assignGlobalFields();

        rootView = findViewById(R.id.root_view);
        textDrill = findViewById(R.id.text_drill);
        buttonMinus = findViewById(R.id.button_minus);
        textRate = findViewById(R.id.text_rate);
        buttonPlus = findViewById(R.id.button_plus);
        buttonClear = findViewById(R.id.button_clear);
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
        performanceViewModel.getCompletionObserver().observe(this, this::onConfirmationChange);
        performanceViewModel.getPreferenceObserver().observe(this, this::onPreferenceChange);
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

    private void onConfirmationChange(@Nullable Performance performance) {
        if (performance == null) {
            return;
        }

        int animation = performance.isSuccess()
                ? ConfirmationActivity.SUCCESS_ANIMATION
                : ConfirmationActivity.FAILURE_ANIMATION;

        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, animation);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, getPerformanceText(performance));
        startActivity(intent);
    }

    private void onPreferenceChange(UXPreference preference) {
        if (preference.getCategory() != UXPreference.Category.WORKOUT) {
            return;
        }

        boolean clearEnabled = preference.isEnabled(UXPreference.Item.CLEAR);
        buttonClear.setVisibility(clearEnabled ? View.VISIBLE : View.GONE);

        boolean minusEnabled = preference.isEnabled(UXPreference.Item.MINUS);
        buttonMinus.setVisibility(minusEnabled ? View.VISIBLE : View.GONE);

        enableScreenTaps = preference.isEnabled(UXPreference.Item.SCREEN_TAPS);
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
            extends WearNavigationActivity.NavigationModule<ActiveDrillActivity> {

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
