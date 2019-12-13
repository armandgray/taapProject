package com.armandgray.shared.application;

import com.armandgray.shared.helpers.StringHelper;

import androidx.annotation.NonNull;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class TAAPRepository {

    protected final String TAG = StringHelper.toLogTag(getClass().getSimpleName());

    @SuppressWarnings("WeakerAccess")
    protected final CompositeDisposable disposables = new CompositeDisposable();

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }

    protected abstract class RepositoryObserver<T> implements Observer<T> {

        @Override
        public void onSubscribe(@NonNull Disposable d) {
            TAAPRepository.this.disposables.add(d);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            throw new RuntimeException();
        }

        @Override
        public void onComplete() {
        }
    }
}
