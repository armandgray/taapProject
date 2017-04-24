package com.armandgray.taap.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.armandgray.taap.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class RecyclerItemClickListenerTest {

    @Mock
    Context context;
    private View resultView;
    private int resultPosition = -1;

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

    @Test
    public void implementsOnItemTouchListener() throws Exception {
        RecyclerView.OnItemTouchListener clickListener = new RecyclerItemClickListener(context,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {}
                });
        assertNotNull(clickListener);
    }

    @Test
    public void doesPassViewFromTouchListenerToItemClickInterfaceMethod() throws Exception {
        RecyclerItemClickListener clickListener = new RecyclerItemClickListener(context,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        resultView = view;
                        resultPosition = position;
                    }
                });
        RecyclerView rv = Mockito.mock(RecyclerView.class);
        MotionEvent motionEvent = MotionEvent.obtain(200, 300, MotionEvent.ACTION_UP, 10.0f, 10.0f, 0);
        clickListener.onInterceptTouchEvent(rv, motionEvent);

        assertNotNull(resultView);
        assertTrue(resultPosition != -1);
    }

}