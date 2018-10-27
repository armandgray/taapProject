package com.armandgray.shared.permission;

import android.content.Context;
import android.content.pm.PackageManager;

import com.armandgray.shared.application.TAAPAppComponent;
import com.armandgray.shared.db.DatabaseManager;
import com.armandgray.shared.rx.SchedulerProvider;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

public class PermissionManagerImpl implements PermissionManager {

    private static final int RATIONALE_TIMEOUT = 10000;

    @Inject
    Context context;

    @Inject
    SchedulerProvider schedulers;

    @Inject
    DatabaseManager databaseManager;

    CompositeDisposable disposables = new CompositeDisposable();

    private final PublishSubject<DangerousPermission> rationaleRequestSubject = PublishSubject.create();
    private final PublishSubject<Boolean> rationaleResultSubject = PublishSubject.create();
    private final PublishSubject<DangerousPermission> permissionRequestSubject = PublishSubject.create();
    private final PublishSubject<DangerousPermission> permissionResultSubject = PublishSubject.create();

    public PermissionManagerImpl() {
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
        return rationaleResponseToRequestObservable(permission);
    }

    private boolean hasPermission(DangerousPermission permission) {
        String key = permission.getKey();
        return context.checkSelfPermission(key) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasRequestedPermission(DangerousPermission permission) {
        return databaseManager.getSharedPreferencesDao().hasRequestedPermission(permission);
    }

    private void showPermissionRationaleDialog(DangerousPermission permission) {
        rationaleRequestSubject.onNext(permission);
    }

    private Observable<Boolean> rationaleResponseToRequestObservable(DangerousPermission permission) {
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
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui());
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }
}
