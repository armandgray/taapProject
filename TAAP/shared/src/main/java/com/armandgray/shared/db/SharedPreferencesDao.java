package com.armandgray.shared.db;

import com.armandgray.shared.permission.DangerousPermission;

public interface SharedPreferencesDao {

    boolean hasRequestedPermission(DangerousPermission permission);

    void registerRequestedPermission(DangerousPermission permission);
}
