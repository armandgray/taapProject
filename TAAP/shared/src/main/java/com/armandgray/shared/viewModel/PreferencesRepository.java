package com.armandgray.shared.viewModel;

import com.armandgray.shared.model.UXPreference;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

@Singleton
class PreferencesRepository {

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<UXPreference> selectedPreference = new MutableLiveData<>();
    private final MutableLiveData<UXPreference.Value> selectedValue = new MutableLiveData<>();
    private final PublishSubject<UXPreference> preferenceSubject = PublishSubject.create();

    @Inject
    PreferencesRepository() {
    }

    LiveData<UXPreference> getSelectedPreference() {
        return selectedPreference;
    }

    void setSelectedPreference(UXPreference preference) {
        this.selectedPreference.setValue(preference);
    }

    LiveData<UXPreference.Value> getSelectedValue() {
        return selectedValue;
    }

    void setSelectedValue(UXPreference.Value value) {
        this.selectedValue.setValue(value);
    }

    void addPreferenceConsumer(Consumer<UXPreference> consumer) {
        disposable.add(preferenceSubject.subscribe(consumer));
    }

    void onPreferenceUpdated() {
        if (selectedPreference.getValue() != null) {
            preferenceSubject.onNext(selectedPreference.getValue());
        }
    }
}
