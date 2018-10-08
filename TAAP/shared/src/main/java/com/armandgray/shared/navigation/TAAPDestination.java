package com.armandgray.shared.navigation;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class TAAPDestination<T> {

    private final Class<T> destinationClass;

    protected TAAPDestination(Class<T> destinationClass) {
        this.destinationClass = destinationClass;
    }

    protected Class<T> getDestinationClass() {
        return destinationClass;
    }

    @Override
    public int hashCode() {
        return getDestinationClass().hashCode();
    }

    @Override
    public boolean equals(@Nullable Object that) {
        return that instanceof TAAPDestination
                && this.destinationClass == ((TAAPDestination) that).destinationClass;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Destination{%s}",
                destinationClass.getSimpleName());
    }
}
