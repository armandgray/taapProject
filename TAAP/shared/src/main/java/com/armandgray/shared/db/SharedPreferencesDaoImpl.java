package com.armandgray.shared.db;

import com.armandgray.shared.permission.DangerousPermission;

import java.util.Set;

import javax.inject.Inject;

import androidx.annotation.NonNull;

public class SharedPreferencesDaoImpl implements SharedPreferencesDao {

    @Inject
    SharedPreferencesWrapper sharedPreferencesWrapper;

    SharedPreferencesDaoImpl() {
        DatabaseManager.State.databaseComponent().inject(this);
    }

    @Override
    public boolean hasRequestedPermission(DangerousPermission permission) {
        return sharedPreferencesWrapper.getRequestedPermissions().contains(permission.getKey());
    }

    @Override
    public void registerRequestedPermission(DangerousPermission permission) {
        Set<String> permissions = sharedPreferencesWrapper.getRequestedPermissions();
        if (permissions.contains(permission.getKey())) {
            return;
        }

        permissions.add(permission.getKey());
        sharedPreferencesWrapper.setRequestedPermissions(permissions);
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }
}
