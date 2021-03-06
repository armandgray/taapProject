package com.armandgray.taap.detail;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.armandgray.taap.detail.dialogs.DetailSummaryDialog;
import com.armandgray.taap.detail.dialogs.TimerDialog;

public class DrillDetailActivity extends AppCompatActivity

        implements DetailSummaryDialog.DetailSummaryDialogListener,
        TimerDialog.TimerDialogListener {

    DrillDetailController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new DrillDetailController(this);
    }

    @Override
    public void onTimerDismiss() {
        controller.onTimerDismiss();
    }

    @Override
    public void onDialogContinue() {
        controller.onDialogContinue();
    }

    @Override
    public void onDialogDismiss() {
        controller.onDialogDismiss();
    }
}