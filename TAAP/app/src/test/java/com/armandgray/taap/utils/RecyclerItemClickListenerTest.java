package com.armandgray.taap.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.models.Drill;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class RecyclerItemClickListenerTest {

    @Mock
    Context context;
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
                        resultPosition = position;
                    }
                });
        RecyclerView recyclerView = Mockito.mock(RecyclerView.class);
        ArrayList<Drill> drills = new ArrayList<>();

        DrillsRvAdapter adapter = Mockito.mock(DrillsRvAdapter.class);
        adapter.drillList = drills;
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(clickListener);

        MotionEvent motionEvent1 = MotionEvent.obtain(200, 300, MotionEvent.ACTION_DOWN, 0.001f, 0.001f, 0);
        MotionEvent motionEvent2 = MotionEvent.obtain(200, 300, MotionEvent.ACTION_MOVE, 0.001f, 0.0012f, 0);
        MotionEvent motionEvent3 = MotionEvent.obtain(200, 300, MotionEvent.ACTION_MOVE, 0.0012f, 0.001f, 0);
        MotionEvent motionEvent4 = MotionEvent.obtain(200, 300, MotionEvent.ACTION_UP, 0.001f, 0.001f, 0);
        clickListener.onInterceptTouchEvent(recyclerView, motionEvent1);
        clickListener.onInterceptTouchEvent(recyclerView, motionEvent2);
        clickListener.onInterceptTouchEvent(recyclerView, motionEvent3);
        clickListener.onInterceptTouchEvent(recyclerView, motionEvent4);
        assertTrue(resultPosition != -1);
    }

}
