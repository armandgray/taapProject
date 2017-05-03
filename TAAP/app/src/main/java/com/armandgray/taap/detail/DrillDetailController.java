package com.armandgray.taap.detail;

import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.armandgray.taap.R;
import com.armandgray.taap.models.SessionLog;

import java.util.Calendar;
import java.util.Date;

import static com.armandgray.taap.detail.DetailSummaryDialog.DIALOG;
import static com.armandgray.taap.detail.DetailSummaryDialog.SESSION_LOG;

class DrillDetailController implements DrillDetailViews.DrillDetailViewsListener {

    DrillDetailActivity activity;
    DrillDetailViews views;
    private boolean drillActive;
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
            restTime += timeElapsed == currentTimeMillis ? 0 : timeElapsed;
            views.fab.setImageResource(R.drawable.ic_pause_white_24dp);
            drillActive = true;
        }
        timeElapsed = currentTimeMillis;
    }

    @Override
    public void onBtnFinishedClick(View v) {
        if (drillActive) { togglePausePlay(); }
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        sessionLog = new SessionLog.Builder()
                .sessionLength(getTimeElapsed(activeWorkTime + restTime))
                .sessionGoal(new Date(0))
                .activeWork(getTimeElapsed(activeWorkTime))
                .restTime(getTimeElapsed(restTime))
                .setsCompleted(views.npSets.getValue())
                .repsCompleted(views.npReps.getValue())
                .successRate(getRateFromPickers())
                .successRecord(getRateFromPickers())
                .create();
        DetailSummaryDialog.newInstance(sessionLog).show(fragmentManager, DIALOG);
    }

    private double getRateFromPickers() {
        int reps = views.npReps.getValue();
        return reps == 0
                ? views.npSuccesses.getValue() * 1.0 / views.npSets.getValue()
                : views.npSuccesses.getValue() * 1.0 / (reps * views.npSets.getValue());
    }

    @VisibleForTesting
    Date getTimeElapsed(long timeElapsed) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, 0, 0, 0);
        if (timeElapsed != 0) { calendar.setTimeInMillis(timeElapsed); }
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour > 12) { calendar.set(Calendar.HOUR_OF_DAY, 0); }
        return calendar.getTime();
    }

    void onSummaryDialogDismiss() {
        Intent intent = new Intent(activity, LogActivity.class);
        intent.putExtra(SESSION_LOG, sessionLog);
        activity.startActivity(intent);
        activity.finish();
    }
}
