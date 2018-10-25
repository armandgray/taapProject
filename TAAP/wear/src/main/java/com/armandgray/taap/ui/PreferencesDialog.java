package com.armandgray.taap.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.armandgray.shared.application.UIComponent;
import com.armandgray.shared.model.UXPreference;
import com.armandgray.shared.navigation.NavigationActivity;
import com.armandgray.shared.ui.RecyclerItemClickListener;
import com.armandgray.shared.viewModel.PreferencesViewModel;
import com.armandgray.taap.R;
import com.armandgray.taap.navigation.Destination;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjection;

public class PreferencesDialog extends NavigationActivity implements UIComponent {

    @Inject
    PreferencesViewModel preferencesViewModel;

    @Inject
    PreferencesAdapter preferencesAdapter;

    private TextView textTitle;
    private WearableRecyclerView recyclerPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Dagger Injection
        AndroidInjection.inject(this);

        // Super
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.dialog_preferences);
        onSetupContent();
    }

    @Override
    public void assignGlobalFields() {
        textTitle = findViewById(R.id.text_title);
        recyclerPreferences = findViewById(R.id.recycler_preferences);
    }

    @Override
    public void setupVisualElements() {
        textTitle.setBackgroundResource(R.drawable.bg_round_corners_outline);
        recyclerPreferences.setAdapter(preferencesAdapter);
        recyclerPreferences.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void setupEventListeners() {
        recyclerPreferences.addOnItemTouchListener(new RecyclerItemClickListener(this,
                (view, position) -> {
                    UXPreference.Value preference = preferencesAdapter.getPreference(position);
                    switch (preference.getItem().getTypeConstant()) {
                        case NUMBER_RANGE:
                            navigationViewModel.onNavigate(Destination.SEEK_BAR_DIALOG);
                            preferencesViewModel.setActiveValue(preference);
                            return;

                        case TOGGLE:
                            navigationViewModel.onNavigate(Destination.TOGGLE_DIALOG);
                            preferencesViewModel.setActiveValue(preference);
                            return;

                        case TRIGGERED:
                            preferencesViewModel.onPreferenceTriggered(preference);
                            finish();
                            return;

                        default:
                            Log.e(TAG, "Recycler Item Contains Unsupported TypeConstant");
                    }
                }));
    }

    @Override
    public void setupViewModel() {
        super.setupViewModel();

        preferencesViewModel.getActivePreference().observe(this, this::onSelectionChanged);
    }

    private void onSelectionChanged(@Nullable UXPreference preference) {
        if (preference != null) {
            textTitle.setText(preference.getTitle());
            preferencesAdapter.updateData(preference.getValues());
        }
    }

    @Module
    public static class ActivityModule
            extends NavigationActivity.NavigationModule<PreferencesDialog> {

        @Provides
        @NonNull
        PreferencesViewModel providePreferenceViewModel(PreferencesDialog activity) {
            return ViewModelProviders.of(activity).get(PreferencesViewModel.class);
        }
    }
}
