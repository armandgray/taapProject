package com.armandgray.taap.utils;

import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.armandgray.taap.R;
import com.armandgray.taap.models.SessionLog;

import java.util.ArrayList;
import java.util.Locale;

public class LogSetsRvAdapter extends RecyclerView.Adapter<LogSetsRvAdapter.LogSetsViewHolder> {

    @VisibleForTesting ArrayList<SessionLog> logs;
    private boolean arePrevLogs;

    public LogSetsRvAdapter() {}

    public LogSetsRvAdapter(ArrayList<SessionLog> logs, boolean arePrevLogs) {
        this.logs = logs;
        this.arePrevLogs = arePrevLogs;
    }

    public LogSetsRvAdapter(ArrayList<SessionLog> logs) {
        this.logs = logs;
    }

    @Override
    public LogSetsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LogSetsViewHolder(getLayout(parent));
    }

    @Override
    public void onBindViewHolder(LogSetsViewHolder viewHolder, int position) {
        SessionLog log = getItemAtPosition(position);
        TextView tvText = viewHolder.tvText;
        int repsCompleted = arePrevLogs && log.getRepsCompleted() != 1
                ? log.getRepsCompleted()/log.getSetsCompleted()
                : log.getRepsCompleted();
        tvText.setText(String.format(Locale.US,
                "%dx%d @%d%%",
                log.getSetsCompleted(),
                repsCompleted,
                ((Double) (log.getSuccessRate() * 100)).intValue()));
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    View getLayout(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.log_sets_textview, parent, false);
    }

    @VisibleForTesting
    public SessionLog getItemAtPosition(int position) {
        if (logs == null || logs.size() <= position) { return null; }
        return logs.get(position);
    }

    public void addLog(SessionLog log) {
        this.logs.add(log);
        notifyDataSetChanged();
    }

    static class LogSetsViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView tvText;

        LogSetsViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            tvText = (TextView) itemView.findViewById(R.id.tvText);
        }
    }
}
