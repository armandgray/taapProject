package com.armandgray.shared.db;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import androidx.annotation.VisibleForTesting;

/**
 * @see SharedPreferencesDaoImpl
 */
@DatabaseManager.DatabaseScope
class SharedPreferencesWrapper {

    /**
     * Shared Preference Keys
     */
    @VisibleForTesting
    static final String REQUESTED_PERMISSIONS = "REQUESTED_PERMISSIONS";

    private static final String PREFERENCES_NAME = "TAAP_SHARED_PREFERENCES";

    private final SharedPreferences sharedPreferences;

    @Inject
    SharedPreferencesWrapper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    Set<String> getRequestedPermissions() {
        return sharedPreferences.getStringSet(REQUESTED_PERMISSIONS, new HashSet<>());
    }

    void setRequestedPermissions(Set<String> permissions) {
        sharedPreferences.edit().putStringSet(REQUESTED_PERMISSIONS, permissions).apply();
    }
}
