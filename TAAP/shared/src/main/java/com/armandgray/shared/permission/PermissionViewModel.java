package com.armandgray.shared.permission;

import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.application.TAAPViewModel;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class PermissionViewModel extends TAAPViewModel {

    @Inject
    PermissionManager permissionManager;

    private MutableLiveData<DangerousPermission> permissionsLiveData;
    private MutableLiveData<DangerousPermission> rationaleLiveData;

    private boolean isResumed = true;

    public PermissionViewModel() {
        TAAPApplication.getAppComponent().inject(this);
    }

    LiveData<DangerousPermission> getRationaleRequest() {
        if (rationaleLiveData == null) {
            rationaleLiveData = new MutableLiveData<>();

            permissionManager.getRationaleRequestObservable()
                    .subscribe(new ViewModelObserver<DangerousPermission>() {
                        @Override
                        public void onNext(DangerousPermission permission) {
                            if (!isResumed) {
                                return;
                            }

                            rationaleLiveData.setValue(permission);
                        }
                    });
        }

        return rationaleLiveData;
    }

    void onRationaleResponded(boolean allow) {
        permissionManager.onRationaleResponded(allow);
    }

    LiveData<DangerousPermission> getPermissionRequest() {
        if (permissionsLiveData == null) {
            permissionsLiveData = new MutableLiveData<>();

            permissionManager.getPermissionRequestObservable()
                    .subscribe(new ViewModelObserver<DangerousPermission>() {
                        @Override
                        public void onNext(DangerousPermission permission) {
                            if (!isResumed) {
                                return;
                            }

                            permissionsLiveData.setValue(permission);
                        }
                    });
        }

        return permissionsLiveData;
    }

    void onResult(DangerousPermission permission) {
        permissionManager.onResult(permission);
    }

    void setResumed(boolean resumed) {
        this.isResumed = resumed;
    }
}
