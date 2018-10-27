package com.armandgray.shared.viewModel;

import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.application.TAAPViewModel;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import static com.armandgray.shared.location.LocationManager.Availability;

public class LocationViewModel extends TAAPViewModel {

    @Inject
    LocationRepository repository;

    private MutableLiveData<Availability> availabilityLiveData;

    LocationViewModel() {
        TAAPApplication.getAppComponent().inject(this);
    }

    public LiveData<Availability> getAvailability() {
        if (availabilityLiveData == null) {
            availabilityLiveData = new MutableLiveData<>();

            repository.getAvailability().subscribe(new ViewModelObserver<Availability>() {
                @Override
                public void onNext(Availability availability) {
                    availabilityLiveData.setValue(availability);
                }
            });
        }

        return availabilityLiveData;
    }
}
