package com.armandgray.taap.application;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import com.armandgray.shared.navigation.NavigationActivity;
import com.armandgray.shared.navigation.NavigationDrawerItem;
import com.armandgray.shared.permission.DangerousPermission;
import com.armandgray.shared.viewModel.PreferencesViewModel;
import com.armandgray.taap.R;
import com.armandgray.taap.navigation.Destination;
import com.armandgray.taap.navigation.NavigationDrawerAdapter;
import com.armandgray.taap.permission.PermissionRationaleDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.wear.widget.drawer.WearableActionDrawerView;
import androidx.wear.widget.drawer.WearableNavigationDrawerView;
import dagger.Module;
import dagger.Provides;

public abstract class WearDelegateActivity extends NavigationActivity implements
        MenuItem.OnMenuItemClickListener,
        WearableNavigationDrawerView.OnItemSelectedListener {

    @Inject
    protected PreferencesViewModel preferencesViewModel;

    protected WearableActionDrawerView wearableActionDrawer;

    @Inject
    NavigationDrawerAdapter drawerAdapter;

    private WearableNavigationDrawerView wearableNavigationDrawer;

    @Override
    public void assignGlobalFields() {
        wearableNavigationDrawer = findViewById(R.id.top_navigation_drawer);
        wearableActionDrawer = findViewById(R.id.bottom_action_drawer);
    }

    @Override
    public void setupVisualElements() {
        drawerAdapter.updateItems(WearDelegateActivity.Defaults.getDefaults(this));
        wearableNavigationDrawer.setAdapter(drawerAdapter);
        wearableNavigationDrawer.setCurrentItem(0, true);
        wearableNavigationDrawer.getController().peekDrawer();
        wearableNavigationDrawer.addOnItemSelectedListener(this);

        wearableActionDrawer.setPeekOnScrollDownEnabled(true);
        wearableActionDrawer.setOnMenuItemClickListener(this);
        wearableActionDrawer.setVisibility(View.GONE);
    }

    @Override
    public void setupEventListeners() {
    }

    @Override
    public void setupViewModel() {
        super.setupViewModel();
    }

    @Override
    public void showRationale(DangerousPermission permission, boolean wasRevoked) {
        Intent intent = new Intent(this, PermissionRationaleDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(PERMISSION_KEY, permission);
        intent.putExtra(REVOKED_KEY, wasRevoked);
        startActivityForResult(intent, permission.getCode());
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    @Override
    public void onItemSelected(int position) {
        navigationViewModel.onNavigate(drawerAdapter.getItemDestination(position));
    }

    @Module
    public static abstract class NavigationModule<A extends WearDelegateActivity>
            extends NavigationActivity.NavigationModule<A> {

        @SuppressWarnings("WeakerAccess")
        @Provides
        @NonNull
        protected PreferencesViewModel providePreferencesViewModel(A activity) {
            return ViewModelProviders.of(activity).get(PreferencesViewModel.class);
        }
    }

    static final class Defaults {

        private Defaults() {
            // Helper
        }

        static List<NavigationDrawerItem<Destination<?>>> getDefaults(
                WearDelegateActivity activity) {
            List<NavigationDrawerItem<Destination<?>>> actions = new ArrayList<>();
            actions.add(new NavigationDrawerItem<>(Destination.COURT,
                    activity.getDrawable(R.drawable.ic_dribbble_white_48dp)));
            actions.add(new NavigationDrawerItem<>(Destination.LOGS,
                    activity.getDrawable(R.drawable.ic_zig_zag_up_white_24dp)));
            actions.add(new NavigationDrawerItem<>(Destination.SETTINGS,
                    activity.getDrawable(R.drawable.ic_settings_white_24dp)));
            actions.add(new NavigationDrawerItem<>(Destination.ACTIVE_DRILL,
                    activity.getDrawable(R.drawable.ic_dumbbell_white_24dp)));
            actions.add(new NavigationDrawerItem<>(Destination.LOCATION,
                    activity.getDrawable(R.drawable.ic_location_white_24dp)));

            return actions;
        }
    }
}
