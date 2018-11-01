package com.armandgray.shared.voice;

import com.armandgray.shared.application.TAAPAppComponent;
import com.armandgray.shared.permission.DangerousPermission;
import com.armandgray.shared.permission.PermissionManager;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Observable;

public class VoiceManagerImpl implements VoiceManager {

    @Inject
    ClapRecognizer clapRecognizer;

    @Inject
    PermissionManager permissionManager;

    public VoiceManagerImpl() {
    }

    @Override
    public void inject(TAAPAppComponent appComponent) {
        Component component = appComponent.voiceBuilder()
                .voiceManager(this)
                .managerModule(new ManagerModule())
                .build();

        component.inject(this);
    }

    @Override
    public Observable<VoiceEvent> getClapObservable() {
        boolean hasPermission = permissionManager.hasPermission(DangerousPermission.MICROPHONE);
        return !hasPermission ? Observable.just(VoiceEvent.MISSING_PERMISSION) : clapObservable();
    }

    private Observable<VoiceEvent> clapObservable() {
        return clapRecognizer.getEventSubject()
                .doOnObserved(clapRecognizer::registerListener)
                .doOnUnObserved(() -> clapRecognizer.unregisterListener())
                .toObservable();
    }

    @Override
    public Observable<VoiceEvent> getCallOutObservable() {
        boolean hasPermission = permissionManager.hasPermission(DangerousPermission.MICROPHONE);
        // TODO implement
        return !hasPermission ? Observable.just(VoiceEvent.MISSING_PERMISSION) : Observable.just(VoiceEvent.NONE);
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }
}
