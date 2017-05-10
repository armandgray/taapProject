package com.armandgray.taap.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.armandgray.taap.R;

public class ConfirmClearDataDialog extends DialogFragment {

    private ClearDataListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ClearDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ClearDataListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //                activity.getContentResolver().delete(CONTENT_URI_DELETE_ALL_DATA, null, null);
                        listener.onPositiveClearDataClick();
                    }
                })
                .setNegativeButton(context.getString(R.string.cancel), null);
        return builder.create();
    }

    public interface ClearDataListener {
        void onPositiveClearDataClick();
    }

}
