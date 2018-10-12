package com.armandgray.shared.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.armandgray.shared.R;
import com.armandgray.shared.model.Drill;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.widget.RecyclerView;

public class DrillAdapter extends RecyclerView.Adapter<DrillAdapter.DrillViewHolder> {

    private final List<Drill> list = new ArrayList<>();

    @Inject
    DrillAdapter() {
    }

    @NonNull
    @Override
    public DrillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DrillViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drill_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DrillViewHolder holder, int position) {
        holder.setUp(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateData(@Nullable List<Drill> drills) {
        if (drills != null) {
            this.list.clear();
            this.list.addAll(drills);
            notifyDataSetChanged();
        }
    }

    public Drill getItem(int position) {
        return list.get(position);
    }

    public int indexOf(@Nullable Drill drill) {
        return drill == null ? 0 : list.indexOf(drill);
    }

    static class DrillViewHolder extends RecyclerView.ViewHolder {
        @VisibleForTesting
        final ImageView imageIcon;

        @VisibleForTesting
        final TextView textTitle;

        @VisibleForTesting
        final TextView textActive;

        DrillViewHolder(View itemView) {
            super(itemView);

            imageIcon = itemView.findViewById(R.id.image_icon);
            textTitle = itemView.findViewById(R.id.text_title);
            textActive = itemView.findViewById(R.id.text_active);
        }

        public void setUp(@NonNull Drill drill) {
            imageIcon.setImageResource(drill.getImageResId());
            textTitle.setText(drill.getTitle());
            textActive.setVisibility(drill.isActive() ? View.VISIBLE : View.GONE);
        }
    }
}