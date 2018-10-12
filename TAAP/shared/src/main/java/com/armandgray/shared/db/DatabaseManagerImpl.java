package com.armandgray.shared.db;

import com.armandgray.shared.model.Drill;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class DatabaseManagerImpl {

    @Inject
    DrillDatabase database;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    DatabaseManagerImpl() {
    }

    public void populateDefaults() {
        disposables.add(Observable.fromIterable(Drill.Defaults.getDefaults())
                .share()
                .subscribeOn(Schedulers.io())
                .subscribe(database.drillDao()::insertDrill));
    }
}
