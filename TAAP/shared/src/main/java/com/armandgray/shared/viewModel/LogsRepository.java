package com.armandgray.shared.viewModel;

import android.annotation.SuppressLint;

import com.armandgray.shared.application.TAAPRepository;
import com.armandgray.shared.db.DatabaseManager;
import com.armandgray.shared.db.PerformanceDao;
import com.armandgray.shared.model.WorkoutInfo;
import com.armandgray.shared.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

@Singleton
class LogsRepository extends TAAPRepository {

    @Inject
    DatabaseManager databaseManager;

    @Inject
    SchedulerProvider schedulers;

    @Inject
    LogsRepository() {
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    Observable<List<WorkoutInfo>> getRecentWorkoutObservable() {
        return databaseManager.getPerformanceDao()
                .logsBetween(new Date(0).getTime(), System.currentTimeMillis())
                .map(toWorkoutInfo())
                .observeOn(schedulers.ui())
                .toObservable();
    }

    private Function<List<PerformanceDao.DaoLog>, List<WorkoutInfo>> toWorkoutInfo() {
        return logs -> {
            List<WorkoutInfo> list = new ArrayList<>(logs.size());
            list.add(new WorkoutInfo(logs));
            return list;
        };
    }
}
