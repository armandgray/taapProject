package com.armandgray.taap.navigation;

import android.support.annotation.NonNull;

import com.armandgray.shared.navigation.TAAPDestination;
import com.armandgray.taap.activity.CourtActivity;
import com.armandgray.taap.activity.ActiveDrillActivity;
import com.armandgray.taap.activity.DrillPickerActivity;

import java.util.HashSet;
import java.util.Set;

public class Destination<T> extends TAAPDestination<T> {

    public static final Destination<DrillPickerActivity> DRILL_PICKER;

    static final Destination<ActiveDrillActivity> ACTIVE_DRILL;
    static final Destination<ActiveDrillActivity> TARGETS;
    static final Destination<CourtActivity> COURT;
    static final Destination<ActiveDrillActivity> LOGS;
    static final Destination<ActiveDrillActivity> SETTINGS;

    private static final Set<Destination> DESTINATIONS;

    static {
        DESTINATIONS = new HashSet<>();
        DRILL_PICKER = new Destination<>(DrillPickerActivity.class);
        ACTIVE_DRILL = new Destination<>(ActiveDrillActivity.class);
        TARGETS = new Destination<>(ActiveDrillActivity.class);
        COURT = new Destination<>(CourtActivity.class);
        LOGS = new Destination<>(ActiveDrillActivity.class);
        SETTINGS = new Destination<>(ActiveDrillActivity.class);
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
