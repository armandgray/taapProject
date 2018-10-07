package com.armandgray.shared.ui;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import androidx.recyclerview.widget.RecyclerView;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class RecyclerItemClickListenerTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    RecyclerView mockRecyclerView;

    @Mock
    GestureDetector mockGestureDetector;

    @Mock
    MotionEvent mockMotionEvent;

    @Mock
    View mockChildView;

    private Context context = RuntimeEnvironment.application;
    private int resultPosition = -1;

    @Test
    public void canCreateRecyclerItemClickListener_TestConstructor() {
        RecyclerItemClickListener clickListener = new RecyclerItemClickListener(context,
                (view, position) -> { });
        Assert.assertThat(clickListener, is(notNullValue()));
    }

    @Test
    public void doesUseGestureDetectorForClicks_TestConstructor() {
        RecyclerItemClickListener clickListener = new RecyclerItemClickListener(context,
                (view, position) -> { });
        Assert.assertThat(clickListener.gestureDetector, is(notNullValue()));
    }

    @Test
    public void implementsOnItemTouchListener() {
        RecyclerView.OnItemTouchListener clickListener = new RecyclerItemClickListener(context,
                (view, position) -> { });
        Assert.assertThat(clickListener, is(notNullValue()));
    }

    @Test
    public void doesPassViewFromTouchListenerToItemClickInterfaceMethod() {
        // Arrange
        RecyclerItemClickListener clickListener = new RecyclerItemClickListener(context,
                (view, position) -> resultPosition = position);
        clickListener.gestureDetector = mockGestureDetector;
        Mockito.when(mockGestureDetector.onTouchEvent(mockMotionEvent)).thenReturn(true);
        Mockito.when(mockRecyclerView.findChildViewUnder(Mockito.anyFloat(), Mockito.anyFloat()))
                .thenReturn(mockChildView);

        // Act
        clickListener.onInterceptTouchEvent(mockRecyclerView, mockMotionEvent);

        // Assert
        Assert.assertThat(resultPosition, is(not(-1)));
    }

}
