package com.armandgray.taap.navigation;

import android.view.MenuItem;
import android.view.View;

import com.armandgray.shared.navigation.NavigationActivity;
import com.armandgray.shared.navigation.NavigationDrawerItem;
import com.armandgray.shared.viewModel.PreferencesViewModel;
import com.armandgray.taap.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.wear.widget.drawer.WearableActionDrawerView;
import androidx.wear.widget.drawer.WearableNavigationDrawerView;
import dagger.Module;
import dagger.Provides;

public abstract class WearNavigationActivity extends NavigationActivity implements
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
        drawerAdapter.updateItems(WearNavigationActivity.Defaults.getDefaults(this));
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
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    @Override
    public void onItemSelected(int position) {
        Destination<?> destination = drawerAdapter.getItemDestination(position);
        if (destination == Destination.SETTINGS) {

        }

        navigationViewModel.onNavigate(destination);
    }

    @Module
    public static abstract class NavigationModule<A extends WearNavigationActivity>
            extends NavigationActivity.NavigationModule<A> {

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
                WearNavigationActivity activity) {
            List<NavigationDrawerItem<Destination<?>>> actions = new ArrayList<>();
            actions.add(new NavigationDrawerItem<>(Destination.COURT,
                    activity.getDrawable(R.drawable.ic_dribbble_white_48dp)));
            actions.add(new NavigationDrawerItem<>(Destination.LOGS,
                    activity.getDrawable(R.drawable.ic_zig_zag_up_white_24dp)));
            actions.add(new NavigationDrawerItem<>(Destination.SETTINGS,
                    activity.getDrawable(R.drawable.ic_settings_white_24dp)));
            actions.add(new NavigationDrawerItem<>(Destination.ACTIVE_DRILL,
                    activity.getDrawable(R.drawable.ic_dumbbell_white_24dp)));

            moveCurrentToTop(actions, activity);
            return actions;
        }

        private static void moveCurrentToTop(List<NavigationDrawerItem<Destination<?>>> actions,
                                             WearNavigationActivity activity) {
            NavigationDrawerItem<?> genericItem = NavigationDrawerItem.getItem(
                    Destination.getDestination(activity.getClass()));
            if (genericItem != null) {
                NavigationDrawerItem<Destination<?>> item = actions.get(actions.indexOf(genericItem));
                actions.remove(item);
                actions.add(0, item);
            }
        }
    }
}
