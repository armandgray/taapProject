package com.armandgray.shared.permission;

import com.armandgray.shared.application.TAAPAppComponent;

import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.Annotation;

import io.reactivex.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class PermissionManagerTest {

    @Test
    public void testPermissionManager() {
        PermissionManager testManager = new PermissionManager() {
            @Override
            public Observable<Boolean> usePermission(DangerousPermission permission) {
                return null;
            }

            @Override
            public Observable<DangerousPermission> getPermissionRequestObservable() {
                return null;
            }

            @Override
            public void onResult(DangerousPermission permission) {

            }

            @Override
            public Observable<DangerousPermission> getRationaleRequestObservable() {
                return null;
            }

            @Override
            public void onRationaleResponded(boolean showRationale) {

            }

            @Override
            public void inject(TAAPAppComponent parentComponent) {

            }
        };

        Assert.assertThat(testManager, is(notNullValue()));
    }

    @Test
    public void testPermissionManager_DefinesInterfaceTag() {
        Assert.assertThat(PermissionManager.TAG, is(notNullValue()));
    }

    /**
     * Inner Class - Component
     */

    @SuppressWarnings("Convert2Lambda")
    @Test
    public void testPermissionManager_Component() {
        PermissionManager.Component component = new PermissionManager.Component() {
            @Override
            public void inject(PermissionManagerImpl manager) {
            }
        };

        PermissionManager.Component.Builder builder = new PermissionManager.Component.Builder() {
            @Override
            public PermissionManager.Component.Builder permissionManager(PermissionManager manager) {
                return null;
            }

            @Override
            public PermissionManager.Component.Builder managerModule(
                    PermissionManager.ManagerModule module) {
                return null;
            }

            @Override
            public PermissionManager.Component build() {
                return null;
            }
        };

        Assert.assertThat(component, is(notNullValue()));
        Assert.assertThat(builder, is(notNullValue()));
    }

    @Test
    public void testPermissionManager_Scope() {
        PermissionManager.PermissionScope scope = new PermissionManager.PermissionScope() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }
        };

        Assert.assertThat(scope, is(notNullValue()));
    }
}
