package com.armandgray.taap.detail.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.armandgray.taap.R;
import com.armandgray.taap.models.SessionLog;

import java.util.ArrayList;

import static com.armandgray.taap.log.LogActivity.SESSION_LOG;

public class DetailSummaryDialog extends DialogFragment {

    public static final String DIALOG = "DIALOG";
    public static final String ALL_LOGS = "ALL_LOGS";
    Activity activity;
    public DetailSummaryDialogListener listener;
    @VisibleForTesting
    SummaryDialogHelper helper;

    public static DetailSummaryDialog newInstance(SessionLog sessionLog) {
        Bundle args = new Bundle();
        DetailSummaryDialog fragment = new DetailSummaryDialog();
        args.putParcelable(SESSION_LOG, sessionLog);
        fragment.setArguments(args);
        return fragment;
    }

    public static DetailSummaryDialog newInstance(SessionLog sessionLog, ArrayList<SessionLog> logs) {
        Bundle args = new Bundle();
        DetailSummaryDialog fragment = new DetailSummaryDialog();
        args.putParcelable(SESSION_LOG, sessionLog);
        args.putParcelableArrayList(ALL_LOGS, logs);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        helper = new SummaryDialogHelper(this);
        listener = helper.getDetailActivityAsListener(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View dialogLayout = LayoutInflater.from(getActivity())
                .inflate(R.layout.detail_summary_dialog_layout, null);
        RecyclerView rvSummary = (RecyclerView) dialogLayout.findViewById(R.id.rvSummary);
        helper.setupRvSummary(activity, rvSummary);

        return new AlertDialog.Builder(getActivity()).setView(dialogLayout)
                .setPositiveButton(R.string.continue_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogContinue();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    public interface DetailSummaryDialogListener {
        void onDialogContinue();
    }
}
