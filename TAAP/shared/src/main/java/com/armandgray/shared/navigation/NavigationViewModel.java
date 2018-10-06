package com.armandgray.shared.navigation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NavigationViewModel extends ViewModel {

    private MutableLiveData<TAAPDestination> destination = new MutableLiveData<>();

    LiveData<TAAPDestination> getDestination() {
        return destination;
    }

    public void onNavigate(TAAPDestination destination) {
        this.destination.setValue(destination);
    }
}
