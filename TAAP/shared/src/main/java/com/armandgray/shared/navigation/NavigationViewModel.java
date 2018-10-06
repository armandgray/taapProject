package com.armandgray.shared.navigation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class NavigationViewModel extends ViewModel {

    private MutableLiveData<TAAPDestination> destination = new MutableLiveData<>();

    LiveData<TAAPDestination> getDestination() {
        return destination;
    }

    public void onNavigate(TAAPDestination destination) {
        this.destination.setValue(destination);
    }
}
