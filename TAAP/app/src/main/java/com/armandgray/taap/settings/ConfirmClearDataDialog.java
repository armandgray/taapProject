package com.armandgray.taap.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

class ConfirmClearDataDialog {

    private Context context;

    ConfirmClearDataDialog() {}

    ConfirmClearDataDialog(Context context) {
        this.context = context;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder.create();
    }

}
