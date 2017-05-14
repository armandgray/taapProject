package com.armandgray.taap.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.armandgray.taap.R;
import com.armandgray.taap.models.SessionLog;

import java.util.ArrayList;

public class LogSetsRvAdapter extends RecyclerView.Adapter<LogSetsRvAdapter.LogSetsViewHolder> {

    private ArrayList<SessionLog> logs;

    public LogSetsRvAdapter(ArrayList<SessionLog> logs) {
        this.logs = logs;
    }

    @Override
    public LogSetsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(LogSetsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
