package com.armandgray.taap.log;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.core.util.Pair;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.armandgray.taap.R;
import com.armandgray.taap.db.LogsDataModel;
import com.armandgray.taap.utils.ActivitySetupHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.armandgray.taap.db.LogsDataModel.LogDataContainer.TOTAL_ACTIVE_WORK;
import static com.armandgray.taap.db.LogsDataModel.LogDataContainer.TOTAL_EXERCISES_COMPLETED;
import static com.armandgray.taap.db.LogsDataModel.LogDataContainer.TOTAL_REPS_COMPLETED;
import static com.armandgray.taap.db.LogsDataModel.LogDataContainer.TOTAL_REST_TIME;
import static com.armandgray.taap.db.LogsDataModel.LogDataContainer.TOTAL_SESSION_TIME;
import static com.armandgray.taap.models.Drill.BALL_HANDLING;
import static com.armandgray.taap.models.Drill.CONDITIONING;
import static com.armandgray.taap.models.Drill.DEFENSE;
import static com.armandgray.taap.models.Drill.FUNDAMENTALS;
import static com.armandgray.taap.models.Drill.OFFENSE;
import static com.armandgray.taap.models.Drill.SHOOTING;

public class LogActivityViews implements ActivitySetupHelper.ActivityViewsInterface {

    private static final String ZERO_TIME = "00:00:00";
    private static final String ZERO_PERCENT = "0%";

    @VisibleForTesting final View rootView;
    @VisibleForTesting final Toolbar toolbar;
    private LinearLayout layoutTotalSessionTime;
    private LinearLayout layoutTotalActiveTime;
    private LinearLayout layoutTotalRestTime;
    private LinearLayout layoutExercisesCompleted;
    private LinearLayout layoutRepsCompleted;
    private TextView tvDate;
    private LinearLayout layoutFundamentals;
    private LinearLayout layoutDefense;
    private LinearLayout layoutOffense;
    private LinearLayout layoutConditioning;
    private LinearLayout layoutShooting;
    private LinearLayout layoutBallHandling;

    LogActivityViews(@NonNull View rootView, @NonNull Toolbar toolbar) {
        this.rootView = rootView;
        this.toolbar = toolbar;
    }

    @Override
    public void setListener(Object object) {}

    @Override
    public void setupActivityCoordinatorWidgets() {
        ((TextView) toolbar.findViewById(R.id.tvTitle)).setText(R.string.session_history);
    }

    @Override
    public void setupActivityInitialState() {
        assignFields();
        setupDetailItems();
        setupRecordItems();
    }

    private void assignFields() {
        layoutTotalSessionTime = (LinearLayout) rootView.findViewById(R.id.layoutTotalSessionTime);
        layoutTotalActiveTime = (LinearLayout) rootView.findViewById(R.id.layoutTotalActiveTime);
        layoutTotalRestTime = (LinearLayout) rootView.findViewById(R.id.layoutTotalRestTime);
        layoutExercisesCompleted = (LinearLayout) rootView.findViewById(R.id.layoutExercisesCompleted);
        layoutRepsCompleted = (LinearLayout) rootView.findViewById(R.id.layoutRepsCompleted);

        LinearLayout RecordsContainer = (LinearLayout) rootView.findViewById(R.id.recordsContainer);
        tvDate = (TextView) RecordsContainer.findViewById(R.id.tvDate);
        layoutFundamentals = (LinearLayout) RecordsContainer.findViewById(R.id.layoutFundamentals);
        layoutDefense = (LinearLayout) RecordsContainer.findViewById(R.id.layoutDefense);
        layoutOffense = (LinearLayout) RecordsContainer.findViewById(R.id.layoutOffense);
        layoutConditioning = (LinearLayout) RecordsContainer.findViewById(R.id.layoutConditioning);
        layoutShooting = (LinearLayout) RecordsContainer.findViewById(R.id.layoutShooting);
        layoutBallHandling = (LinearLayout) RecordsContainer.findViewById(R.id.layoutBallHandling);
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
        setTextForRecordLayoutViews(layoutFundamentals, R.drawable.ic_key_white_48dp, R.string.fundamentals);
        setTextForRecordLayoutViews(layoutDefense, R.drawable.ic_account_multiple_outline_white_48dp, R.string.defense);
        setTextForRecordLayoutViews(layoutOffense, R.drawable.ic_human_handsup_white_48dp, R.string.offense);
        setTextForRecordLayoutViews(layoutConditioning, R.drawable.ic_run_fast_white_48dp, R.string.conditioning);
        setTextForRecordLayoutViews(layoutShooting, R.drawable.ic_dribbble_white_48dp, R.string.shooting);
        setTextForRecordLayoutViews(layoutBallHandling, R.drawable.ic_gesture_two_double_tap_white_48dp, R.string.ball_handling);
    }

