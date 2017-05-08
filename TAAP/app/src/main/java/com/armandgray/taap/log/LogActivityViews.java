package com.armandgray.taap.log;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
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
    LinearLayout layoutFundamentals;
    LinearLayout layoutDefense;
    LinearLayout layoutOffBallOffense;
    LinearLayout layoutConditioning;
    LinearLayout layoutShooting;
    LinearLayout layoutBallHandling;

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

        LinearLayout RecordsContainer = (LinearLayout) activity.findViewById(R.id.recordsContainer);
        tvDate = (TextView) RecordsContainer.findViewById(R.id.tvDate);
        layoutFundamentals = (LinearLayout) RecordsContainer.findViewById(R.id.layoutFundamentals);
        layoutDefense = (LinearLayout) RecordsContainer.findViewById(R.id.layoutDefense);
        layoutOffBallOffense = (LinearLayout) RecordsContainer.findViewById(R.id.layoutOffense);
        layoutConditioning = (LinearLayout) RecordsContainer.findViewById(R.id.layoutConditioning);
        layoutShooting = (LinearLayout) RecordsContainer.findViewById(R.id.layoutShooting);
        layoutBallHandling = (LinearLayout) RecordsContainer.findViewById(R.id.layoutBallHandling);
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
        setTextForDetailLayoutViews(layoutTotalSessionTime, R.string.total_session_time, "00:00:00");
        setTextForDetailLayoutViews(layoutTotalActiveTime, R.string.total_active_time, "00:00:00");
        setTextForDetailLayoutViews(layoutTotalRestTime, R.string.total_rest_time, "00:00:00");
        setTextForDetailLayoutViews(layoutExercisesCompleted, R.string.exercises_completed, "0");
        setTextForDetailLayoutViews(layoutRepsCompleted, R.string.reps_completed, "0");
    }

    private void setTextForDetailLayoutViews(LinearLayout layout, int headerResId, String text) {
        TextView header = (TextView) layout.findViewById(R.id.header);
        TextView tvText = (TextView) layout.findViewById(R.id.tvText);
        header.setText(headerResId);
        tvText.setText(text);
    }

    private void setupRecordItems() {
        String dateString = ">  "
                + new SimpleDateFormat("EEE, MMM d, ''yy", Locale.US).format(new Date())
                + "  <";
        tvDate.setText(dateString);
        setTextForRecordLayoutViews(layoutFundamentals, R.drawable.ic_key_white_48dp,
                "00:00:00", "0%", R.string.fundamentals);
        setTextForRecordLayoutViews(layoutDefense, R.drawable.ic_account_multiple_outline_white_48dp,
                "00:00:00", "0%", R.string.defense);
        setTextForRecordLayoutViews(layoutOffBallOffense, R.drawable.ic_human_handsup_white_48dp,
                "00:00:00", "0%", R.string.off_ball_offense);
        setTextForRecordLayoutViews(layoutConditioning, R.drawable.ic_run_fast_white_48dp,
                "00:00:00", "0%", R.string.conditioning);
        setTextForRecordLayoutViews(layoutShooting, R.drawable.ic_dribbble_white_48dp,
                "00:00:00", "0%", R.string.shooting);
        setTextForRecordLayoutViews(layoutBallHandling,
                R.drawable.ic_gesture_two_double_tap_white_48dp,
                "00:00:00", "0%", R.string.ball_handling);
    }

    private void setTextForRecordLayoutViews(
            LinearLayout layout, int imageResId, String time, String successRate, int headerResId) {
        ImageView ivImage = (ImageView) layout.findViewById(R.id.ivImage);
        TextView tvTime = (TextView) layout.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) layout.findViewById(R.id.tvSuccessRate);
        TextView tvHeader = (TextView) layout.findViewById(R.id.tvHeader);

        ivImage.setImageResource(imageResId);
        tvTime.setText(time);
        tvSuccessRate.setText(successRate);
        tvHeader.setText(headerResId);
    }

    interface LogViewsListener {
    }
}
