package com.armandgray.shared.navigation;

import io.reactivex.annotations.Nullable;

interface Navigator {

    void observeDestinationActivity();

    <D extends TAAPDestination<?>> void goTo(@Nullable D destination);

    <D extends TAAPDestination<?>> Class<?> getDestinationClass(D destination);
}
