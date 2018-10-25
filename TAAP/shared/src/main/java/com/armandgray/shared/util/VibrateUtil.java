package com.armandgray.shared.util;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.Nullable;

@Singleton
public class VibrateUtil {

    @Nullable
    private final Vibrator vibrator;

    @Inject
    VibrateUtil(Context context) {
        this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void vibrate(int lengthInMillis) {
        if (vibrator == null || lengthInMillis < 100) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(lengthInMillis,
                    VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(lengthInMillis);
        }
    }
}
