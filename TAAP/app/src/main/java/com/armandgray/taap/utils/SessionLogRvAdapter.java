package com.armandgray.taap.utils;

import android.support.annotation.NonNull;
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
import java.util.HashMap;
import java.util.Locale;

public class SessionLogRvAdapter extends RecyclerView.Adapter<SessionLogRvAdapter.SessionLogViewHolder> {

    static final int TYPE_HEADER = 100;
    static final int TYPE_ITEM = 101;
    public static final String IMAGE_RESOURCE = "IMAGE_RESOURCE";
    public static final String ITEM_DATA = "ITEM_DATA";
    public static final String STRING_RESOURCE_ID = "STRING_RESOURCE_ID";

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
            return new SessionLogHeaderViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.session_log_header_layout, parent, false));
        }
        return new SessionLogViewHolder(getLayout(parent));
    }

    @Override
    public void onBindViewHolder(SessionLogViewHolder viewHolder, int position) {
        final HashMap<String, Object> sessionItem = getItemAtPosition(position);

        if (position == 0 && viewHolder instanceof SessionLogHeaderViewHolder) {
            SessionLogHeaderViewHolder holder = (SessionLogHeaderViewHolder) viewHolder;

            TextView tvText = holder.tvText;
            Date date = (Date) sessionItem.get(ITEM_DATA);
            tvText.setText(new SimpleDateFormat("EEE, MMM d, ''yy", Locale.US).format(date));
            return;
        }

        TextView tvHeader = viewHolder.tvHeader;
        ImageView ivImage = viewHolder.ivImage;
        TextView tvText = viewHolder.tvText;

        tvHeader.setText((Integer) sessionItem.get(STRING_RESOURCE_ID));

        if (position == 5 || position == 6) {
            tvText.setText(String.valueOf(sessionItem.get(ITEM_DATA)));
            return;
        }

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

    HashMap<String, Object> getItemAtPosition(int position) {
        switch (position) {
            case 0:
                return getHashMap(R.string.session_date, sessionLog.getSessionDate(), R.drawable.ic_timer_white_24dp);
            case 1:
                return getHashMap(R.string.session_length, sessionLog.getSessionLength(), R.drawable.ic_timer_white_24dp);
            case 2:
                return getHashMap(R.string.session_goal, sessionLog.getSessionGoal(), R.drawable.ic_timer_white_24dp);
            case 3:
                return getHashMap(R.string.active_work, sessionLog.getActiveWork(), R.drawable.ic_timer_white_24dp);
            case 4:
                return getHashMap(R.string.rest_time, sessionLog.getRestTime(), R.drawable.ic_timer_white_24dp);
            case 5:
                return getHashMap(R.string.sets_completed, sessionLog.getSetsCompleted(), R.drawable.ic_timer_white_24dp);
            case 6:
                return getHashMap(R.string.reps_completed, sessionLog.getRepsCompleted(), R.drawable.ic_timer_white_24dp);
            case 7:
                return getHashMap(R.string.success_rate, sessionLog.getSuccessRate(), R.drawable.ic_timer_white_24dp);
            case 8:
                return getHashMap(R.string.success_record, sessionLog.getSuccessRecord(), R.drawable.ic_timer_white_24dp);
            default:
                return null;
        }
    }
    
    @NonNull
    private HashMap<String, Object> getHashMap(Integer stringResId, Object obj, Integer imageResId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(STRING_RESOURCE_ID, stringResId);
        hashMap.put(ITEM_DATA, obj);
        hashMap.put(IMAGE_RESOURCE, imageResId);
        return hashMap;
    }

    static class SessionLogHeaderViewHolder extends SessionLogViewHolder {
        View itemView;
        TextView tvText;

        SessionLogHeaderViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            tvText = (TextView) itemView.findViewById(R.id.tvText);
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
