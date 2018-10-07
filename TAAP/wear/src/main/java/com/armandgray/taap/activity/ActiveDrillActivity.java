package com.armandgray.taap.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.PerformanceRate;
import com.armandgray.shared.viewModel.DrillViewModel;
import com.armandgray.taap.R;
import com.armandgray.taap.navigation.Destination;
import com.armandgray.taap.navigation.WearNavigationActivity;
import com.armandgray.taap.ui.MultiInputClickListener;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.wear.activity.ConfirmationActivity;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjection;

public class ActiveDrillActivity extends WearNavigationActivity {

    @Inject
    DrillViewModel drillViewModel;

    private ConstraintLayout rootView;
    private TextView textDrill;
    private ImageButton buttonMinus;
    private TextView textRate;
    private ImageButton buttonPlus;

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
    }

    @Override
    public  void setupVisualElements() {
        super.setupVisualElements();

        textDrill.setBackgroundResource(R.drawable.bg_round_outline);
        buttonMinus.setImageResource(R.drawable.ic_remove_white_24dp);
        buttonPlus.setImageResource(R.drawable.ic_add_white_24dp);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public  void setupEventListeners() {
        super.setupEventListeners();

        rootView.setOnTouchListener(onMultiInputClick());
        textDrill.setOnClickListener(view -> navigationViewModel
                .onNavigate(Destination.DRILL_PICKER));
        buttonMinus.setOnClickListener(view -> drillViewModel.onMinusClick());
        buttonPlus.setOnClickListener(view -> drillViewModel.onPlusClick());
    }

    @NonNull
    private MultiInputClickListener onMultiInputClick() {
        return new MultiInputClickListener(new MultiInputClickListener.OnMultiInputClickListener() {
            @Override
            public void onSingleInputClick(View view) {
                drillViewModel.onSingleInputClick();
            }

            @Override
            public void onDoubleInputClick(View view) {
                drillViewModel.onDoubleInputClick();
            }
        });
    }

    @Override
    public  void setupViewModel() {
        super.setupViewModel();

        drillViewModel.getDrill().observe(this, this::onDrillChanged);
        drillViewModel.getPerformance().observe(this, this::onPerformanceRateChange);
        drillViewModel.getCompletionObserver().observe(this, this::onConfirmationChange);
    }

    private void onDrillChanged(Drill drill) {
        if (drill != null) {
            textDrill.setText(drill.getTitle());
        }
    }

    private void onPerformanceRateChange(PerformanceRate rate) {
        if (rate != null) {
            textRate.setText(rate.toString());
        }
    }

    private void onConfirmationChange(PerformanceRate rate) {
        if (rate == null) {
            return;
        }

        int animation = rate.isSuccess()
                ? ConfirmationActivity.SUCCESS_ANIMATION
                : ConfirmationActivity.FAILURE_ANIMATION;

        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, animation);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, rate.toString());
        startActivity(intent);
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
        DrillViewModel provideDrillViewModel(ActiveDrillActivity activity) {
            return ViewModelProviders.of(activity).get(DrillViewModel.class);
        }
    }
}
