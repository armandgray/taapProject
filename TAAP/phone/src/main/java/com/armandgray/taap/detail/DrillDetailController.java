package com.armandgray.taap.detail;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import com.armandgray.taap.R;
import com.armandgray.taap.detail.dialogs.DetailSummaryDialog;
import com.armandgray.taap.detail.dialogs.TimerDialog;
import com.armandgray.taap.log.LogActivity;
import com.armandgray.taap.models.SessionLog;

import java.util.ArrayList;
import java.util.List;

import static com.armandgray.taap.db.DatabaseContentProvider.ALL_TABLE_COLUMNS;
import static com.armandgray.taap.db.DatabaseContentProvider.CONTENT_URI_ALL;
import static com.armandgray.taap.db.DatabaseContentProvider.insertLogToDatabase;
import static com.armandgray.taap.detail.dialogs.DetailSummaryDialog.DIALOG;
import static com.armandgray.taap.db.CursorDataHelper.addAllLogsForQuery;
import static com.armandgray.taap.utils.DateTimeHelper.getTimeElapsedAsDate;

class DrillDetailController implements DrillDetailViews.DrillDetailViewsListener {

    DrillDetailActivity activity;
    DrillDetailViews views;
    @VisibleForTesting boolean drillActive;
    private long timeElapsed;
    @VisibleForTesting long activeWorkTime;
    @VisibleForTesting long restTime;
    @VisibleForTesting SessionLog sessionLog;
    private ArrayList<SessionLog> listAllLogs;
    @VisibleForTesting int setsCompleted;
    @VisibleForTesting int repsCompleted;
    @VisibleForTesting double successRate;
    private double placeholderRate;

    DrillDetailController(DrillDetailActivity activity) {
        this.activity = activity;
        this.views = new DrillDetailViews(activity, this);

        views.setupActivityInitialState();

        listAllLogs = new ArrayList<>();
        addAllCursorDrillData();
        views.setupRvPreviousLogs(listAllLogs);
        views.setupRvCurrentLog();
    }

    private void addAllCursorDrillData() {
        Cursor cursor = getCursorAllLogsForDrill();
        if (cursor == null) { return; }

        addAllLogsForQuery(listAllLogs, cursor);
        cursor.close();
    }

    private Cursor getCursorAllLogsForDrill() {
        if (views == null || views.drill == null) {
            return null;
        }

        int drillId = views.drill.getId();
        String[] selectionArgs = {String.valueOf(drillId)};
        Uri uri = Uri.parse(CONTENT_URI_ALL + "/" + drillId);
        return activity.getContentResolver()
                .query(uri, ALL_TABLE_COLUMNS, null, selectionArgs, null);
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
            beforeRestBegins(currentTimeMillis);
        } else {
            beforeActiveSetBegins(currentTimeMillis);
        }
        timeElapsed = currentTimeMillis;
    }

    private void beforeRestBegins(long currentTimeMillis) {
        drillActive = false;
        activeWorkTime += timeElapsed == currentTimeMillis ? 0 : timeElapsed;
        if (views.npSets.getValue() > 1 ) { views.npSets.setValue(views.npSets.getValue() - 1); }
        views.fab.setImageResource(R.drawable.ic_play_arrow_white_24dp);
        Toast.makeText(activity, activity.getString(R.string.rest_time_started),
                Toast.LENGTH_LONG).show();
    }

    private void beforeActiveSetBegins(long currentTimeMillis) {
        if (!isFirstSet(currentTimeMillis)) {
            if (isSuccessesBiggerThanReps()) {
                notifyUserValsAreInvalid();
                return;
            }
            recordSetData();
            addSetToCurrentLogs();
        }

        drillActive = true;
        new TimerDialog().show(activity.getSupportFragmentManager(), DIALOG);
        restTime += timeElapsed == currentTimeMillis ? 0 : timeElapsed;
        views.fab.setImageResource(R.drawable.ic_pause_white_24dp);

    }

    private void notifyUserValsAreInvalid() {
        String alert = activity.getString(R.string.invalid_successes);
        Toast.makeText(activity, alert, Toast.LENGTH_SHORT).show();
    }

    private boolean isSuccessesBiggerThanReps() {
        return views.npReps.getValue() < views.npSuccesses.getValue();
    }

    private boolean isFirstSet(long currentTimeMillis) {
        return timeElapsed == currentTimeMillis;
    }

    private void recordSetData() {
        setsCompleted++;
        repsCompleted += views.npReps.getValue();
        double currentSuccessRate = views.npSuccesses.getValue() * 1.0 / views.npReps.getValue();
        successRate = getAverage(successRate, currentSuccessRate);
    }

    private double getAverage(double successRate, double currentSuccessRate) {
        Double avg = (successRate * (setsCompleted - 1) + currentSuccessRate) / setsCompleted;
        return Math.floor(avg * 100) / 100;
    }

    private void addSetToCurrentLogs() {
        views.adapterPrevLogs.addLog(new SessionLog.Builder()
                .setsCompleted(1)
                .repsCompleted(views.npReps.getValue())
                .successRate(successRate)
                .create());
    }

    @Override
    public void onBtnFinishedClick(View v) {
        if (drillActive) { togglePausePlay(); }
        placeholderRate = successRate;
        recordSetData();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        sessionLog = new SessionLog.Builder()
                .sessionLength(getTimeElapsedAsDate(activeWorkTime + restTime, 16))
                .sessionGoal("None")
                .activeWork(getTimeElapsedAsDate(activeWorkTime, 16))
                .restTime(getTimeElapsedAsDate(restTime, 16))
                .setsCompleted(setsCompleted)
                .repsCompleted(repsCompleted)
                .successRate(successRate)
                .successRecord(getMaxSuccessRate(listAllLogs))
                .drill(views.drill)
                .create();
        DetailSummaryDialog.newInstance(sessionLog).show(fragmentManager, DIALOG);
    }

    private double getMaxSuccessRate(List<SessionLog> listAllLogs) {
        double max = successRate;
        for (SessionLog log : listAllLogs) {
            if (log.getSuccessRate() > max) { max = log.getSuccessRate(); }
        }

        return max;
    }

    void onTimerDismiss() {
        togglePausePlay();
    }

    void onDialogContinue() {
        if (sessionLog != null) { insertLogToDatabase(sessionLog, activity); }
        activity.startActivity(new Intent(activity, LogActivity.class));
        activity.finish();
    }

    void onDialogDismiss() {
        resetSetData();
    }

    private void resetSetData() {
        setsCompleted -= 1;
        repsCompleted -= views.npReps.getValue();
        successRate = placeholderRate;
    }
}
