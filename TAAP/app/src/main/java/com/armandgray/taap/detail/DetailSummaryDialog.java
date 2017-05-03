package com.armandgray.taap.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.armandgray.taap.R;
import com.armandgray.taap.models.SessionLog;
import com.armandgray.taap.utils.SessionLogRvAdapter;

public class DetailSummaryDialog extends DialogFragment {

    public static final String DIALOG = "DIALOG";
    public static final String SESSION_LOG = "SESSION_LOG";
    Activity activity;
    private RecyclerView rvSummary;
    public DetailSummaryDialogListener listener;

    public static DetailSummaryDialog newInstance(SessionLog sessionLog) {
        Bundle args = new Bundle();
        DetailSummaryDialog fragment = new DetailSummaryDialog();
        args.putParcelable(SESSION_LOG, sessionLog);
        fragment.setArguments(args);
        return fragment;
    }

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

    @NonNull
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

    private void setupRvSummary() {
        if (getArguments() == null || getArguments().get(SESSION_LOG) == null) {
            Toast.makeText(activity, "Missing Required Session Log!", Toast.LENGTH_SHORT).show();
            this.dismiss();
            return;
        }
        SessionLog sessionLog = (SessionLog) getArguments().get(SESSION_LOG);
        rvSummary.setAdapter(new SessionLogRvAdapter(sessionLog));
        GridLayoutManager layoutManager = getGridLayoutManager();
        rvSummary.setLayoutManager(layoutManager);
    }

    @NonNull
    private GridLayoutManager getGridLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(activity, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });
        return layoutManager;
    }

    interface DetailSummaryDialogListener {
        void onDialogDismiss();
    }
}
