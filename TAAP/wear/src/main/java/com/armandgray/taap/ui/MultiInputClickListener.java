package com.armandgray.taap.ui;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class MultiInputClickListener implements View.OnTouchListener {

    private final OnMultiInputClickListener listener;
    private boolean skipActionUp;

    public MultiInputClickListener(OnMultiInputClickListener listener) {
        this.listener = listener;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public synchronized boolean onTouch(View view, MotionEvent event) {
        if (!isTouchWithinView(view, event)) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_POINTER_UP:
                listener.onDoubleInputClick(view);
                skipActionUp = true;
                break;

            case MotionEvent.ACTION_UP:
                if (!skipActionUp) {
                    listener.onSingleInputClick(view);
                }

                break;

            case MotionEvent.ACTION_DOWN:
                skipActionUp = false;
                break;
        }

        return true;
    }

    private boolean isTouchWithinView(View view, MotionEvent event) {
        Rect rect = new Rect();
        view.getHitRect(rect);
        return rect.contains(Math.round(event.getX()), Math.round(event.getY()));
    }

    public interface OnMultiInputClickListener {

        void onSingleInputClick(View view);

        void onDoubleInputClick(View view);
    }
}
