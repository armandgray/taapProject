package com.armandgray.shared.permission;

import android.content.Intent;

import com.armandgray.shared.application.UIComponent;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import dagger.Module;
import dagger.Provides;

import static com.armandgray.shared.permission.DangerousPermission.*;
import static com.armandgray.shared.permission.PermissionManager.PermissionResolver;

public abstract class PermissionActivity extends AppCompatActivity
        implements UIComponent, PermissionResolver {

    public static final String PERMISSION_KEY = "PERMISSION_KEY";
    public static final String REVOKED_KEY = "REVOKED_KEY";

    @Inject
    protected PermissionViewModel permissionViewModel;

    @Override
    protected void onResume() {
        super.onResume();

        permissionViewModel.setResumed(true);
    }

    @Override
    protected void onPause() {
        super.onPause();

        permissionViewModel.setResumed(false);
    }

    @Override
    public void setupViewModel() {
        permissionViewModel.getRationaleRequest().observe(this, this::onRequestPermissionWithRationale);
        permissionViewModel.getPermissionRequest().observe(this, this::onRequestPermission);
    }

    @Override
    public void onRequestPermissionWithRationale(@Nullable DangerousPermission permission) {
        if (permission != null) {
            showRationale(permission, !ActivityCompat.shouldShowRequestPermissionRationale(
                    this, permission.getKey()));
        }
    }

    @Override
    public void onRequestPermission(@Nullable DangerousPermission permission) {
        if (permission != null) {
            requestPermissions(new String[]{permission.getKey()}, permission.getCode());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        DangerousPermission permission = getPermission(requestCode);
        if (permission != ERROR) {
            permissionViewModel.onResult(permission);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        permissionViewModel.setResumed(true);

        DangerousPermission permission = getPermission(requestCode);
        if (permission != ERROR) {
            permissionViewModel.onRationaleResponded(resultCode == RESULT_OK);
        }
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }

    @Module
    public static abstract class PermissionModule<A extends PermissionActivity> {

        @SuppressWarnings("WeakerAccess")
        @Provides
        @NonNull
        protected PermissionViewModel providePermissionViewModel(A activity) {
            return ViewModelProviders.of(activity).get(PermissionViewModel.class);
        }
    }
}
