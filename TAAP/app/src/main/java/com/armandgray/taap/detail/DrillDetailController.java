package com.armandgray.taap.detail;

import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.armandgray.taap.LogActivity;
import com.armandgray.taap.R;

import java.util.Calendar;
import java.util.Date;

import static com.armandgray.taap.detail.DetailSummaryDialog.DIALOG;

class DrillDetailController implements DrillDetailViews.DrillDetailViewsListener {

    DrillDetailActivity activity;
    DrillDetailViews views;
    private boolean drillActive;

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
        if (drillActive) {
            views.fab.setImageResource(R.drawable.ic_play_arrow_white_24dp);
            drillActive = false;
        } else {
            views.fab.setImageResource(R.drawable.ic_pause_white_24dp);
            drillActive = true;
        }
    }

    @Override
    public void onBtnFinishedClick(View v) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        new DetailSummaryDialog().show(fragmentManager, DIALOG);
    }

    @VisibleForTesting
    Date getTimeElapsed(long startTime, long endTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, 0, 0, 0);
        Date time = calendar.getTime();
        time.setHours(0);
        System.out.println(time);
        if (endTime != startTime) {
            time.setTime(endTime - startTime);
        }
        System.out.println(time);
        return time;
    }
}
