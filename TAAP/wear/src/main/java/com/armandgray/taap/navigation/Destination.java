package com.armandgray.taap.navigation;

import com.armandgray.shared.navigation.TAAPDestination;
import com.armandgray.taap.activity.ActiveDrillActivity;
import com.armandgray.taap.activity.CourtActivity;
import com.armandgray.taap.ui.DrillPickerDialog;
import com.armandgray.taap.activity.LogsActivity;
import com.armandgray.taap.ui.PreferenceSeekBarDialog;
import com.armandgray.taap.ui.PreferencesDialog;
import com.armandgray.taap.activity.SettingsActivity;

import java.util.HashSet;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

public class Destination<T> extends TAAPDestination<T> {

    public static final Destination<DrillPickerDialog> DRILL_PICKER_DIALOG;
    public static final Destination<PreferencesDialog> PREFERENCES_DIALOG;
    public static final Destination<PreferenceSeekBarDialog> SEEK_BAR_DIALOG;

    static final Destination<ActiveDrillActivity> ACTIVE_DRILL;
    static final Destination<CourtActivity> COURT;
    static final Destination<LogsActivity> LOGS;
    static final Destination<SettingsActivity> SETTINGS;

    @VisibleForTesting
    static final Set<Destination> DESTINATIONS;

    static {
        DESTINATIONS = new HashSet<>();
        DRILL_PICKER_DIALOG = new Destination<>(DrillPickerDialog.class);
        ACTIVE_DRILL = new Destination<>(ActiveDrillActivity.class);
        PREFERENCES_DIALOG = new Destination<>(PreferencesDialog.class);
        SEEK_BAR_DIALOG = new Destination<>(PreferenceSeekBarDialog.class);
        COURT = new Destination<>(CourtActivity.class);
        LOGS = new Destination<>(LogsActivity.class);
        SETTINGS = new Destination<>(SettingsActivity.class);
    }

    private Destination(Class<T> destinationClass) {
        super(destinationClass);

        DESTINATIONS.add(this);
    }

    @NonNull
    static Destination<?> getDestination(Class<?> aClass) {
        for (Destination destination : DESTINATIONS) {
            if (destination.getDestinationClass() == aClass) {
                return destination;
            }
        }

        throw new IllegalStateException("Requested Undefined Destination");
    }
}
