package com.armandgray.shared.voice;

import com.armandgray.shared.application.TAAPAppComponent;

import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.Annotation;

import io.reactivex.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class VoiceManagerTest {

    @Test
    public void testVoiceManager() {
        VoiceManager testManager = new VoiceManager() {
            @Override
            public void inject(TAAPAppComponent parentComponent) {

            }

            @Override
            public Observable<VoiceEvent> getClapObservable() {
                return null;
            }

            @Override
            public Observable<VoiceEvent> getCallOutObservable() {
                return null;
            }
        };

        Assert.assertThat(testManager, is(notNullValue()));
    }

    @Test
    public void testVoiceManager_DefinesInterfaceTag() {
        Assert.assertThat(VoiceManager.TAG, is(notNullValue()));
    }

    /**
     * Inner Class - Component
     */

    @SuppressWarnings("Convert2Lambda")
    @Test
    public void testVoiceManager_Component() {
        VoiceManager.Component component = new VoiceManager.Component() {
            @Override
            public void inject(VoiceManagerImpl manager) {
            }
        };

        VoiceManager.Component.Builder builder = new VoiceManager.Component.Builder() {
            @Override
            public VoiceManager.Component.Builder voiceManager(VoiceManager manager) {
                return null;
            }

            @Override
            public VoiceManager.Component.Builder managerModule(
                    VoiceManager.ManagerModule module) {
                return null;
            }

            @Override
            public VoiceManager.Component build() {
                return null;
            }
        };

        Assert.assertThat(component, is(notNullValue()));
        Assert.assertThat(builder, is(notNullValue()));
    }

    @Test
    public void testVoiceManager_Scope() {
        VoiceManager.VoiceScope scope = new VoiceManager.VoiceScope() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }
        };

        Assert.assertThat(scope, is(notNullValue()));
    }

    /**
     * Inner Class - ManagerModule
     */

    @Test
    public void testStub() {
    }
}
