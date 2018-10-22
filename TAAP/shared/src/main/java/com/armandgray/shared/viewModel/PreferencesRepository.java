package com.armandgray.shared.viewModel;

import com.armandgray.shared.application.TAAPRepository;
import com.armandgray.shared.model.UXPreference;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.VisibleForTesting;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

@Singleton
class PreferencesRepository extends TAAPRepository {

    @VisibleForTesting
    final BehaviorSubject<UXPreference> activePreferenceSubject = BehaviorSubject.create();

    @VisibleForTesting
    final BehaviorSubject<UXPreference.Value> activeValueSubject = BehaviorSubject.create();

    @VisibleForTesting
    final PublishSubject<UXPreference> preferenceUpdateSubject = PublishSubject.create();

    @Inject
    PreferencesRepository() {
    }

    Observable<UXPreference> getActivePreferenceObservable() {
        return activePreferenceSubject;
    }

    void setActivePreference(UXPreference preference) {
        this.activePreferenceSubject.onNext(preference);
    }

    Observable<UXPreference.Value> getActiveValueObservable() {
        return activeValueSubject;
    }

    void setActiveValue(UXPreference.Value value) {
        this.activeValueSubject.onNext(value);
    }

    Observable<UXPreference> getPreferenceUpdateObservable() {
        return preferenceUpdateSubject;
    }

    void onPreferenceUpdated() {
        if (activePreferenceSubject.getValue() != null) {
            preferenceUpdateSubject.onNext(activePreferenceSubject.getValue());
        }
    }
}
