package com.armandgray.shared.db;

import com.armandgray.shared.model.Setting;
import com.armandgray.shared.model.UXPreference;
import com.armandgray.shared.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.room.Dao;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface SettingsDao extends GlobalDatabase.BaseDao<Setting> {

    @Nullable
    default Setting findSetting(@NonNull UXPreference preference) {
        return null;
    }

    @Query("SELECT * FROM settings")
    Single<List<Setting>> all();

    @Query("SELECT * FROM settings WHERE id == :id")
    Single<Setting> setting(int id);

    @DatabaseManager.DatabaseScope
    class SettingsDaoWrapper implements SettingsDao {

        @VisibleForTesting
        static final List<Setting> CACHE = new ArrayList<>();

        @Inject
        GlobalDatabase database;

        @Inject
        SchedulerProvider schedulers;

        @Inject
        SettingsDaoWrapper() {
        }

        @Override
        public Setting findSetting(@NonNull UXPreference preference) {
            return CACHE.stream()
                    .filter(setting -> {
                        UXPreference.Category nextCategory = setting.getPreference().getCategory();
                        return preference.getCategory().equals(nextCategory);
                    })
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public Single<List<Setting>> all() {
            Single<List<Setting>> db = database.settingsDao().all().doOnSuccess(CACHE::addAll);
            Single<List<Setting>> observable = CACHE.isEmpty() ? db : Single.just(CACHE);
            return observable.subscribeOn(schedulers.io());
        }

        @Override
        public Single<Setting> setting(int id) {
            return database.settingsDao().setting(id).subscribeOn(schedulers.io());
        }

        @Override
        public Single<List<Long>> insert(Setting... arr) {
            return database.settingsDao().insert(arr).subscribeOn(schedulers.io());
        }

        @Override
        public Single<Integer> update(Setting... arr) {
            return database.settingsDao().update(arr).subscribeOn(schedulers.io());
        }

        @Override
        public Single<Integer> delete(Setting... arr) {
            return database.settingsDao().delete(arr).subscribeOn(schedulers.io());
        }
    }
}
