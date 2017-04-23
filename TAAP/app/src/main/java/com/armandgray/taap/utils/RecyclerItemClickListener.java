package com.armandgray.taap.utils;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.view.GestureDetector;
import android.view.View;

public class RecyclerItemClickListener {

    private final OnItemClickListener listener;
    @VisibleForTesting GestureDetector gestureDetector;

    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
