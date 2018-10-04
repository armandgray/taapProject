package com.armandgray.taap.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.wear.widget.drawer.WearableNavigationDrawerView;

import com.armandgray.taap.R;

import javax.inject.Inject;

class ActiveDrillDrawerAdapter
        extends WearableNavigationDrawerView.WearableNavigationDrawerAdapter {

    private final DrawerMenuItem[] items;

    @Inject
    ActiveDrillDrawerAdapter(Context context) {
        this.items = DrawerMenuItem.getItems(context);
    }

    @Override
    public CharSequence getItemText(int position) {
        return "";
    }

    @Override
    public Drawable getItemDrawable(int position) {
        return items[position].drawable;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    static class DrawerMenuItem {

        private final Drawable drawable;

        DrawerMenuItem(Drawable drawable) {
            this.drawable = drawable;
        }

        static DrawerMenuItem[] getItems(Context context) {
            DrawerMenuItem[] items = new DrawerMenuItem[3];
            items[0] = new DrawerMenuItem(context.getDrawable(R.drawable.ic_dribbble_white_48dp));
            items[1] = new DrawerMenuItem(context.getDrawable(R.drawable.ic_zig_zag_up_white_24dp));
            items[2] = new DrawerMenuItem(context.getDrawable(R.drawable.ic_settings_white_24dp));
            return items;
        }
    }
}