    private void setTextForRecordLayoutViews(LinearLayout layout, int imageResId, int headerResId) {
        ImageView ivImage = (ImageView) layout.findViewById(R.id.ivImage);
        TextView tvTime = (TextView) layout.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) layout.findViewById(R.id.tvSuccessRate);
        TextView tvHeader = (TextView) layout.findViewById(R.id.tvHeader);

        ivImage.setImageResource(imageResId);
        ivImage.setTag(imageResId);
        tvTime.setText(ZERO_TIME);
        tvSuccessRate.setText(ZERO_PERCENT);
        tvHeader.setText(headerResId);
    }

    @Override
    public void updateData(Object object) {
        if (object == null) {
            return;
        }

        LogsDataModel.LogDataContainer logDataContainer;
        try {
            logDataContainer = (LogsDataModel.LogDataContainer) object;
        } catch (ClassCastException e) {
            throw new ClassCastException(this.getClass().getName()
                    + " updateData() override method must receive LogsDataModel.LogDataContainer");
        }

        HashMap<String, String> detailsDataMap = logDataContainer.getDetailsDataMap();
        HashMap<String, Pair<String, String>> categoriesDataMap = logDataContainer.getCategoriesDataMap();
        ((TextView) layoutTotalSessionTime.findViewById(R.id.tvText)).setText(detailsDataMap.get(TOTAL_SESSION_TIME));
        ((TextView) layoutTotalActiveTime.findViewById(R.id.tvText)).setText(detailsDataMap.get(TOTAL_ACTIVE_WORK));
        ((TextView) layoutTotalRestTime.findViewById(R.id.tvText)).setText(detailsDataMap.get(TOTAL_REST_TIME));
        ((TextView) layoutRepsCompleted.findViewById(R.id.tvText)).setText(detailsDataMap.get(TOTAL_REPS_COMPLETED));
        ((TextView) layoutExercisesCompleted.findViewById(R.id.tvText)).setText(detailsDataMap.get(TOTAL_EXERCISES_COMPLETED));

        setDataValuesForRecordLayout(layoutFundamentals, categoriesDataMap.get(FUNDAMENTALS));
        setDataValuesForRecordLayout(layoutDefense, categoriesDataMap.get(DEFENSE));
        setDataValuesForRecordLayout(layoutOffense, categoriesDataMap.get(OFFENSE));
        setDataValuesForRecordLayout(layoutConditioning, categoriesDataMap.get(CONDITIONING));
        setDataValuesForRecordLayout(layoutShooting, categoriesDataMap.get(SHOOTING));
        setDataValuesForRecordLayout(layoutBallHandling, categoriesDataMap.get(BALL_HANDLING));
    }

    private void setDataValuesForRecordLayout(LinearLayout layout, Pair<String, String> data) {
        TextView tvTime = (TextView) layout.findViewById(R.id.tvTime);
        TextView tvSuccessRate = (TextView) layout.findViewById(R.id.tvSuccessRate);

        tvTime.setText(data.first);
        tvSuccessRate.setText(data.second);
    }
}
