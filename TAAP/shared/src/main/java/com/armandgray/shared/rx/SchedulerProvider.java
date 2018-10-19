package com.armandgray.shared.rx;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public interface SchedulerProvider {

    Scheduler ui();

    Scheduler io();

    @SuppressWarnings("unused")
    Scheduler trampoline();

    @SuppressWarnings("unused")
    Scheduler computation();

    @SuppressWarnings("unused")
    Scheduler newThread();

    Scheduler single();

    SchedulerProvider DEFAULT = new SchedulerProvider() {

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
    };
}
