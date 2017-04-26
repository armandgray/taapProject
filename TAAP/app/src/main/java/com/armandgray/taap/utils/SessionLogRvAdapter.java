package com.armandgray.taap.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.armandgray.taap.R;
import com.armandgray.taap.models.Drill;
import com.armandgray.taap.models.SessionLog;

public class SessionLogRvAdapter {

    private SessionLog sessionLog;

    public SessionLogRvAdapter() {}

    public SessionLogRvAdapter(SessionLog sessionLog) {
        this.sessionLog = sessionLog;
    }

    public SessionLogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SessionLogViewHolder(getLayout(parent));
    }

    public void onBindViewHolder(SessionLogViewHolder viewHolder, int position) {
        final Object item = getItemAtPosition(position);

        ImageView ivImage = viewHolder.ivImage;
        TextView tvTitle = viewHolder.tvTitle;

        ivImage.setImageResource(drill.getImageId());
        tvTitle.setText(drill.getTitle());
    }

    View getLayout(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.drill_listitem, parent, false);
    }

    public Object getItemAtPosition(int position) {
        switch (position) {
            case 0:
                return sessionLog.getSessionDate();
            case 1:
                return sessionLog.getSessionLength();
            case 2:
                return sessionLog.getSessionGoal();
            case 3:
                return sessionLog.getActiveWork();
            case 4:
                return sessionLog.getRestTime();
            case 5:
                return sessionLog.getSetsCompleted();
            case 6:
                return sessionLog.getRepsCompleted();
            case 7:
                return sessionLog.getSuccessRate();
            case 8:
                return sessionLog.getSuccessRecord();
        }
    }

    static class SessionLogViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView ivImage;
        TextView tvTitle;

        SessionLogViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }
}
