package com.armandgray.taap.utils;

import android.content.Context;
import android.view.View;

import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.Assert.assertNotNull;

public class RecyclerItemClickListenerTest {

    @Mock
    Context context;

    @Test
    public void canCreateRecyclerItemClickListener_TestConstructor() throws Exception {
        assertNotNull(new RecyclerItemClickListener(context,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {}
                }));
    }

    @Test
    public void doesUseGestureDetectorForClicks_TestConstructor() throws Exception {
        RecyclerItemClickListener clickListener = new RecyclerItemClickListener(context,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {}
                });
        assertNotNull(clickListener);
        assertNotNull(clickListener.gestureDetector);
    }

}