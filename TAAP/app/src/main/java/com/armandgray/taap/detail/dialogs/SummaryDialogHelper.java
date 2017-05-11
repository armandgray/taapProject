package com.armandgray.taap.detail.dialogs;

import android.app.Activity;
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

    public SummaryDialogHelper(DetailSummaryDialog dialog) {
        this.dialog = dialog;
    }

    void setupRvSummary(Activity activity, RecyclerView rvSummary) {
        if (dialog.getArguments() == null || dialog.getArguments().get(SESSION_LOG) == null) {
            Toast.makeText(dialog.getActivity(), "Missing Required Session Log!",
                    Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        }
        SessionLog sessionLog = (SessionLog) dialog.getArguments().get(SESSION_LOG);
        rvSummary.setAdapter(new SessionLogRvAdapter(sessionLog));
        GridLayoutManager layoutManager = getGridLayoutManager(activity);
        rvSummary.setLayoutManager(layoutManager);
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
