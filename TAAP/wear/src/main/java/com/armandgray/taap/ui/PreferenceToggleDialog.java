package com.armandgray.taap.ui;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.armandgray.shared.application.UIComponent;
import com.armandgray.shared.model.UXPreference;
import com.armandgray.shared.navigation.NavigationActivity;
import com.armandgray.shared.viewModel.PreferencesViewModel;
import com.armandgray.taap.R;
import com.armandgray.taap.application.WearDelegateDialog;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjection;

public class PreferenceToggleDialog extends WearDelegateDialog implements UIComponent {

    @Inject
    PreferencesViewModel preferencesViewModel;

    private TextView textTitle;
    private TextView textDescription;
    private ImageView buttonReset;
    private CheckBox checkboxEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Dagger Injection
        AndroidInjection.inject(this);

        // Super
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.dialog_preference_toggle);
        onSetupContent();
    }

    @Override
    public void assignGlobalFields() {
        textTitle = findViewById(R.id.text_title);
        textDescription = findViewById(R.id.text_description);
        buttonReset = findViewById(R.id.button_reset);
        checkboxEnabled = findViewById(R.id.checkbox_enabled);
    }

    @Override
    public void setupVisualElements() {
        textTitle.setBackgroundResource(R.drawable.bg_round_corners_outline);
    }

    @Override
    public void setupEventListeners() {
        buttonReset.setOnClickListener(view -> {
            UXPreference.Value value = preferencesViewModel.getActiveValue().getValue();
            if (value != null) {
                value.setValue(value.getItem().getDefault());
                update(value);
            }
        });

        checkboxEnabled.setOnClickListener(view -> {
            UXPreference.Value value = preferencesViewModel.getActiveValue().getValue();
            if (value != null) {
                value.setEnabled(!value.isEnabled());
                update(value);
            }
        });
    }

    private void update(UXPreference.Value value) {
        checkboxEnabled.setChecked(value.isEnabled());
        preferencesViewModel.setActiveValue(value);
        preferencesViewModel.onPreferenceUpdated();
        if (value.isEnabled() && value.getItem().hasWarning()) {
            Toast.makeText(this, value.getItem().getWarning(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setupViewModel() {
        super.setupViewModel();

        preferencesViewModel.getActiveValue().observe(this, this::onSelectionChanged);
    }

    private void onSelectionChanged(@Nullable UXPreference.Value value) {
        if (value != null) {
            UXPreference.Item item = value.getItem();
            textTitle.setText(item.name());
            textDescription.setText(item.getDescription());
            checkboxEnabled.setChecked(value.isEnabled());
        }
    }

    @Module
    public static class ActivityModule
            extends NavigationActivity.NavigationModule<PreferenceToggleDialog> {

        @Provides
        @NonNull
        PreferencesViewModel provideViewModel(PreferenceToggleDialog activity) {
            return ViewModelProviders.of(activity).get(PreferencesViewModel.class);
        }
    }
}
