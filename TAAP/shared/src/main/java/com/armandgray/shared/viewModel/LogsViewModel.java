package com.armandgray.shared.viewModel;

import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.application.TAAPViewModel;
import com.armandgray.shared.model.WorkoutInfo;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class LogsViewModel extends TAAPViewModel {

    @Inject
    LogsRepository repository;

    private MutableLiveData<List<WorkoutInfo>> recentWorkoutsLiveData;

    LogsViewModel() {
        TAAPApplication.getAppComponent().inject(this);
    }

    public LiveData<List<WorkoutInfo>> getRecentWorkouts() {
        if (recentWorkoutsLiveData == null) {
            recentWorkoutsLiveData = new MutableLiveData<>();

            repository.getRecentWorkoutObservable()
                    .subscribe(new ViewModelObserver<List<WorkoutInfo>>() {
                        @Override
                        public void onNext(List<WorkoutInfo> logs) {
                            recentWorkoutsLiveData.setValue(logs);
                        }
                    });
        }

        return recentWorkoutsLiveData;
    }
}
