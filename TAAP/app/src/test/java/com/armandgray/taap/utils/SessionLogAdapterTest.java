package com.armandgray.taap.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;
import com.armandgray.taap.models.Drill;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.armandgray.taap.models.Drill.ALL;
import static com.armandgray.taap.models.Drill.SHOOTING;
import static com.armandgray.taap.models.Drill.getQueryResultList;
import static com.armandgray.taap.utils.DrillsHelper.getDrillsList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SessionLogAdapterTest {

    private SessionLogAdapter adapter;
    private View mockView;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        adapter = new SessionLogAdapter(null);
        mockView = mock(View.class);
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        adapter = null;
        mockView = null;
    }

}