package com.armandgray.taap.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.armandgray.taap.R;

class DetailSummaryDialog {

    Activity activity;

    public DetailSummaryDialog(Activity activity) {
        this.activity = activity;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        @SuppressLint("InflateParams") View dialogLayout = LayoutInflater.from(activity)
                .inflate(R.layout.detail_summary_dialog_layout, null);
        builder.setView(dialogLayout);
        return builder.create();
    }
}
