package com.armandgray.taap.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.armandgray.shared.R;
import com.armandgray.shared.model.Setting;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.widget.RecyclerView;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder> {

    private final List<Setting> list = new ArrayList<>();

    @Inject
    SettingsAdapter() {
    }

    @NonNull
    @Override
    public SettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SettingsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.preference_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsViewHolder holder, int position) {
        holder.setUp(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    void updateData(@Nullable List<Setting> settings) {
        if (settings != null) {
            this.list.clear();
            this.list.addAll(settings);
            notifyDataSetChanged();
        }
    }

    Setting getSetting(int position) {
        return list.get(position);
    }

    static class SettingsViewHolder extends RecyclerView.ViewHolder {

        @VisibleForTesting
        final ImageView imageIcon;

        @VisibleForTesting
        final TextView textTitle;

        SettingsViewHolder(View itemView) {
            super(itemView);

            imageIcon = itemView.findViewById(R.id.image_icon);
            textTitle = itemView.findViewById(R.id.text_title);
        }

        public void setUp(@NonNull Setting setting) {
            imageIcon.setBackgroundResource(R.drawable.bg_circular_outline);
            imageIcon.setImageResource(setting.getImageResId());
            textTitle.setText(setting.getTitle());
        }
    }
}