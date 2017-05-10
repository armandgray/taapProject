package com.armandgray.taap.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class ConfirmClearDataDialog {

    private Context context;

    public ConfirmClearDataDialog(Context context) {
        this.context = context;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return null;
    }

}
