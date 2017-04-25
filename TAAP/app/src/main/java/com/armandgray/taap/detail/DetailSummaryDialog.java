package com.armandgray.taap.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.armandgray.taap.LogActivity;
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
        builder.setView(dialogLayout)
                .setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(activity, LogActivity.class);
                        activity.startActivity(intent);
                    }
                });
        return builder.create();
    }
}
