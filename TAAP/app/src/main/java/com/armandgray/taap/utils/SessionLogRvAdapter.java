package com.armandgray.taap.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.armandgray.taap.R;
import com.armandgray.taap.models.SessionLog;

public class SessionLogRvAdapter {

    private SessionLog sessionLog;

    public SessionLogRvAdapter() {}

    public SessionLogRvAdapter(SessionLog sessionLog) {
        this.sessionLog = sessionLog;
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
