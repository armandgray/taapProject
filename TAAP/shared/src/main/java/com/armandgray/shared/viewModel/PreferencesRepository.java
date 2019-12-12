package com.armandgray.shared.viewModel;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.armandgray.shared.application.TAAPRepository;
import com.armandgray.shared.db.DatabaseManager;
import com.armandgray.shared.model.Performance;
import com.armandgray.shared.model.Setting;
import com.armandgray.shared.model.UXPreference;
import com.armandgray.shared.rx.SchedulerProvider;
import com.google.common.collect.ImmutableList;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.FileProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.ReplaySubject;

import static java.util.Objects.requireNonNull;

@Singleton
class PreferencesRepository extends TAAPRepository {

    private static final List<String> EXPORT_INCLUSION = ImmutableList.of(
            "getId",
            "getDate",
            "getDrillTitle",
            "getLength",
            "getGoal",
            "getRate",
            "getCount",
            "getTotal",
            "getLocation");
    private static final String GET = "get";

    @VisibleForTesting
    @NonNull
    final BehaviorSubject<UXPreference> activePreferenceSubject = BehaviorSubject.create();

    @VisibleForTesting
    @NonNull
    final BehaviorSubject<UXPreference.Value> activeValueSubject = BehaviorSubject.create();

    @VisibleForTesting
    @NonNull
    final ReplaySubject<UXPreference> preferenceUpdateSubject = ReplaySubject.create();

    private final Context context;
    private final DatabaseManager databaseManager;
    private final SchedulerProvider schedulers;

    @Inject
    PreferencesRepository(Context context, DatabaseManager databaseManager, SchedulerProvider schedulers) {
        this.context = context;
        this.databaseManager = databaseManager;
        this.schedulers = schedulers;

        this.databaseManager.stateSubject
                .switchMap(toSettingsList())
                .compose(schedulers.asyncTask())
                .subscribe(onSettingsRetrieved());
    }

    private Function<DatabaseManager.State, ObservableSource<List<Setting>>> toSettingsList() {
        return state -> state == DatabaseManager.State.READY
                ? databaseManager.getSettingsDao().all().toObservable()
                : Observable.just(new ArrayList<Setting>());
    }

    private RepositoryObserver<List<Setting>> onSettingsRetrieved() {
        return new RepositoryObserver<List<Setting>>() {

            @Override
            public void onNext(List<Setting> list) {
                if (list.size() == 0) {
                    Log.d(TAG, "Settings Population: Retrieved Empty List (Check State)");
                    return;
                }

                updateSettingsSubscribers(list);
            }
        };
    }

    @VisibleForTesting
    void updateSettingsSubscribers(List<Setting> list) {
        list.stream().map(Setting::getPreference).forEach(preferenceUpdateSubject::onNext);
    }

    @NonNull
    Observable<UXPreference> getActivePreferenceObservable() {
        return activePreferenceSubject;
    }

    void setActivePreference(UXPreference preference) {
        this.activePreferenceSubject.onNext(preference);
    }

    @NonNull
    Observable<UXPreference.Value> getActiveValueObservable() {
        return activeValueSubject;
    }

    void setActiveValue(UXPreference.Value value) {
        this.activeValueSubject.onNext(value);
    }

    @NonNull
    Observable<UXPreference> getPreferenceUpdateObservable() {
        return preferenceUpdateSubject;
    }

    @NonNull
    Observable<List<Setting>> getSettingsObservable() {
        return databaseManager.getSettingsDao().all().observeOn(schedulers.ui()).toObservable();
    }

    void onPreferenceUpdated() {
        UXPreference preference = activePreferenceSubject.getValue();
        if (preference == null) {
            return;
        }

        preferenceUpdateSubject.onNext(preference);
        storeSetting(preference);
    }

    void onPreferenceTriggered(@NonNull UXPreference.Value value) {
        if (activePreferenceSubject.getValue() == null) {
            return;
        }

        UXPreference preference = activePreferenceSubject.getValue();
        switch (value.getItem()) {
            case EXPORT:
                onExportPreference();
                return;

            case RESET:
                onResetPreference(preference);
                break;
        }

        preferenceUpdateSubject.onNext(preference);
    }

