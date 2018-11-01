package com.armandgray.shared.permission;

import android.content.Context;
import android.content.pm.PackageManager;

import com.armandgray.shared.application.TAAPAppComponent;
import com.armandgray.shared.db.DatabaseManager;
import com.armandgray.shared.rx.SchedulerProvider;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class PermissionManagerImpl implements PermissionManager {

    @Inject
    Context context;

    @Inject
    SchedulerProvider schedulers;

    @Inject
    DatabaseManager databaseManager;

    @VisibleForTesting
    final PublishSubject<Boolean> rationaleResultSubject;

    private final PublishSubject<DangerousPermission> rationaleRequestSubject;
    private final PublishSubject<DangerousPermission> permissionRequestSubject;
    private final PublishSubject<DangerousPermission> permissionResultSubject;

    public PermissionManagerImpl() {
        rationaleRequestSubject = PublishSubject.create();
        rationaleResultSubject = PublishSubject.create();
        permissionRequestSubject = PublishSubject.create();
        permissionResultSubject = PublishSubject.create();
    }

    @Override
    public void inject(TAAPAppComponent appComponent) {
        Component component = appComponent.permissionBuilder()
                .permissionManager(this)
                .managerModule(new ManagerModule())
                .build();

        component.inject(this);
    }

    @Override
    public boolean hasPermission(DangerousPermission permission) {
        String key = permission.getKey();
        return context.checkSelfPermission(key) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public Observable<DangerousPermission> getPermissionRequestObservable() {
        return permissionRequestSubject;
    }

    @Override
    public void onResult(DangerousPermission permission) {
        // TODO Verify onBackPress doesn't skip onResult on Permission Dialog for Phones
        databaseManager.getSharedPreferencesDao().registerRequestedPermission(permission);
        permissionResultSubject.onNext(permission);
    }

    @Override
    public Observable<DangerousPermission> getRationaleRequestObservable() {
        return rationaleRequestSubject;
    }

    @Override
    public void onRationaleResponded(boolean allow) {
        rationaleResultSubject.onNext(allow);
    }

    @Override
    public Observable<Boolean> usePermission(DangerousPermission permission) {
        if (hasPermission(permission)) {
            return Observable.just(true);
        }

        if (!hasRequestedPermission(permission)) {
            requestPermission(permission);
            return permissionResultObservable(permission);
        }

        showPermissionRationaleDialog(permission);
        return rationaleResultToRequestObservable(permission);
    }

    private boolean hasRequestedPermission(DangerousPermission permission) {
        return databaseManager.getSharedPreferencesDao().hasRequestedPermission(permission);
    }

    private void showPermissionRationaleDialog(DangerousPermission permission) {
        rationaleRequestSubject.onNext(permission);
    }

    private Observable<Boolean> rationaleResultToRequestObservable(DangerousPermission permission) {
        return rationaleResultSubject.concatMap(userAllowed -> {
            boolean hasPermission = hasPermission(permission);
            if (hasPermission || !userAllowed) {
                return Observable.just(hasPermission);
            }

            requestPermission(permission);
            return permissionResultObservable(permission);
        });
    }

    private void requestPermission(DangerousPermission permission) {
        permissionRequestSubject.onNext(permission);
    }

    private Observable<Boolean> permissionResultObservable(DangerousPermission permission) {
        return permissionResultSubject
                .filter(result -> result == permission)
                .map(this::hasPermission)
                .compose(schedulers.asyncTask());
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }
}
