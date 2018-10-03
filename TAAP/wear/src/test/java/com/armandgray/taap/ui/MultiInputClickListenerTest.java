package com.armandgray.taap.ui;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MultiInputClickListener.class, Rect.class})
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
    MultiInputClickListener.OnMultiInputClickListener mockOnClickListener;

    private MultiInputClickListener testListener;

    @Before
    public void setUp() throws Exception {
        testListener = new MultiInputClickListener(mockOnClickListener);
    }

    @Test
    public void stub_test_testing() {
        // TODO Complete Testing
        assertTrue(true);
    }

    @After
    public void tearDown() {
        testListener = null;
    }
}
