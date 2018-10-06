package com.armandgray.shared.navigation;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashSet;
import java.util.Set;


public class NavigationDrawerItem<D extends TAAPDestination<?>> {

    private static final Set<NavigationDrawerItem> ITEMS = new HashSet<>();

    @Nullable
    private final Drawable drawable;
    private final D destination;

    public NavigationDrawerItem(@NonNull D destination, @Nullable Drawable drawable) {
        this.drawable = drawable;
        this.destination = destination;
        ITEMS.add(this);
    }

    @Nullable
    public Drawable getDrawable() {
        return drawable;
    }

    public D getDestination() {
        return destination;
    }

    @Override
    public int hashCode() {
        return destination.getDestinationClass().hashCode();
    }

    @Override
    public boolean equals(@Nullable Object that) {
        return that instanceof NavigationDrawerItem
                && this.destination == ((NavigationDrawerItem) that).destination;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("NavAction{%s}", destination);
    }

    @Nullable
    public static <D extends TAAPDestination<?>> NavigationDrawerItem<?> getAction(D destination) {
        for (NavigationDrawerItem<?> action : ITEMS) {
            if (action.destination == destination) {
                return action;
            }
        }

        return null;
    }
}
