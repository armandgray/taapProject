package com.armandgray.shared.api;

import com.armandgray.shared.application.TAAPAppComponent;

import javax.inject.Inject;

import androidx.annotation.NonNull;

public class NetworkManagerImpl implements NetworkManager {

    @Inject
    ApiClient apiClient;

    public NetworkManagerImpl() {
    }

    @Override
    public void inject(TAAPAppComponent appComponent) {
        Component component = appComponent.networkBuilder()
                .networkManager(this)
                .managerModule(new ManagerModule())
                .build();

        component.inject(this);
    }

    @Override
    public ApiClient.TAAPApiService apiService() {
        return apiClient.taapApi();
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }
}
