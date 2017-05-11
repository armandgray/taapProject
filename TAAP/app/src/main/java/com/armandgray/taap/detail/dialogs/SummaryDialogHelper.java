package com.armandgray.taap.detail.dialogs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.armandgray.taap.models.SessionLog;
import com.armandgray.taap.utils.SessionLogRvAdapter;

import static com.armandgray.taap.log.LogActivity.SESSION_LOG;

class SummaryDialogHelper {

    @VisibleForTesting DetailSummaryDialog dialog;

    SummaryDialogHelper(DetailSummaryDialog dialog) {
        this.dialog = dialog;
    }

    DetailSummaryDialog.DetailSummaryDialogListener getDetailActivityAsListener(Context context) {
        DetailSummaryDialog.DetailSummaryDialogListener listener;
        try {
            listener = (DetailSummaryDialog.DetailSummaryDialogListener) context;
            return listener;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DetailSummaryDialogListener");
        }
    }

    void setupRvSummary(Activity activity, RecyclerView rvSummary) {
        Bundle args = dialog.getArguments();
        SessionLog sessionLog = args != null ? (SessionLog) args.get(SESSION_LOG) : null;
        if (isSessionLogNull(sessionLog)) return;
        rvSummary.setAdapter(new SessionLogRvAdapter(sessionLog));
        GridLayoutManager layoutManager = getGridLayoutManager(activity);
        rvSummary.setLayoutManager(layoutManager);
    }

    private boolean isSessionLogNull(SessionLog sessionLog) {
        if (sessionLog == null) {
            Toast.makeText(dialog.getActivity(), "Missing Required Session Log!",
                    Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return true;
        }
        return false;
    }

    private double getDrillSuccessRecord() {
        return 0;
    }

    @NonNull
    private GridLayoutManager getGridLayoutManager(Activity activity) {
        GridLayoutManager layoutManager = new GridLayoutManager(activity, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });
        return layoutManager;
    }
}
