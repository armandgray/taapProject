package com.armandgray.shared.voice;

import com.armandgray.shared.application.TAAPAppComponent;
import com.armandgray.shared.helpers.StringHelper;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

import dagger.BindsInstance;
import dagger.Module;
import dagger.Subcomponent;
import io.reactivex.Observable;

/**
 * @see VoiceManagerImpl
 */
public interface VoiceManager
        extends TAAPAppComponent.InjectableSubComponent<TAAPAppComponent> {

    String TAG = StringHelper.toLogTag(VoiceManager.class.getSimpleName());

    Observable<VoiceEvent> getClapObservable();

    Observable<VoiceEvent> getCallOutObservable();

    @VoiceScope
    @Subcomponent(modules = ManagerModule.class)
    interface Component {

        @Subcomponent.Builder
        interface Builder {

            @BindsInstance
            Component.Builder voiceManager(VoiceManager manager);

            @BindsInstance
            Component.Builder managerModule(ManagerModule module);

            Component build();
        }

        void inject(VoiceManagerImpl manager);
    }

    @Scope
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface VoiceScope {
    }

    @Module
    class ManagerModule {
    }
}
