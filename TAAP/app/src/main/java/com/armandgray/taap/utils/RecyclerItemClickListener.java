package com.armandgray.taap.utils;

import android.content.Context;
import android.view.View;

public class RecyclerItemClickListener {

    private final OnItemClickListener listener;

    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
