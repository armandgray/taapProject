package com.armandgray.taap.navigation;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.wear.widget.drawer.WearableNavigationDrawerView;

import com.armandgray.shared.navigation.NavigationDrawerItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

class NavigationDrawerAdapter
        extends WearableNavigationDrawerView.WearableNavigationDrawerAdapter {

    private final List<NavigationDrawerItem<Destination<?>>> items = new ArrayList<>();

    @Inject
    NavigationDrawerAdapter() {
    }

    @Override
    public CharSequence getItemText(int position) {
        return "";
    }

    @Nullable
    @Override
    public Drawable getItemDrawable(int position) {
        return items.get(position).getDrawable();
    }

    Destination<?> getItemDestination(int position) {
        return items.get(position).getDestination();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    void updateItems(@NonNull List<NavigationDrawerItem<Destination<?>>> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }
}
