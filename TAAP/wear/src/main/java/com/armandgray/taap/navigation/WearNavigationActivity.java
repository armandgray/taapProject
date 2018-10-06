package com.armandgray.taap.navigation;

import android.support.wear.widget.drawer.WearableActionDrawerView;
import android.support.wear.widget.drawer.WearableNavigationDrawerView;
import android.view.MenuItem;

import com.armandgray.shared.navigation.NavigationActivity;
import com.armandgray.shared.navigation.NavigationDrawerItem;
import com.armandgray.shared.navigation.NavigationViewModel;
import com.armandgray.taap.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.Module;

public abstract class WearNavigationActivity extends NavigationActivity implements
        MenuItem.OnMenuItemClickListener,
        WearableNavigationDrawerView.OnItemSelectedListener {

    @Inject
    protected NavigationViewModel navigationViewModel;

    @Inject
    NavigationDrawerAdapter drawerAdapter;

    private WearableNavigationDrawerView wearableNavigationDrawer;
    private WearableActionDrawerView wearableActionDrawer;

    @Override
    public void assignGlobalFields() {
        wearableNavigationDrawer = findViewById(R.id.top_navigation_drawer);
        wearableActionDrawer = findViewById(R.id.bottom_action_drawer);
    }

    @Override
    public void setupVisualElements() {
        drawerAdapter.updateItems(WearNavigationDefaults.getNavigationDrawerActions(this));
        wearableNavigationDrawer.setAdapter(drawerAdapter);
        wearableNavigationDrawer.setCurrentItem(0, true);
        wearableNavigationDrawer.getController().peekDrawer();
        wearableNavigationDrawer.addOnItemSelectedListener(this);

        wearableActionDrawer.getController().peekDrawer();
        wearableActionDrawer.setPeekOnScrollDownEnabled(true);
        wearableActionDrawer.setOnMenuItemClickListener(this);
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
        switch (menuItem.getItemId()) {
            case R.id.action_targets:
                super.navigationViewModel.onNavigate(Destination.TARGETS);
                return true;
        }

        return false;
    }

    @Override
    public void onItemSelected(int position) {
        super.navigationViewModel.onNavigate(drawerAdapter.getItemDestination(position));
    }

    @Module
    public static abstract class NavigationModule<A extends WearNavigationActivity>
            extends NavigationActivity.NavigationModule<A> {
    }

    static final class WearNavigationDefaults {

        private WearNavigationDefaults() {
        }

        static List<NavigationDrawerItem<Destination<?>>> getNavigationDrawerActions(
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
            NavigationDrawerItem<?> genericItem = NavigationDrawerItem.getAction(
                    Destination.getDestination(activity.getClass()));
            if (genericItem != null) {
                NavigationDrawerItem<Destination<?>> item = actions.get(actions.indexOf(genericItem));
                actions.remove(item);
                actions.add(0, item);
            }
        }
    }
}
