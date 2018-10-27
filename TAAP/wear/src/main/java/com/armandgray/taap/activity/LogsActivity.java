package com.armandgray.taap.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.armandgray.shared.model.WorkoutLocation;
import com.armandgray.shared.model.WorkoutInfo;
import com.armandgray.shared.ui.WorkoutAdapter;
import com.armandgray.shared.viewModel.LogsViewModel;
import com.armandgray.taap.R;
import com.armandgray.taap.application.WearDelegateActivity;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjection;

public class LogsActivity extends WearDelegateActivity {

    @Inject
    LogsViewModel logsViewModel;

    @Inject
    WorkoutAdapter workoutAdapter;

    private ProgressBar progressBar;
    private TextView textNoLogs;

    private ImageView imageLocation;
    private TextView textLastLocation;
    private TextView textLastPerformance;
    private ImageView imageTime;
    private TextView textLastLength;
    private ImageView imageReps;
    private TextView textLastReps;
    private ImageView imageLastTypes;
    private RecyclerView recyclerLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Dagger Injection
        AndroidInjection.inject(this);

        // Super
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_logs);
        super.onSetupContent();
    }

    @Override
    public void assignGlobalFields() {
        super.assignGlobalFields();

        progressBar = findViewById(R.id.progress_bar);
        textNoLogs = findViewById(R.id.text_no_logs);

        imageLocation = findViewById(R.id.image_location);
        textLastLocation = findViewById(R.id.text_last_location);
        textLastPerformance = findViewById(R.id.text_last_performance);
        imageTime = findViewById(R.id.image_time);
        textLastLength = findViewById(R.id.text_last_length);
        imageReps = findViewById(R.id.image_reps);
        textLastReps = findViewById(R.id.text_last_reps);
        imageLastTypes = findViewById(R.id.image_last_types);
        recyclerLogs = findViewById(R.id.recycler_logs);
    }

    @Override
    public void setupVisualElements() {
        super.setupVisualElements();

        recyclerLogs.setAdapter(workoutAdapter);
        recyclerLogs.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.HORIZONTAL, false));
    }

    @Override
    public void setupEventListeners() {
        super.setupEventListeners();
    }

    @Override
    public void setupViewModel() {
        super.setupViewModel();

        logsViewModel.getRecentWorkouts().observe(this, this::onWorkoutsChanged);
    }

    private void onWorkoutsChanged(@Nullable List<WorkoutInfo> workouts) {
        if (workouts == null) {
            return;
        }

        progressBar.setVisibility(View.GONE);

        // TODO Investigate getTypes being empty (Possible dummy value from DB when empty)
        if (workouts.size() == 0 || workouts.get(0).getTypes().size() == 0) {
            textNoLogs.setVisibility(View.VISIBLE);
            return;
        }

        toggleUIVisibility();
        displayData(workouts);
    }

    private void toggleUIVisibility() {
        textNoLogs.setVisibility(View.GONE);

        imageLocation.setVisibility(View.VISIBLE);
        imageTime.setVisibility(View.VISIBLE);
        imageReps.setVisibility(View.VISIBLE);
        imageLastTypes.setVisibility(View.VISIBLE);
    }

    private void displayData(@NonNull List<WorkoutInfo> workouts) {
        WorkoutInfo lastWorkout = workouts.get(workouts.size() - 1);
        WorkoutLocation location = lastWorkout.getLocation();
        String locationInfo = lastWorkout.getDay() + String.format(Locale.getDefault(), " - %s",
                location == null ? "" : location.getTitle());

        textLastLocation.setText(locationInfo);
        textLastPerformance.setText(lastWorkout.getOverallPerformance());
        textLastLength.setText(lastWorkout.getLength());
        textLastReps.setText(String.valueOf(lastWorkout.getOverallReps()));
        imageLastTypes.setImageResource(lastWorkout.getTypes().get(0).getImageResId());

        workoutAdapter.updateData(workouts.subList(0, workouts.size() - 1));
    }

    @Module
    public static class ActivityModule
            extends WearDelegateActivity.NavigationModule<LogsActivity> {

        @Provides
        @NonNull
        LogsViewModel provideViewModel(LogsActivity activity) {
            return ViewModelProviders.of(activity).get(LogsViewModel.class);
        }
    }
}
