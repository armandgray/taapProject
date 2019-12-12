package com.armandgray.shared.api;

import com.armandgray.shared.application.TAAPAppComponent;
import com.armandgray.shared.helpers.StringHelper;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

import dagger.BindsInstance;
import dagger.Module;
import dagger.Subcomponent;

/**
 * @see NetworkManagerImpl
 */
public interface NetworkManager
        extends TAAPAppComponent.InjectableSubComponent<TAAPAppComponent> {

    String TAG = StringHelper.toLogTag(NetworkManager.class.getSimpleName());

    ApiClient.TAAPApiService apiService();

    @NetworkScope
    @Subcomponent(modules = ManagerModule.class)
    interface Component {

        @Subcomponent.Builder
        interface Builder {

            @BindsInstance
            Component.Builder networkManager(NetworkManager manager);

            @BindsInstance
            Component.Builder managerModule(ManagerModule module);

            Component build();
        }

        void inject(NetworkManagerImpl manager);
    }

    @Scope
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface NetworkScope {
    }

    @Module
    class ManagerModule {
    }
}
