package com.armandgray.taap.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.armandgray.shared.R;

import com.armandgray.shared.model.UXPreference;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.widget.RecyclerView;

class PreferencesAdapter extends RecyclerView.Adapter<PreferencesAdapter.PreferencesViewHolder> {

    private final List<UXPreference.Value> list = new ArrayList<>();

    @Inject
    PreferencesAdapter() {
    }

    @NonNull
    @Override
    public PreferencesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PreferencesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.preference_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PreferencesViewHolder holder, int position) {
        holder.setUp(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    void updateData(@Nullable List<UXPreference.Value> items) {
        if (items != null) {
            this.list.clear();
            this.list.addAll(items);
            notifyDataSetChanged();
        }
    }

    UXPreference.Value getPreference(int position) {
        return list.get(position);
    }

    static class PreferencesViewHolder extends RecyclerView.ViewHolder {

        @VisibleForTesting
        final ImageView imageIcon;

        @VisibleForTesting
        final TextView textTitle;

        PreferencesViewHolder(View itemView) {
            super(itemView);

            imageIcon = itemView.findViewById(R.id.image_icon);
            textTitle = itemView.findViewById(R.id.text_title);
        }

        public void setUp(@NonNull UXPreference.Value value) {
            String text = "Set " + value.getItem().getText();
            imageIcon.setBackgroundResource(R.drawable.bg_circular_outline);
            imageIcon.setImageResource(value.getItem().getImageResId());
            textTitle.setText(text);
        }
    }
}