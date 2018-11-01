package com.armandgray.taap.application;

import android.content.Intent;

import com.armandgray.shared.navigation.NavigationActivity;
import com.armandgray.shared.permission.DangerousPermission;
import com.armandgray.taap.permission.PermissionRationaleDialog;

public abstract class WearDelegateDialog extends NavigationActivity {

    @Override
    public void showRationale(DangerousPermission permission, boolean wasRevoked) {
        Intent intent = new Intent(this, PermissionRationaleDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(PERMISSION_KEY, permission);
        intent.putExtra(REVOKED_KEY, wasRevoked);
        startActivityForResult(intent, permission.getCode());
    }
}
