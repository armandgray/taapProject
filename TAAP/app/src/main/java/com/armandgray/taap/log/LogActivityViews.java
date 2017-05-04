package com.armandgray.taap.log;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.armandgray.taap.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class LogActivityViews {

    @VisibleForTesting
    LogActivity activity;
    private LogViewsListener listener;
    LinearLayout layoutTotalSessionTime;
    LinearLayout layoutTotalActiveTime;
    LinearLayout layoutTotalRestTime;
    LinearLayout layoutExercisesCompleted;
    LinearLayout layoutRepsCompleted;
    TextView tvDate;

    LogActivityViews(LogActivity activity, LogViewsListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    void setupActivityInitialState() {
        activity.setContentView(R.layout.activity_log);
        setupToolbar();
        assignFields();
        setupDetailItems();
        setupRecordItems();
    }

    private void assignFields() {
        layoutTotalSessionTime = (LinearLayout) activity.findViewById(R.id.layoutTotalSessionTime);
        layoutTotalActiveTime = (LinearLayout) activity.findViewById(R.id.layoutTotalActiveTime);
        layoutTotalRestTime = (LinearLayout) activity.findViewById(R.id.layoutTotalRestTime);
        layoutExercisesCompleted = (LinearLayout) activity.findViewById(R.id.layoutExercisesCompleted);
        layoutRepsCompleted = (LinearLayout) activity.findViewById(R.id.layoutRepsCompleted);
        tvDate = (TextView) activity.findViewById(R.id.recordsContainer).findViewById(R.id.tvDate);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        ((TextView) toolbar.findViewById(R.id.tvTitle)).setText(R.string.session_history);

        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            setHomeAsUpIndicatorColor();
        }
    }

    private void setHomeAsUpIndicatorColor() {
        final Drawable upArrow = activity.getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(activity.getResources().getColor(R.color.colorDarkGray), PorterDuff.Mode.SRC_ATOP);
        if (activity.getSupportActionBar() == null) {
            return;
        }
        activity.getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private void setupDetailItems() {
        setTextForDetailLayoutViews(layoutTotalSessionTime,
                R.string.total_session_time, "00:00:00");
        setTextForDetailLayoutViews(layoutTotalActiveTime,
                R.string.total_active_time, "00:00:00");
        setTextForDetailLayoutViews(layoutTotalRestTime,
                R.string.total_rest_time, "00:00:00");
        setTextForDetailLayoutViews(layoutExercisesCompleted,
                R.string.exercises_completed, "0");
        setTextForDetailLayoutViews(layoutRepsCompleted,
                R.string.reps_completed, "0");
    }

    private void setTextForDetailLayoutViews(LinearLayout layoutTotalSessionTime, int total_session_time, String text) {
        TextView header = (TextView) layoutTotalSessionTime.findViewById(R.id.header);
        TextView tvText = (TextView) layoutTotalSessionTime.findViewById(R.id.tvText);
        header.setText(total_session_time);
        tvText.setText(text);
    }

    private void setupRecordItems() {
        String dateString = ">  "
                + new SimpleDateFormat("EEE, MMM d, ''yy", Locale.US).format(new Date())
                + "  <";
        tvDate.setText(dateString);
    }

    interface LogViewsListener {
    }
}
