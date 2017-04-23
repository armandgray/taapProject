package com.armandgray.taap.utils;

import android.content.Context;

public class RecyclerItemClickListener {

    private final OnItemClickListener listener;

    public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
    }
}
