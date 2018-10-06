package com.armandgray.shared.navigation;

import androidx.annotation.NonNull;

import java.util.Locale;

public abstract class TAAPDestination<T> {

    private final Class<T> destinationClass;

    protected TAAPDestination(Class<T> destinationClass) {
        this.destinationClass = destinationClass;
    }

    protected Class<T> getDestinationClass() {
        return destinationClass;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Destination{%s}",
                destinationClass.getSimpleName());
    }
}
