package com.armandgray.taap.utils;

import android.view.View;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;
import com.armandgray.taap.models.Drill;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DrillsRvAdapterTest {

    private DrillsRvAdapter adapter;
    private View mockView;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        adapter = new DrillsRvAdapter(null);
        mockView = mock(View.class);
    }

    @Test
    public void canGetItemCount() throws Exception {
        Drill drill = new Drill("2-Ball Pound Dribble", R.drawable.ic_fitness_center_white_24dp);
        ArrayList<Drill> drillList = new ArrayList<>();
        drillList.add(drill);
        drillList.add(drill);
        drillList.add(drill);
        adapter = new DrillsRvAdapter(drillList);
        assertEquals(3, adapter.getItemCount());
    }

    @Test
    public void canGetItemAtPosition() {
        Drill firstDrill =
                new Drill("1-Ball Pound Dribble", R.drawable.ic_fitness_center_white_24dp);
        Drill secondDrill =
                new Drill("2-Ball Pound Dribble", R.drawable.ic_fitness_center_white_24dp);
        adapter = new DrillsRvAdapter(new ArrayList<>(Arrays.asList(firstDrill, secondDrill)));
        assertEquals(firstDrill, adapter.getItemAtPosition(0));
        assertEquals(secondDrill, adapter.getItemAtPosition(1));
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
    }

}