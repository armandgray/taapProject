package com.armandgray.shared.viewModel;

import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.application.TAAPViewModel;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class PerformanceViewModel extends TAAPViewModel {

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

            repository.getActiveDrillObservable().subscribe(new ViewModelObserver<Drill>() {
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

            repository.getPerformanceObservable().subscribe(new ViewModelObserver<Performance>() {
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

            repository.getCompletionObservable().subscribe(new ViewModelObserver<Performance>() {
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
}
