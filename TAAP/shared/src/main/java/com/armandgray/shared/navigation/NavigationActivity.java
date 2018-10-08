package com.armandgray.shared.navigation;

import android.content.Intent;

import com.armandgray.shared.application.TAAPActivity;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.Nullable;

public abstract class NavigationActivity extends TAAPActivity implements Navigator {

    @Inject
    public NavigationViewModel navigationViewModel;

    @Override
    public void setupViewModel() {
        observeDestinationActivity();
    }

    @Override
    public void observeDestinationActivity() {
        navigationViewModel.getDestination().observe(this, this::goTo);
    }

    @Override
    public <D extends TAAPDestination<?>> void goTo(@Nullable D destination) {
        if (destination != null && destination.getDestinationClass() != this.getClass()) {
            startActivity(new Intent(this, destination.getDestinationClass()));
        }
    }

    @Override
    public <D extends TAAPDestination<?>> Class<?> getDestinationClass(D destination) {
        return destination.getDestinationClass();
    }

    @Module
    public static abstract class NavigationModule<A extends NavigationActivity> {

        @SuppressWarnings("WeakerAccess")
        @Provides
        @NonNull
        public NavigationViewModel provideNavigationViewModel(A activity) {
            return ViewModelProviders.of(activity).get(NavigationViewModel.class);
        }
    }
}
