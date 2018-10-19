package com.armandgray.shared.viewModel;

import android.util.Log;

import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.model.UXPreference;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class PreferencesViewModel extends ViewModel {

    private static final String TAG = "PREFERENCES_VIEW_MODEL";

    @Inject
    PreferencesRepository repository;

    @VisibleForTesting
    final CompositeDisposable disposables = new CompositeDisposable();

    private MutableLiveData<UXPreference> activePreferenceLiveData;
    private MutableLiveData<UXPreference.Value> activeValueLiveData;

    PreferencesViewModel() {
        TAAPApplication.getAppComponent().inject(this);
    }

    public LiveData<UXPreference> getActivePreference() {
        if (activePreferenceLiveData == null) {
            activePreferenceLiveData = new MutableLiveData<>();

            repository.getActivePreferenceObservable()
                    .subscribe(new PreferencesObserver<UXPreference>() {
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
                    .subscribe(new PreferencesObserver<UXPreference.Value>() {
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

    @Override
    protected void onCleared() {
        disposables.dispose();
        disposables.clear();
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }

    abstract class PreferencesObserver<T> implements Observer<T> {

        @Override
        public void onSubscribe(@NonNull Disposable d) {
            PreferencesViewModel.this.disposables.add(d);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            Log.e(TAG, "PreferencesObserver: onError: " + e.getMessage());
        }

        @Override
        public void onComplete() {
        }
    }
}
