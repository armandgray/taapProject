package com.armandgray.taap.utils;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.armandgray.taap.R;
import com.armandgray.taap.models.SessionLog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class SessionLogRvAdapter extends RecyclerView.Adapter<SessionLogRvAdapter.SessionLogViewHolder> {

    static final int TYPE_HEADER = 100;
    static final int TYPE_ITEM = 101;
    @VisibleForTesting
    static final String IMAGE_RESOURCE_ID = "IMAGE_RESOURCE_ID";
    @VisibleForTesting
    static final String ITEM_DATA = "ITEM_DATA";
    @VisibleForTesting
    static final String STRING_RESOURCE_ID = "STRING_RESOURCE_ID";
    private static final String TINT_COLOR = "TINT_COLOR";

    private SessionLog sessionLog;
    private ViewGroup parent;

    SessionLogRvAdapter() {
    }

    public SessionLogRvAdapter(SessionLog sessionLog) {
        this.sessionLog = sessionLog;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public SessionLogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        if (viewType == TYPE_HEADER) {
            return new SessionLogHeaderViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.session_log_header_layout, parent, false));
        }
        return new SessionLogViewHolder(getLayout(parent));
    }

    @Override
    public void onBindViewHolder(SessionLogViewHolder viewHolder, int position) {
        final HashMap<String, Object> sessionItem = getItemAtPosition(position);

        if (atHeaderPosition(viewHolder, position)) {
            setupHeader((SessionLogHeaderViewHolder) viewHolder, sessionItem);
            return;
        }

        TextView tvHeader = viewHolder.tvHeader;
        ImageView ivImage = viewHolder.ivImage;
        TextView tvText = viewHolder.tvText;

        tvHeader.setText((Integer) sessionItem.get(STRING_RESOURCE_ID));
        ivImage.setImageResource((Integer) sessionItem.get(IMAGE_RESOURCE_ID));
        ivImage.setColorFilter(parent.getResources()
                .getColor((Integer) sessionItem.get(TINT_COLOR)));
        setTvText(position, sessionItem, tvText);
    }

    private boolean atHeaderPosition(SessionLogViewHolder viewHolder, int position) {
        return position == 0 && viewHolder instanceof SessionLogHeaderViewHolder;
    }

    private void setupHeader(SessionLogHeaderViewHolder viewHolder, HashMap<String, Object> sessionItem) {
        TextView tvText = viewHolder.tvText;
        Date date = (Date) sessionItem.get(ITEM_DATA);
        tvText.setText(new SimpleDateFormat("EEE, MMM d, ''yy", Locale.US).format(date));
    }

    private void setTvText(int position, HashMap<String, Object> sessionItem, TextView tvText) {
        Object itemData = sessionItem.get(ITEM_DATA);

        if (position <= 4) {
            tvText.setText(getFormattedTimeAsString(itemData));
        } else if (position <= 6) {
            tvText.setText(String.valueOf(itemData));
        } else if (position <= 8) {
            Double rate = (Double) itemData * 100;
            String text = rate.intValue() + "%";
            tvText.setText(text);
        }
    }

    private String getFormattedTimeAsString(Object itemData) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime((Date) itemData);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        SimpleDateFormat simpleDateFormat =
                hour == 0
                        ? new SimpleDateFormat("00:mm:ss", Locale.US)
                        : new SimpleDateFormat("hh:mm:ss", Locale.US);
        return simpleDateFormat.format(calendar.getTime());
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
                return getHashMap(R.string.session_date, sessionLog.getSessionDate(), R.drawable.ic_timer_white_24dp, android.R.color.holo_red_dark);
            case 1:
                return getHashMap(R.string.session_length, sessionLog.getSessionLength(), R.drawable.ic_timer_white_24dp, android.R.color.holo_green_dark);
            case 2:
                return getHashMap(R.string.session_goal, sessionLog.getSessionGoal(), R.drawable.ic_fast_forward_white_24dp, android.R.color.holo_green_dark);
            case 3:
                return getHashMap(R.string.active_work, sessionLog.getActiveWork(), R.drawable.ic_add_alarm_white_24dp, android.R.color.holo_green_dark);
            case 4:
                return getHashMap(R.string.rest_time, sessionLog.getRestTime(), R.drawable.ic_battery_charging_20_white_24dp, android.R.color.holo_red_dark);
            case 5:
                return getHashMap(R.string.sets_completed, sessionLog.getSetsCompleted(), R.drawable.ic_fitness_center_white_24dp, R.color.colorPrimary);
            case 6:
                return getHashMap(R.string.reps_completed, sessionLog.getRepsCompleted(), R.drawable.ic_fitness_center_white_24dp, R.color.colorPrimaryDark);
            case 7:
                return getHashMap(R.string.success_rate, sessionLog.getSuccessRate(), R.drawable.ic_star_white_24dp, android.R.color.holo_green_dark);
            case 8:
                return getHashMap(R.string.success_record, sessionLog.getSuccessRecord(), R.drawable.ic_whatshot_white_24dp, android.R.color.holo_green_dark);
            default:
                return null;
        }
    }

    @NonNull
    private HashMap<String, Object> getHashMap(Integer stringResId, Object obj, Integer imageResId, int imgTintColorId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(STRING_RESOURCE_ID, stringResId);
        hashMap.put(ITEM_DATA, obj);
        hashMap.put(IMAGE_RESOURCE_ID, imageResId);
        hashMap.put(TINT_COLOR, imgTintColorId);
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
