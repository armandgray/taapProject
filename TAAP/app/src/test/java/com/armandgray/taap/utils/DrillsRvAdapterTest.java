package com.armandgray.taap.utils;

import android.view.View;

import com.armandgray.taap.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DrillsRvAdapterTest {

    private DrillsRvAdapter adapter;
    private View mockView;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        adapter = new DrillsRvAdapter();
        mockView = mock(View.class);
    }

    @Test
    public void itemCount() throws Exception {
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
    }

}