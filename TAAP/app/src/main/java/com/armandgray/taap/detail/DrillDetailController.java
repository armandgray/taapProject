package com.armandgray.taap.detail;

import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import com.armandgray.taap.R;
import com.armandgray.taap.log.LogActivity;
import com.armandgray.taap.models.SessionLog;

import static com.armandgray.taap.detail.DetailSummaryDialog.DIALOG;
import static com.armandgray.taap.log.LogActivity.SESSION_LOG;
import static com.armandgray.taap.utils.DateTimeHelper.getTimeElapsedAsDate;

class DrillDetailController implements DrillDetailViews.DrillDetailViewsListener {

    DrillDetailActivity activity;
    DrillDetailViews views;
    @VisibleForTesting boolean drillActive;
    private long timeElapsed;
    @VisibleForTesting long activeWorkTime;
    @VisibleForTesting long restTime;
    @VisibleForTesting SessionLog sessionLog;

    DrillDetailController(DrillDetailActivity activity) {
        this.activity = activity;
        this.views = new DrillDetailViews(activity, this);
    }

    @Override
    public void onFabClick(View v) {
        views.btnFinished.setVisibility(View.VISIBLE);
        views.fab.setBackgroundTintList(activity.getResources().getColorStateList(
                android.R.color.white));
        togglePausePlay();
    }

    private void togglePausePlay() {
        long currentTimeMillis = System.currentTimeMillis();
        timeElapsed = currentTimeMillis - timeElapsed;
        if (drillActive) {
            activeWorkTime += timeElapsed == currentTimeMillis ? 0 : timeElapsed;
            views.fab.setImageResource(R.drawable.ic_play_arrow_white_24dp);
            drillActive = false;
        } else {
            new TimerDialog().show(activity.getSupportFragmentManager(), DIALOG);
            restTime += timeElapsed == currentTimeMillis ? 0 : timeElapsed;
            views.fab.setImageResource(R.drawable.ic_pause_white_24dp);
            drillActive = true;
        }
        timeElapsed = currentTimeMillis;
    }

    @Override
    public void onBtnFinishedClick(View v) {
        if (drillActive) { togglePausePlay(); }
        if (views.npReps.getValue() < views.npSuccesses.getValue()) {
            String alert = activity.getString(R.string.invalid_successes);
            Toast.makeText(activity, alert, Toast.LENGTH_SHORT).show();
            return;
        }
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        sessionLog = new SessionLog.Builder()
                .sessionLength(getTimeElapsedAsDate(activeWorkTime + restTime, 16))
                .sessionGoal("None")
                .activeWork(getTimeElapsedAsDate(activeWorkTime, 16))
                .restTime(getTimeElapsedAsDate(restTime, 16))
                .setsCompleted(views.npSets.getValue())
                .repsCompleted(views.npReps.getValue())
                .successRate(getRateFromPickers())
                .successRecord(getRateFromPickers())
                .drill(views.drill)
                .create();
        DetailSummaryDialog.newInstance(sessionLog).show(fragmentManager, DIALOG);
    }

    private double getRateFromPickers() {
        int reps = views.npReps.getValue();
        return reps == 0
                ? views.npSuccesses.getValue() * 1.0 / views.npSets.getValue()
                : views.npSuccesses.getValue() * 1.0 / (reps * views.npSets.getValue());
    }

    public void onTimerDismiss() {

    }

    void onSummaryDialogDismiss() {
        Intent intent = new Intent(activity, LogActivity.class);
        intent.putExtra(SESSION_LOG, sessionLog);
        activity.startActivity(intent);
        activity.finish();
    }
}
