package com.armandgray.taap.utils;

import com.armandgray.taap.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DrillsRvAdapterTest {

    private DrillsRvAdapter adapter;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        adapter = new DrillsRvAdapter();
    }

    @Test
    public void itemCount() throws Exception {
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
    }

}