    private void onExportPreference() {
        requireNonNull(databaseManager.getPerformanceDao()
                .all()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(performances -> {
                    try {
                        exportCSV(performances);
                    } catch (IOException | IllegalStateException e) {
                        String message = e instanceof IllegalStateException ? e.getMessage() : "";
                        onExportFailure(e, message);
                    }
                }, e -> onExportFailure(e, "Unable to access stored data")));
    }

    private void onExportFailure(Throwable e, String message) {
        Toast.makeText(
                context,
                message.isEmpty() ? "Export Failed Unexpectedly! " : message,
                Toast.LENGTH_LONG).show();
        Log.e(TAG, e.getMessage());
    }

    private void exportCSV(List<Performance> performances) throws IOException, IllegalStateException {
        if (performances.isEmpty()) {
            throw new IllegalStateException("No performance data found");
        }

        File file = getFile();
        writeCSV(performances, file);
        exportFile(file);
    }

    private File getFile() throws IOException {
        File file = new File(context.getFilesDir(), "exports/performances.csv");
        if (!file.getParentFile().exists() && !file.getParentFile().mkdir()) {
            throw new IllegalStateException("Unable to structure export file");
        }

        if (!file.exists() && !file.createNewFile()) {
            throw new IllegalStateException("Unable to create export file");
        }

        return file;
    }

    private void writeCSV(List<Performance> performances, File file) throws IOException {
        List<Method> getters = collectPerformanceGetters();
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        // Write Columns
        writer.writeNext(getters.stream()
                .map(method -> method.getName().substring(GET.length()))
                .toArray(String[]::new));
        // Write Rows
        for (Performance performance : performances) {
            writer.writeNext(getters.stream()
                    .map(getter -> {
                        try {
                            return String.valueOf(getter.invoke(performance));
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new UnsupportedOperationException("Data retrieval failed");
                        }
                    })
                    .toArray(String[]::new));
        }

        writer.close();
    }

    private List<Method> collectPerformanceGetters() {
        return Stream.of(Performance.class.getMethods())
                .filter(method -> EXPORT_INCLUSION.contains(method.getName()))
                .sorted(Comparator.comparing(
                        method -> EXPORT_INCLUSION.indexOf(method.getName()),
                        Comparator.naturalOrder()))
                .collect(Collectors.toList());
    }

    private void exportFile(File file) {
        Uri uri = FileProvider.getUriForFile(context, "com.armandgray.wear.fileprovider", file);
        Intent intent = createExportIntent(uri);
        grantExportActivityPermissions(uri, intent);
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            throw new UnsupportedOperationException("Unable to find an app to handle export");
        }

        context.startActivity(intent);
    }

    private Intent createExportIntent(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/csv");
        intent.putExtra(Intent.EXTRA_SUBJECT, "TAAP Performance Export");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        return intent;
    }

    private void grantExportActivityPermissions(Uri uri, Intent intent) {
        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    private void onResetPreference(@NonNull UXPreference preference) {
        if (preference.getCategory().equals(UXPreference.Category.DATA)) {
            databaseManager.getPerformanceDao().deleteAll();
            return;
        }

        preference.getValues().forEach(pref -> {
            int defaultValue = pref.getItem().getDefault();
            pref.setValue(defaultValue);
        });

        storeSetting(preference);
    }

    private void storeSetting(@NonNull UXPreference preference) {
        if (!preference.getCategory().isDrillCategory()) {
            updateDatabaseSetting(databaseManager.getSettingsDao().findSetting(preference));
        }
    }

    private void updateDatabaseSetting(Setting setting) {
        databaseManager.getSettingsDao().update(setting).subscribe(
                new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.d(TAG, "Setting Update Success: " + setting);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Setting Update Failed: " + setting);
                    }
                });
    }
}
