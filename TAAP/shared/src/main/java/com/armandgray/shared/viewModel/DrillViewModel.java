package com.armandgray.shared.viewModel;

import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.application.TAAPViewModel;
import com.armandgray.shared.model.Drill;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class DrillViewModel extends TAAPViewModel {

    private MutableLiveData<List<Drill>> drillsLiveData;
    private MutableLiveData<Drill> activeDrillLiveData;

    @Inject
    DrillRepository repository;

    DrillViewModel() {
        TAAPApplication.getAppComponent().inject(this);
    }

    public LiveData<List<Drill>> getDrills() {
        if (drillsLiveData == null) {
            drillsLiveData = new MutableLiveData<>();

            repository.getDrillsObservable().subscribe(new ViewModelObserver<List<Drill>>() {
                @Override
                public void onNext(@NonNull List<Drill> drills) {
                    if (drills.size() != 0) {
                        drillsLiveData.setValue(drills);
                    }
                }
            });
        }

        return drillsLiveData;
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

    public void onDrillSelected(Drill drill) {
        repository.setActiveDrill(drill);
    }
}
