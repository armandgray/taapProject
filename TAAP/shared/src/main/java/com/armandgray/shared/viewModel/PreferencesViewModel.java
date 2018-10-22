package com.armandgray.shared.viewModel;

import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.application.TAAPViewModel;
import com.armandgray.shared.model.UXPreference;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class PreferencesViewModel extends TAAPViewModel {

    @Inject
    PreferencesRepository repository;

    private MutableLiveData<UXPreference> activePreferenceLiveData;
    private MutableLiveData<UXPreference.Value> activeValueLiveData;

    PreferencesViewModel() {
        TAAPApplication.getAppComponent().inject(this);
    }

    public LiveData<UXPreference> getActivePreference() {
        if (activePreferenceLiveData == null) {
            activePreferenceLiveData = new MutableLiveData<>();

            repository.getActivePreferenceObservable()
                    .subscribe(new ViewModelObserver<UXPreference>() {
                        @Override
                        public void onNext(UXPreference preference) {
                            activePreferenceLiveData.setValue(preference);
                        }
                    });
        }

        return activePreferenceLiveData;
    }

    public void setActivePreference(UXPreference preference) {
        repository.setActivePreference(preference);
    }

    public LiveData<UXPreference.Value> getActiveValue() {
        if (activeValueLiveData == null) {
            activeValueLiveData = new MutableLiveData<>();

            repository.getActiveValueObservable()
                    .subscribe(new ViewModelObserver<UXPreference.Value>() {
                        @Override
                        public void onNext(UXPreference.Value value) {
                            activeValueLiveData.setValue(value);
                        }
                    });
        }

        return activeValueLiveData;
    }

    public void setActiveValue(UXPreference.Value value) {
        repository.setActiveValue(value);
    }

    public void onPreferenceUpdated() {
        repository.onPreferenceUpdated();
    }
}
