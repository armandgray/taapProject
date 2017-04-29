package com.armandgray.taap.utils;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.armandgray.taap.R;
import com.armandgray.taap.models.SessionLog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SessionLogRvAdapter extends RecyclerView.Adapter<SessionLogRvAdapter.SessionLogViewHolder> {

    static final int TYPE_HEADER = 100;
    static final int TYPE_ITEM = 101;

    private SessionLog sessionLog;

    public SessionLogRvAdapter() {}

    public SessionLogRvAdapter(SessionLog sessionLog) {
        this.sessionLog = sessionLog;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) { return TYPE_HEADER; }
        return TYPE_ITEM;
    }

    @Override
    public SessionLogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new SessionLogViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.session_log_header_layout, parent, false));
        }
        return new SessionLogViewHolder(getLayout(parent));
    }

    @Override
    public void onBindViewHolder(SessionLogViewHolder viewHolder, int position) {
        if (position == getItemCount() - 1) {return;}
        final Pair<Integer, ?> sessionItem = getItemAtPosition(position + 1);

        TextView tvHeader = viewHolder.tvHeader;
        ImageView ivImage = viewHolder.ivImage;
        TextView tvText = viewHolder.tvText;

        tvHeader.setText(sessionItem.first);
        ivImage.setImageResource(R.drawable.ic_timer_white_24dp);

        SimpleDateFormat defaultDateFormat = new SimpleDateFormat("00:00:00", Locale.US);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss", Locale.US);
        String currentDateTimeString = defaultDateFormat.format(new Date(0));
        tvText.setText(currentDateTimeString);
    }

    @Override
    public int getItemCount() {
        return SessionLog.getFieldCount();
    }

    View getLayout(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.session_log_listitem, parent, false);
    }

    Pair<Integer, ?> getItemAtPosition(int position) {
        switch (position) {
            case 0:
                return new Pair<>(R.string.session_date, sessionLog.getSessionDate());
            case 1:
                return new Pair<>(R.string.session_length, sessionLog.getSessionLength());
            case 2:
                return new Pair<>(R.string.session_goal, sessionLog.getSessionGoal());
            case 3:
                return new Pair<>(R.string.active_work, sessionLog.getActiveWork());
            case 4:
                return new Pair<>(R.string.rest_time, sessionLog.getRestTime());
            case 5:
                return new Pair<>(R.string.sets_completed, sessionLog.getSetsCompleted());
            case 6:
                return new Pair<>(R.string.reps_completed, sessionLog.getRepsCompleted());
            case 7:
                return new Pair<>(R.string.success_rate, sessionLog.getSuccessRate());
            case 8:
                return new Pair<>(R.string.success_record, sessionLog.getSuccessRecord());
            default:
                return null;
        }
    }

    static class SessionLogViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView tvHeader;
        ImageView ivImage;
        TextView tvText;

        SessionLogViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            tvHeader = (TextView) itemView.findViewById(R.id.tvHeader);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
        }
    }
}
