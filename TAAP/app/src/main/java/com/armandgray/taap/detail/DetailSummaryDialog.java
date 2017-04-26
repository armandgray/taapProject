package com.armandgray.taap.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.armandgray.taap.R;
import com.armandgray.taap.models.SessionLog;

import java.util.Date;

public class DetailSummaryDialog extends DialogFragment {

    public static final String DIALOG = "DIALOG";
    Activity activity;
    private RecyclerView rvSummary;
    public DetailSummaryDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DetailSummaryDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DetailSummaryDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        @SuppressLint("InflateParams") View dialogLayout = LayoutInflater.from(getActivity())
                .inflate(R.layout.detail_summary_dialog_layout, null);
        rvSummary = (RecyclerView) dialogLayout.findViewById(R.id.rvSummary);
        builder.setView(dialogLayout)
                .setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogDismiss();
                    }
                });
        setupRvSummary();
        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        listener.onDialogDismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        listener.onDialogDismiss();
    }

    private void setupRvSummary() {
        SessionLog sessionLog = new SessionLog.Builder()
                .sessionLength(new Date(0))
                .sessionGoal(new Date(0))
                .activeWork(new Date(0))
                .restTime(new Date(0))
                .setsCompleted(0)
                .repsCompleted(0)
                .successRate(0.0)
                .successRecord(0.0)
                .create();
//        rvSummary.setAdapter(new SessionLogAdapter(sessionLog));
        rvSummary.setLayoutManager(
                new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
    }

    public interface DetailSummaryDialogListener {
        void onDialogDismiss();
    }
}
