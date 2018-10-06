package com.armandgray.taap.detail.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.view.View;
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
                setupDialogViews();
            }
        });
        return alertDialog;
    }

    private void setupDialogViews() {
        Window window = getDialog().getWindow();
        if (window == null) { return; }
        setWindowLayout(window);
        ((Chronometer) window.findViewById(R.id.chronometer)).start();
        (window.findViewById(R.id.fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }

    private void setWindowLayout(Window window) {
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(null);
        window.setContentView(R.layout.timer_dialog_layout);
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
