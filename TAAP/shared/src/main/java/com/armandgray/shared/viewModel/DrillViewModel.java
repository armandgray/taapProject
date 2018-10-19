package com.armandgray.shared.viewModel;

import android.util.Log;

import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.model.Drill;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DrillViewModel extends ViewModel {

    private static final String TAG = "DRILL_VIEW_MODEL";

    @VisibleForTesting
    final CompositeDisposable disposables = new CompositeDisposable();

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

            repository.getDrillsObservable().subscribe(new DrillObserver<List<Drill>>() {
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

            repository.getActiveDrillObservable().subscribe(new DrillObserver<Drill>() {
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

    abstract class DrillObserver<T> implements Observer<T> {

        @Override
        public void onSubscribe(@NonNull Disposable d) {
            DrillViewModel.this.disposables.add(d);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            Log.e(TAG, "DrillObserver: onError: " + e.getMessage());
        }

        @Override
        public void onComplete() {
        }
    }
}
