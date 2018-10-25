package com.armandgray.taap.ui;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.armandgray.shared.application.UIComponent;
import com.armandgray.shared.model.UXPreference;
import com.armandgray.shared.navigation.NavigationActivity;
import com.armandgray.shared.viewModel.PreferencesViewModel;
import com.armandgray.taap.R;

import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjection;

public class PreferenceSeekBarDialog extends NavigationActivity implements UIComponent {

    @Inject
    PreferencesViewModel preferencesViewModel;

    private TextView textTitle;
    private TextView textDescription;
    private SeekBar seekBar;
    private ImageView buttonReset;
    private TextView textValue;
    private ImageButton buttonCancel;
    private ImageButton buttonDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Dagger Injection
        AndroidInjection.inject(this);

        // Super
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.dialog_preference_seekbar);
        onSetupContent();
    }

    @Override
    public void assignGlobalFields() {
        textTitle = findViewById(R.id.text_title);
        textDescription = findViewById(R.id.text_description);
        seekBar = findViewById(R.id.seek_bar);
        buttonReset = findViewById(R.id.button_reset);
        textValue = findViewById(R.id.text_value);
        buttonCancel = findViewById(R.id.button_cancel);
        buttonDone = findViewById(R.id.button_done);
    }

    @Override
    public void setupVisualElements() {
        textTitle.setBackgroundResource(R.drawable.bg_round_corners_outline);
    }

    @Override
    public void setupEventListeners() {
        seekBar.setOnSeekBarChangeListener(onSeekBarValueChanged());

        buttonReset.setOnClickListener(view -> {
            UXPreference.Value value = preferencesViewModel.getActiveValue().getValue();
            if (value != null) {
                UXPreference.Item item = value.getItem();
                int defaultValue = applyMinOffset(item.getDefault(), item);
                seekBar.setProgress(defaultValue);
                onSeekBarValueChanged().onValueChanged(defaultValue);
            }
        });

        buttonDone.setOnClickListener(view -> {
            UXPreference.Value value = preferencesViewModel.getActiveValue().getValue();
            if (value != null) {
                String stringValue = textValue.getText().toString().replaceAll("[^\\d]", "");
                value.setValue(Integer.parseInt(stringValue));
                preferencesViewModel.setActiveValue(value);
                preferencesViewModel.onPreferenceUpdated();
            }

            onBackPressed();
            finish();
        });

        buttonCancel.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
    }

    private SeekBarValueListener onSeekBarValueChanged() {
        return value -> {
            UXPreference.Value preference = preferencesViewModel.getActiveValue().getValue();
            if (preference == null) {
                return;
            }

            value += preference.getItem().getMin();
            int scale = preference.getItem().getScaleFactor();
            switch (scale) {
                case UXPreference.Constants.INT_SCALE:
                    this.textValue.setText(String.valueOf(value));
                    break;

                case UXPreference.Constants.PERCENT_SCALE:
                    this.textValue.setText(String.format(Locale.getDefault(), "%d%%", value));
                    break;

                case UXPreference.Constants.SECONDS_SCALE:
                    this.textValue.setText(String.format(Locale.getDefault(), "%ds", value));
                    break;

                case UXPreference.Constants.MINUTES_SCALE:
                    this.textValue.setText(String.format(Locale.getDefault(), "%dm", value));
                    break;

                default:
                    throw new UnsupportedOperationException("Unsupported Item Scale");
            }
        };
    }

    @Override
    public void setupViewModel() {
        super.setupViewModel();

        preferencesViewModel.getActiveValue().observe(this, this::onSelectionChanged);
    }

    private void onSelectionChanged(@Nullable UXPreference.Value value) {
        if (value != null) {
            UXPreference.Item item = value.getItem();
            seekBar.setMax(applyMinOffset(item.getMax(), item));
            seekBar.setProgress(applyMinOffset(value.getValue(),  item));
            textTitle.setText(item.name());
            textDescription.setText(item.getDescription());
        }
    }

    private int applyMinOffset(int value, UXPreference.Item item) {
        return value - item.getMin();
    }

    @Module
    public static class ActivityModule
            extends NavigationActivity.NavigationModule<PreferenceSeekBarDialog> {

        @Provides
        @NonNull
        PreferencesViewModel provideViewModel(PreferenceSeekBarDialog activity) {
            return ViewModelProviders.of(activity).get(PreferencesViewModel.class);
        }
    }

    interface SeekBarValueListener extends SeekBar.OnSeekBarChangeListener {

        void onValueChanged(int value);

        default void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
            onValueChanged(progress);
        }

        default void onStartTrackingTouch(SeekBar seekBar) {
        }

        default void onStopTrackingTouch(SeekBar seekBar) {
        }
    }
}
