package com.armandgray.taap.ui;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.is;

@Config(manifest = Config.NONE)
@PrepareForTest({MultiInputClickListener.class, Rect.class})
@RunWith(PowerMockRunner.class)
public class MultiInputClickListenerTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    MotionEvent mockMotionEvent;

    @Mock
    Rect mockRect;

    @Mock
    View mockView;

    @Mock
    private MultiInputClickListener.OnMultiInputClickListener mockOnClickListener;

    private MultiInputClickListener testListener;

    @Before
    public void setUp() throws Exception {
        testListener = new MultiInputClickListener(mockOnClickListener);

        PowerMockito.mockStatic(Rect.class);
        PowerMockito.whenNew(Rect.class).withNoArguments().thenReturn(mockRect);
    }

    @Test
    public void testOnTouch_ReturnsFalse_WhenTouchIsOutsideView() {
        setMockEventInsideView(false);
        Assert.assertThat(testListener.onTouch(mockView, mockMotionEvent), is(false));
    }

    @Test
    public void testOnTouch_ReturnsTrue_WhenTouchIsInsideView() {
        setMockEventInsideView(true);
        Assert.assertThat(testListener.onTouch(mockView, mockMotionEvent), is(true));
    }

    @Test
    public void testOnTouch_DoesCallOnDoubleClickEvent_WhenTouchActionIsPointerUp() {
        setMockEventInsideView(true);
        Mockito.when(mockMotionEvent.getAction()).thenReturn(MotionEvent.ACTION_POINTER_UP);
        testListener.onTouch(mockView, mockMotionEvent);
        Mockito.verify(mockOnClickListener, Mockito.times(1)).onDoubleInputClick(mockView);
    }

    @Test
    public void testOnTouch_DoesCallOnSingleClickEvent_WhenTouchActionIsUp_WithoutSkip() {
        setMockEventInsideView(true);
        Mockito.when(mockMotionEvent.getAction()).thenReturn(MotionEvent.ACTION_UP);
        testListener.onTouch(mockView, mockMotionEvent);
        Mockito.verify(mockOnClickListener, Mockito.times(1)).onSingleInputClick(mockView);
    }

    @Test
    public void testOnTouch_DoesSetSkipTrue_WhenTouchActionIsPointerUp() {
        // Arrange
        setMockEventInsideView(true);
        Assert.assertThat(testListener.skipActionUp, is(false));
        Mockito.when(mockMotionEvent.getAction()).thenReturn(MotionEvent.ACTION_POINTER_UP);

        // Act
        testListener.onTouch(mockView, mockMotionEvent);

        // Assert
        Assert.assertThat(testListener.skipActionUp, is(true));
    }

    @Test
    public void testOnTouch_DoesSkipOnSingleClickEventCall_WhenTouchActionIsUp_WithSkip() {
        // Arrange
        setMockEventInsideView(true);
        Mockito.when(mockMotionEvent.getAction()).thenReturn(MotionEvent.ACTION_POINTER_UP);
        testListener.onTouch(mockView, mockMotionEvent);
        Assert.assertThat(testListener.skipActionUp, is(true));

        // Act
        Mockito.when(mockMotionEvent.getAction()).thenReturn(MotionEvent.ACTION_UP);
        testListener.onTouch(mockView, mockMotionEvent);

        // Assert
        Mockito.verify(mockOnClickListener, Mockito.never()).onSingleInputClick(mockView);
    }

    @Test
    public void testOnTouch_DoesSetSkipFalse_WhenTouchActionIsDown() {
        // Arrange
        setMockEventInsideView(true);
        Mockito.when(mockMotionEvent.getAction()).thenReturn(MotionEvent.ACTION_POINTER_UP);
        testListener.onTouch(mockView, mockMotionEvent);
        Assert.assertThat(testListener.skipActionUp, is(true));

        // Act
        Mockito.when(mockMotionEvent.getAction()).thenReturn(MotionEvent.ACTION_DOWN);
        testListener.onTouch(mockView, mockMotionEvent);

        // Assert
        Assert.assertThat(testListener.skipActionUp, is(false));
    }

    @After
    public void tearDown() {
        testListener = null;
    }

    private void setMockEventInsideView(boolean inside) {
        Mockito.when(mockRect.contains(Mockito.anyInt(), Mockito.anyInt())).thenReturn(inside);
    }
}
