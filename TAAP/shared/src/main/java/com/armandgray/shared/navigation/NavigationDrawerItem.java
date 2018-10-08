package com.armandgray.shared.navigation;

import android.graphics.drawable.Drawable;

import java.util.HashSet;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;


public class NavigationDrawerItem<D extends TAAPDestination<?>> {

    @VisibleForTesting
    static final Set<NavigationDrawerItem> ITEMS = new HashSet<>();

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
        return String.format("NavigationDrawerItem{%s}", destination);
    }

    @Nullable
    public static <D extends TAAPDestination<?>> NavigationDrawerItem<?> getItem(D destination) {
        for (NavigationDrawerItem<?> item : ITEMS) {
            System.out.println(item + " , " + destination);
            if (item.destination.equals(destination)) {
                return item;
            }
        }

        return null;
    }
}
