package com.armandgray.taap.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.armandgray.shared.model.UXPreference;
import com.armandgray.shared.ui.RecyclerItemClickListener;
import com.armandgray.taap.R;
import com.armandgray.taap.navigation.Destination;
import com.armandgray.taap.application.WearDelegateActivity;

import javax.inject.Inject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;
import dagger.Module;
import dagger.android.AndroidInjection;

public class SettingsActivity extends WearDelegateActivity {

    @Inject
    SettingsAdapter settingsAdapter;

    private TextView textTitle;
    private WearableRecyclerView recyclerSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Dagger Injection
        AndroidInjection.inject(this);

        // Super
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_settings);
        super.onSetupContent();
    }

    @Override
    public void assignGlobalFields() {
        super.assignGlobalFields();

        textTitle = findViewById(R.id.text_title);
        recyclerSettings = findViewById(R.id.recycler_settings);
    }

    @Override
    public void setupVisualElements() {
        super.setupVisualElements();

        textTitle.setBackgroundResource(R.drawable.bg_round_corners_outline);
        recyclerSettings.setAdapter(settingsAdapter);
        recyclerSettings.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void setupEventListeners() {
        super.setupEventListeners();

        recyclerSettings.addOnItemTouchListener(new RecyclerItemClickListener(this,
                (view, position) -> {
                    UXPreference preference = settingsAdapter.getSetting(position).getPreference();
                    preferencesViewModel.setActivePreference(preference);
                    navigationViewModel.onNavigate(Destination.PREFERENCES_DIALOG);
                }));
    }

    @Override
    public void setupViewModel() {
        super.setupViewModel();

        preferencesViewModel.getSettings().observe(this, settingsAdapter::updateData);
    }

    @Module
    public static class ActivityModule
            extends WearDelegateActivity.NavigationModule<SettingsActivity> {
    }
}
