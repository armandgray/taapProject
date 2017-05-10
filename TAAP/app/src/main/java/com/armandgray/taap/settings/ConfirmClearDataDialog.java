package com.armandgray.taap.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.armandgray.taap.R;

class ConfirmClearDataDialog {

    private Context context;

    ConfirmClearDataDialog() {}

    ConfirmClearDataDialog(Context context) {
        this.context = context;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                
            }
        });
        return builder.create();
    }

}
