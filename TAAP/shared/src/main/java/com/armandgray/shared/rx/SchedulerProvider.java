package com.armandgray.shared.rx;

import android.os.HandlerThread;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public interface SchedulerProvider {

    default <U> ObservableTransformer<U, U> asyncTask() {
        return observable -> observable
                .subscribeOn(io())
                .observeOn(ui());
    }

    Scheduler ui();

    Scheduler io();

    @SuppressWarnings("unused")
    Scheduler trampoline();

    @SuppressWarnings("unused")
    Scheduler computation();

    @SuppressWarnings("unused")
    Scheduler newThread();

    Scheduler single();

    Scheduler looper();

    SchedulerProvider DEFAULT = new SchedulerProvider() {

        private HandlerThread handlerThread;

        @Override
        public Scheduler ui() {
            return AndroidSchedulers.mainThread();
        }

        @Override
        public Scheduler io() {
            return Schedulers.io();
        }

        @Override
        public Scheduler trampoline() {
            return Schedulers.trampoline();
        }

        @Override
        public Scheduler computation() {
            return Schedulers.computation();
        }

        @Override
        public Scheduler newThread() {
            return Schedulers.newThread();
        }

        @Override
        public Scheduler single() {
            return Schedulers.single();
        }

        @Override
        public Scheduler looper() {
            if (handlerThread == null) {
                handlerThread = new HandlerThread("TAAP-Scheduler-Thread");
                handlerThread.start();
            }

            return AndroidSchedulers.from(handlerThread.getLooper());
        }
    };
}
