package com.armandgray.taap.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.wear.activity.ConfirmationActivity;
import android.support.wear.widget.drawer.WearableActionDrawerView;
import android.support.wear.widget.drawer.WearableDrawerLayout;
import android.support.wear.widget.drawer.WearableNavigationDrawerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.armandgray.shared.db.ShootingPercentageViewModel;
import com.armandgray.shared.model.PerformanceRate;
import com.armandgray.taap.R;
import com.armandgray.taap.ui.MultiInputClickListener;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjection;

import static com.armandgray.shared.db.ShootingPercentageViewModel.ACTION_COURT;
import static com.armandgray.shared.db.ShootingPercentageViewModel.ACTION_LOGS;
import static com.armandgray.shared.db.ShootingPercentageViewModel.ACTION_SETTINGS;
import static com.armandgray.shared.db.ShootingPercentageViewModel.ACTION_TARGETS;

public class ActiveDrillActivity extends AppCompatActivity implements
        MenuItem.OnMenuItemClickListener,
        WearableNavigationDrawerView.OnItemSelectedListener {

    @Inject
    ShootingPercentageViewModel viewModel;

    @Inject
    ActiveDrillDrawerAdapter drawerAdapter;

    private WearableDrawerLayout wearableDrawerLayout;
    private WearableNavigationDrawerView wearableNavigationDrawer;
    private WearableActionDrawerView wearableActionDrawer;

    private ConstraintLayout rootView;
    private TextView textDrill;
    private ImageButton buttonMinus;
    private TextView textRate;
    private ImageButton buttonPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_drill);

        // Dagger Injection
        AndroidInjection.inject(this);

        assignGlobalFields();
        setupNavigationDrawer();
        setupVisualElements();
        setupEventListeners();
        setupViewModel();
    }

    private void assignGlobalFields() {
        wearableDrawerLayout = findViewById(R.id.drawer_layout);
        wearableNavigationDrawer = findViewById(R.id.top_navigation_drawer);
        wearableActionDrawer = findViewById(R.id.bottom_action_drawer);

        rootView = findViewById(R.id.rootView);
        textDrill = findViewById(R.id.textDrill);
        buttonMinus = findViewById(R.id.buttonMinus);
        textRate = findViewById(R.id.textRate);
        buttonPlus = findViewById(R.id.buttonPlus);
    }

    private void setupNavigationDrawer() {
        wearableNavigationDrawer.setAdapter(drawerAdapter);
        wearableNavigationDrawer.getController().peekDrawer();
        wearableNavigationDrawer.addOnItemSelectedListener(this);

        wearableActionDrawer.getController().peekDrawer();
        wearableActionDrawer.setPeekOnScrollDownEnabled(true);
        wearableActionDrawer.setOnMenuItemClickListener(this);
    }

    private void setupVisualElements() {
        textDrill.setBackgroundResource(R.drawable.bg_round_outline);
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

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_targets:
                viewModel.onAction(ACTION_TARGETS);
                return true;
        }

        return false;
    }

    @Override
    public void onItemSelected(int position) {
        switch (position) {
            case 0:
                viewModel.onAction(ACTION_COURT);
                break;

            case 1:
                viewModel.onAction(ACTION_LOGS);
                break;

            case 2:
                viewModel.onAction(ACTION_SETTINGS);
                break;
        }
    }

    @Module
    public static class ActivityModule {

        @Provides
        ShootingPercentageViewModel provideViewModel(ActiveDrillActivity activity) {
            return ViewModelProviders.of(activity).get(ShootingPercentageViewModel.class);
        }
    }
}
