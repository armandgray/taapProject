package com.armandgray.taap.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.wear.activity.ConfirmationActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.armandgray.shared.model.PerformanceRate;
import com.armandgray.shared.viewModel.PercentageRateViewModel;
import com.armandgray.taap.R;
import com.armandgray.taap.navigation.Destination;
import com.armandgray.taap.navigation.WearNavigationActivity;
import com.armandgray.taap.ui.MultiInputClickListener;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjection;

public class ActiveDrillActivity extends WearNavigationActivity {

    @Inject
    PercentageRateViewModel percentageRateViewModel;

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

        rootView = findViewById(R.id.rootView);
        textDrill = findViewById(R.id.textDrill);
        buttonMinus = findViewById(R.id.buttonMinus);
        textRate = findViewById(R.id.textRate);
        buttonPlus = findViewById(R.id.buttonPlus);
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
        buttonMinus.setOnClickListener(view -> percentageRateViewModel.onMinusClick());
        buttonPlus.setOnClickListener(view -> percentageRateViewModel.onPlusClick());
    }

    @NonNull
    private MultiInputClickListener onMultiInputClick() {
        return new MultiInputClickListener(new MultiInputClickListener.OnMultiInputClickListener() {
            @Override
            public void onSingleInputClick(View view) {
                percentageRateViewModel.onSingleInputClick();
            }

            @Override
            public void onDoubleInputClick(View view) {
                percentageRateViewModel.onDoubleInputClick();
            }
        });
    }

    @Override
    public  void setupViewModel() {
        super.setupViewModel();

        percentageRateViewModel.getCurrentRate().observe(this, this::onPerformanceRateChange);
        percentageRateViewModel.getCompletionObserver().observe(this, this::onConfirmationChange);
    }

    private void onPerformanceRateChange(PerformanceRate rate) {
        if (rate == null) {
            return;
        }

        textDrill.setText(rate.getDrill());
        textRate.setText(rate.toString());
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

    @Module
    public static class ActivityModule
            extends WearNavigationActivity.NavigationModule<ActiveDrillActivity> {

        @Provides
        PercentageRateViewModel providePercentageViewModel(ActiveDrillActivity activity) {
            return ViewModelProviders.of(activity).get(PercentageRateViewModel.class);
        }
    }
}
