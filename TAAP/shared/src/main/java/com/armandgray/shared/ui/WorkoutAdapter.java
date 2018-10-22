package com.armandgray.shared.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.armandgray.shared.R;
import com.armandgray.shared.model.WorkoutInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    private final List<WorkoutInfo> list = new ArrayList<>();

    @Inject
    WorkoutAdapter() {
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorkoutViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_info_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        holder.setUp(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateData(@Nullable List<WorkoutInfo> logs) {
        if (logs != null) {
            this.list.clear();
            this.list.addAll(logs);
            notifyDataSetChanged();
        }
    }

    static class WorkoutViewHolder extends RecyclerView.ViewHolder {

        private final TextView textDate;
        private final ImageView imageType;
        private final TextView textPerformance;
        private final TextView textReps;

        WorkoutViewHolder(View itemView) {
            super(itemView);

            textDate = itemView.findViewById(R.id.text_date);
            imageType = itemView.findViewById(R.id.image_type);
            textPerformance = itemView.findViewById(R.id.text_performance);
            textReps = itemView.findViewById(R.id.text_reps);
        }

        public void setUp(@NonNull WorkoutInfo workout) {
            textDate.setText(workout.getLocation() != null ? workout.getLocation().getTitle() : "");
            imageType.setImageResource(workout.getTypes().get(0).getImageResId());
            textPerformance.setText(workout.getOverallPerformance());
            textReps.setText(String.valueOf(workout.getOverallReps()));
        }
    }
}