package com.armandgray.taap.application;

import android.app.Application;

import com.armandgray.shared.application.TAAPAppComponent;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        ActivityBuilder.class,
        AppModule.class
})
interface AppComponent extends TAAPAppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder appModule(AppModule module);

        AppComponent build();
    }

    void inject(WearApplication application);
}
