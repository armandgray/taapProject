package com.armandgray.taap.ui;

import android.os.Build;
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
            UXPreference.Value value = preferencesViewModel.getSelectedValue().getValue();
            if (value != null) {
                int defaultValue = value.getItem().getDefault();
                seekBar.setProgress(defaultValue);
                textValue.setText(String.valueOf(defaultValue));
            }
        });

        buttonDone.setOnClickListener(view -> {
            UXPreference.Value value = preferencesViewModel.getSelectedValue().getValue();
            if (value != null) {
                value.setValue(Integer.parseInt(textValue.getText().toString().replace("%", "")));
                preferencesViewModel.setSelectedValue(value);
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
            UXPreference.Value preference = preferencesViewModel.getSelectedValue().getValue();
            if (preference == null) {
                return;
            }

            int scale = preference.getItem().getScaleFactor();
            switch (scale) {
                case UXPreference.Constants.INT_SCALE:
                    this.textValue.setText(String.valueOf(value));
                    break;

                case UXPreference.Constants.PERCENT_SCALE:
                    this.textValue.setText(String.format(Locale.getDefault(), "%d%%", value));
                    break;

                default:
                    throw new UnsupportedOperationException("Unsupported Item Scale");
            }
        };
    }

    @Override
    public void setupViewModel() {
        super.setupViewModel();

        preferencesViewModel.getSelectedValue().observe(this, this::onSelectionChanged);
    }

    private void onSelectionChanged(@Nullable UXPreference.Value value) {
        if (value != null) {
            UXPreference.Item item = value.getItem();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // TODO Find issue with min affecting max on seekBark
                seekBar.setMin(item.getMin());
                seekBar.setMax(item.getMax() + item.getMin());
            } else {
                seekBar.setMax(item.getMax());
            }

            seekBar.setProgress(value.getValue());
            textTitle.setText(item.name());
        }
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