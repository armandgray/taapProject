package com.armandgray.taap.detail.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;

import com.armandgray.taap.R;

public class TimerDialog extends DialogFragment {

    private TimerDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (TimerDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement TimerDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alertDialog  = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Window window = getDialog().getWindow();
                if (window == null) { return; }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT);
                window.setBackgroundDrawable(null);
                window.setContentView(R.layout.timer_dialog_layout);
                Chronometer chronometer = (Chronometer) window.findViewById(R.id.chronometer);
                chronometer.start();
            }
        });
        return alertDialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        listener.onTimerDismiss();
    }

    public interface TimerDialogListener {
        void onTimerDismiss();
    }

}
