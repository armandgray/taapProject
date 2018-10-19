package com.armandgray.shared.viewModel;

import android.util.Log;

import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class PerformanceViewModel extends ViewModel {

    private static final String TAG = "PERFORMANCE_VIEW_MODEL";

    @VisibleForTesting
    final CompositeDisposable disposables = new CompositeDisposable();

    private MutableLiveData<Drill> activeDrillLiveData;
    private MutableLiveData<Performance> performanceLiveData;
    private MutableLiveData<Performance> completionLiveData;

    @Inject
    DrillRepository repository;

    PerformanceViewModel() {
        TAAPApplication.getAppComponent().inject(this);
    }

    public LiveData<Drill> getActiveDrill() {
        if (activeDrillLiveData == null) {
            activeDrillLiveData = new MutableLiveData<>();

            repository.getActiveDrillObservable().subscribe(new PerformanceObserver<Drill>() {
                @Override
                public void onNext(@NonNull Drill drill) {
                    activeDrillLiveData.setValue(drill);
                }
            });
        }

        return activeDrillLiveData;
    }

    public LiveData<Performance> getPerformance() {
        if (performanceLiveData == null) {
            performanceLiveData = new MutableLiveData<>();

            repository.getPerformanceObservable().subscribe(new PerformanceObserver<Performance>() {
                @Override
                public void onNext(@NonNull Performance performance) {
                    performanceLiveData.setValue(performance);
                }
            });
        }

        return performanceLiveData;
    }

    public LiveData<Performance> getCompletionObserver() {
        if (completionLiveData == null) {
            completionLiveData = new MutableLiveData<>();

            repository.getCompletionObservable().subscribe(new PerformanceObserver<Performance>() {
                @Override
                public void onNext(@NonNull Performance performance) {
                    completionLiveData.setValue(performance);
                }
            });
        }

        return completionLiveData;
    }

    public void onPlusClick() {
        repository.addMake();
    }

    public void onMinusClick() {
        repository.addMiss();
    }

    public void onSingleInputClick() {
        repository.addMake();
    }

    public void onDoubleInputClick() {
        repository.addMiss();
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

    abstract class PerformanceObserver<T> implements Observer<T> {

        @Override
        public void onSubscribe(@NonNull Disposable d) {
            PerformanceViewModel.this.disposables.add(d);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            Log.e(TAG, "PerformanceObserver: onError: " + e.getMessage());
        }

        @Override
        public void onComplete() {
        }
    }
}
