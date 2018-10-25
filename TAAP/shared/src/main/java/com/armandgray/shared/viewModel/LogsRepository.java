package com.armandgray.shared.viewModel;

import android.annotation.SuppressLint;

import com.armandgray.shared.application.TAAPRepository;
import com.armandgray.shared.db.DatabaseManager;
import com.armandgray.shared.db.PerformanceDao;
import com.armandgray.shared.helpers.WorkoutsHelper;
import com.armandgray.shared.model.UXPreference;
import com.armandgray.shared.model.WorkoutInfo;
import com.armandgray.shared.rx.SchedulerProvider;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

@Singleton
class LogsRepository extends TAAPRepository {

    private static final int RECENT_WORKOUTS_LIMIT = 5;

    private static int maxWorkoutBreak = UXPreference.Item.BREAK_LIMIT.getDefault(true);

    @Inject
    DatabaseManager databaseManager;

    @Inject
    SchedulerProvider schedulers;

    @Inject
    LogsRepository(PreferencesRepository preferencesRepository) {
        disposables.add(preferencesRepository.getPreferenceUpdateObservable()
                .subscribe(this::preferenceConsumer));
    }

    private void preferenceConsumer(UXPreference preference) {
        if (preference.getCategory().isDrillCategory()) {
            return;
        }

        switch (preference.getCategory()) {
            case WORKOUT:
                maxWorkoutBreak = preference.getValue(UXPreference.Item.BREAK_LIMIT, true);
                break;
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    Observable<List<WorkoutInfo>> getRecentWorkoutObservable() {
        return databaseManager.getPerformanceDao()
                .logsBetween(new Date(0).getTime(), System.currentTimeMillis())
                .toObservable()
                .map(toWorkoutInfo())
                .observeOn(schedulers.ui());
    }

    private Function<List<PerformanceDao.DaoLog>, List<WorkoutInfo>> toWorkoutInfo() {
        return logs -> WorkoutsHelper
                .toWorkoutsTreeMap(logs, maxWorkoutBreak, RECENT_WORKOUTS_LIMIT)
                .values()
                .stream()
                .map(WorkoutInfo::new)
                .collect(Collectors.toList());
    }
}
