package com.armandgray.shared.navigation;

import com.armandgray.shared.application.TAAPViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class NavigationViewModel extends TAAPViewModel {

    private MutableLiveData<TAAPDestination> destination = new MutableLiveData<>();

    LiveData<TAAPDestination> getDestination() {
        return destination;
    }

    public void onNavigate(TAAPDestination destination) {
        this.destination.setValue(destination);
    }
}
