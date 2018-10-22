package com.armandgray.shared.application;

import android.util.Log;

import com.armandgray.shared.helpers.StringHelper;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class TAAPViewModel extends ViewModel {

    @SuppressWarnings("WeakerAccess")
    protected final String TAG = StringHelper.toLogTag(getClass().getSimpleName());

    @SuppressWarnings("WeakerAccess")
    protected final CompositeDisposable disposables = new CompositeDisposable();

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        disposables.clear();
    }

    protected abstract class ViewModelObserver<T> implements Observer<T> {

        @Override
        public void onSubscribe(@NonNull Disposable d) {
            TAAPViewModel.this.disposables.add(d);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            Log.e(TAG, "ViewModelObserver: onError: " + e.getMessage());
        }

        @Override
        public void onComplete() {
        }
    }
}
