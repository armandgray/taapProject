package com.armandgray.shared.rx;

import androidx.annotation.VisibleForTesting;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class CountedSubjectWrapper<T> {

    @VisibleForTesting
    final Subject<T> subject;

    private int refCount;

    private Action onObserveAction;

    private Action onUnObserveAction;

    private CountedSubjectWrapper(Subject<T> subject) {
        this.subject = subject;
    }

    @SuppressWarnings("Convert2Diamond")
    @NonNull
    public static <T> CountedSubjectWrapper<T> createBehavior() {
        return new CountedSubjectWrapper<T>(BehaviorSubject.create());
    }

    @SuppressWarnings("Convert2Diamond")
    @NonNull
    public static <T> CountedSubjectWrapper<T> createPublish() {
        return new CountedSubjectWrapper<T>(PublishSubject.create());
    }

    public void onNext(T t) {
        this.subject.onNext(t);
    }

    public CountedSubjectWrapper<T> doOnObserved(Action action) {
        this.onObserveAction = action;
        return this;
    }

    public CountedSubjectWrapper<T> doOnUnObserved(Action action) {
        this.onUnObserveAction = action;
        return this;
    }

    public Observable<T> toObservable() {
        return subject.compose(applyRefCountListeners());
    }

    private <U> ObservableTransformer<U, U> applyRefCountListeners() {
        return observable -> observable
                .doOnSubscribe(registerSubscriber())
                .doOnDispose(unregisterSubscriber());
    }

    private Consumer<Disposable> registerSubscriber() {
        return ignore -> {
            if (refCount == 0 && onObserveAction != null) {
                onObserveAction.run();
            }

            refCount++;
        };
    }

    private Action unregisterSubscriber() {
        return () -> {
            refCount--;

            if (refCount == 0 && onUnObserveAction != null) {
                onUnObserveAction.run();
            }
        };
    }
}
