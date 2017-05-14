package com.armandgray.taap.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.armandgray.taap.R;
import com.armandgray.taap.models.SessionLog;

import java.util.ArrayList;

public class LogSetsRvAdapter extends RecyclerView.Adapter<LogSetsRvAdapter.LogSetsViewHolder> {

    private ArrayList<SessionLog> logs;

    LogSetsRvAdapter() {}

    public LogSetsRvAdapter(ArrayList<SessionLog> logs) {
        this.logs = logs;
    }

    @Override
    public LogSetsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LogSetsViewHolder(getLayout(parent));
    }

    @Override
    public void onBindViewHolder(LogSetsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    View getLayout(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.drill_listitem, parent, false);
    }

    public SessionLog getItemAtPosition(int position) {
        if (logs == null || logs.size() <= position) { return null; }
        return logs.get(position);
    }

    static class LogSetsViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView tvHeader;
        ImageView ivImage;
        TextView tvText;

        LogSetsViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            tvHeader = (TextView) itemView.findViewById(R.id.tvHeader);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
        }
    }
}
