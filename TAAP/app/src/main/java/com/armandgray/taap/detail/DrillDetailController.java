package com.armandgray.taap.detail;

import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.armandgray.taap.LogActivity;
import com.armandgray.taap.R;
import com.armandgray.taap.models.SessionLog;

import java.util.Calendar;
import java.util.Date;

import static com.armandgray.taap.detail.DetailSummaryDialog.DIALOG;

class DrillDetailController implements DrillDetailViews.DrillDetailViewsListener {

    DrillDetailActivity activity;
    DrillDetailViews views;
    private boolean drillActive;
    private long timeElapsed;
    @VisibleForTesting long activeWorkTime;
    @VisibleForTesting long restTime;

    DrillDetailController(DrillDetailActivity activity) {
        this.activity = activity;
        this.views = new DrillDetailViews(activity, this);
    }

    void onSummaryDialogDismiss() {
        Intent intent = new Intent(activity, LogActivity.class);
        activity.startActivity(intent);
        activity.finish();
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
        SessionLog sessionLog = new SessionLog.Builder()
                .sessionLength(getTimeElapsed(activeWorkTime + restTime))
                .create();
        DetailSummaryDialog.newInstance(sessionLog).show(fragmentManager, DIALOG);
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
}
