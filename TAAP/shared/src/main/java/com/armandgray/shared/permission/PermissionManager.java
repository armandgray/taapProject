package com.armandgray.shared.permission;

import com.armandgray.shared.application.TAAPAppComponent;
import com.armandgray.shared.helpers.StringHelper;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

import androidx.annotation.Nullable;
import dagger.BindsInstance;
import dagger.Module;
import dagger.Subcomponent;
import io.reactivex.Observable;

/**
 * @see PermissionManagerImpl
 */
public interface PermissionManager
        extends TAAPAppComponent.InjectableSubComponent<TAAPAppComponent> {

    String TAG = StringHelper.toLogTag(PermissionManager.class.getSimpleName());

    Observable<Boolean> usePermission(DangerousPermission permission);

    Observable<DangerousPermission> getPermissionRequestObservable();

    void onResult(DangerousPermission permission);

    Observable<DangerousPermission> getRationaleRequestObservable();

    void onRationaleResponded(boolean showRationale);

    interface PermissionResolver {

        void onRequestPermission(DangerousPermission permission);

        void onRequestPermissionWithRationale(@Nullable DangerousPermission permission);

        default void showRationale(DangerousPermission permission, boolean wasRevoked) {

        }
    }

    @PermissionScope
    @Subcomponent(modules = ManagerModule.class)
    interface Component {

        @Subcomponent.Builder
        interface Builder {

            @BindsInstance
            Component.Builder permissionManager(PermissionManager manager);

            @BindsInstance
            Component.Builder managerModule(ManagerModule module);

            Component build();
        }

        void inject(PermissionManagerImpl manager);
    }

    @Scope
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface PermissionScope {
    }

    @Module
    class ManagerModule {
    }
}
