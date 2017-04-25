package com.armandgray.taap.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.armandgray.taap.LogActivity;
import com.armandgray.taap.R;
import com.armandgray.taap.utils.DrillsRvAdapter;

import static com.armandgray.taap.utils.DrillsHelper.getDrillsList;

class DetailSummaryDialog {

    Activity activity;
    private RecyclerView rvSummary;

    public DetailSummaryDialog(Activity activity) {
        this.activity = activity;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        @SuppressLint("InflateParams") View dialogLayout = LayoutInflater.from(activity)
                .inflate(R.layout.detail_summary_dialog_layout, null);
        rvSummary = (RecyclerView) dialogLayout.findViewById(R.id.rvSummary);
        builder.setView(dialogLayout)
                .setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(activity, LogActivity.class);
                        activity.startActivity(intent);
                    }
                });
        setupRvSummary();
        return builder.create();
    }

    private void setupRvSummary() {
        rvSummary.setAdapter(new DrillsRvAdapter(getDrillsList()));
        rvSummary.setLayoutManager(
                new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
    }
}
