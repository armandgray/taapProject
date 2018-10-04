package com.armandgray.taap.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.wear.activity.ConfirmationActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.armandgray.shared.db.ShootingPercentageViewModel;
import com.armandgray.shared.model.PerformanceRate;
import com.armandgray.taap.R;
import com.armandgray.taap.ui.MultiInputClickListener;

import dagger.Module;
import dagger.android.AndroidInjection;

public class ActiveDrillActivity extends AppCompatActivity {

    private ShootingPercentageViewModel viewModel;

    private ConstraintLayout rootView;
    private TextView textDrill;
    private FloatingActionButton buttonMinus;
    private TextView textRate;
    private FloatingActionButton buttonPlus;
    private ImageButton buttonMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Dagger Injection
        AndroidInjection.inject(this);

        assignGlobalFields();
        setupButtons();
        setupEventListeners();
        setupViewModel();
    }

    private void assignGlobalFields() {
        rootView = findViewById(R.id.rootView);
        textDrill = findViewById(R.id.textDrill);
        buttonMinus = findViewById(R.id.buttonMinus);
        textRate = findViewById(R.id.textRate);
        buttonPlus = findViewById(R.id.buttonPlus);
        buttonMenu = findViewById(R.id.buttonMenu);
    }

    private void setupButtons() {
        buttonMinus.setImageResource(R.drawable.ic_remove_white_24dp);
        buttonPlus.setImageResource(R.drawable.ic_add_white_24dp);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupEventListeners() {
        rootView.setOnTouchListener(onMultiInputClick());
        buttonMinus.setOnClickListener(view -> viewModel.addMiss());
        buttonPlus.setOnClickListener(view -> viewModel.addMake());
    }

    @NonNull
    private MultiInputClickListener onMultiInputClick() {
        return new MultiInputClickListener(new MultiInputClickListener.OnMultiInputClickListener() {
            @Override
            public void onSingleInputClick(View view) {
                viewModel.addMake();
            }

            @Override
            public void onDoubleInputClick(View view) {
                viewModel.addMiss();
            }
        });
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(ShootingPercentageViewModel.class);
        viewModel.getCurrentRate().observe(this, this::onPerformanceRateChange);
        viewModel.getCompletionObserver().observe(this, this::onConfirmationChange);
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
    public static class ActivityModule {
    }
}
