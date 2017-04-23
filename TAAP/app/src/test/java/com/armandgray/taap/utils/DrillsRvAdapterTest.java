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
public class DrillsRvAdapterTest {

    private DrillsRvAdapter adapter;
    private DrillsRvAdapter.DrillViewHolder holder;
    private View mockView;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        adapter = new DrillsRvAdapter(null);
        mockView = mock(View.class);
    }

    @Test
    public void onCreateViewHolder_ReturnsNewDrillViewHolderOfCorrectLayout() {
        TestableDrillsRvAdapter testableAdapter = new TestableDrillsRvAdapter();
        testableAdapter.setMockView(mockView);
        DrillsRvAdapter.DrillViewHolder drillViewHolder = testableAdapter
                .onCreateViewHolder(new FrameLayout(RuntimeEnvironment.application), 0);
        assertEquals(mockView, drillViewHolder.itemView);
    }

    @Test
    public void onBindViewHolder_DoesSetViewsForDrillItem() {
        adapter = new DrillsRvAdapter(new ArrayList<>(Collections.singletonList(
                new Drill("1-Ball Pound Dribble", R.drawable.ic_fitness_center_white_24dp, Drill.BALL_HANDLING_ARRAY))));
        LayoutInflater inflater = (LayoutInflater) RuntimeEnvironment.application
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        holder = new DrillsRvAdapter.DrillViewHolder(
                inflater.inflate(R.layout.drill_listitem, null, false));
        adapter.onBindViewHolder(holder, 0);

        assertEquals("1-Ball Pound Dribble", holder.tvTitle.getText());
        assertEquals(RuntimeEnvironment.application.getResources().getDrawable(
                R.drawable.ic_fitness_center_white_24dp),
                holder.ivImage.getDrawable());
    }

    static class TestableDrillsRvAdapter extends DrillsRvAdapter {
        View mockView;

        void setMockView(View mockView) {
            this.mockView = mockView;
        }

        @Override
        View getLayout(ViewGroup parent) {
            return mockView;
        }
    }

    @Test
    public void canGetItemCount() throws Exception {
        Drill drill = new Drill("2-Ball Pound Dribble", R.drawable.ic_fitness_center_white_24dp, Drill.BALL_HANDLING_ARRAY);
        ArrayList<Drill> drillList = new ArrayList<>();
        drillList.add(drill);
        drillList.add(drill);
        drillList.add(drill);
        adapter = new DrillsRvAdapter(drillList);
        assertEquals(3, adapter.getItemCount());
    }

    @Test
    public void canGetItemAtPosition() throws Exception {
        Drill firstDrill =
                new Drill("1-Ball Pound Dribble", R.drawable.ic_fitness_center_white_24dp, Drill.BALL_HANDLING_ARRAY);
        Drill secondDrill =
                new Drill("2-Ball Pound Dribble", R.drawable.ic_fitness_center_white_24dp, Drill.BALL_HANDLING_ARRAY);
        adapter = new DrillsRvAdapter(new ArrayList<>(Arrays.asList(firstDrill, secondDrill)));
        assertEquals(firstDrill, adapter.getItemAtPosition(0));
        assertEquals(secondDrill, adapter.getItemAtPosition(1));
        assertNull(adapter.getItemAtPosition(2));
    }

    @Test
    public void canGetItemAtPosition_NullDrillList() throws Exception {
        adapter = new DrillsRvAdapter(null);
        assertNull(adapter.getItemAtPosition(0));
    }

    @Test
    public void canGetItemAtPosition_IndexOutOfBounds() throws Exception {
        adapter = new DrillsRvAdapter(new ArrayList<>(Collections.singletonList(new Drill("", 0, Drill.BALL_HANDLING_ARRAY))));
        assertNull(adapter.getItemAtPosition(1));
    }

    @Test
    public void canSwapRvDrillsAdapterDataOnDrillType() throws Exception {
        ArrayList<Drill> expectedList = getDrillsList();
        for (int i = 0; i < expectedList.size(); i++) {
            if (!Arrays.asList(expectedList.get(i).getCategory()).contains(SHOOTING)) {
                expectedList.remove(i);
                i--;
            }
        }
        adapter = new DrillsRvAdapter(getDrillsList());
        adapter.swapRvDrillsAdapterDataOnDrillType(SHOOTING);

        assertEquals(expectedList.size(), adapter.drillList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertTrue(expectedList.get(i).getTitle().equals(adapter.drillList.get(i).getTitle()));
        }
    }

    @Test
    public void doesRestoreDrillsListForUnknownDrillType_MethodTest_SwapRvDrillsAdapterData() throws Exception {
        ArrayList<Drill> expectedList = getDrillsList();
        adapter = new DrillsRvAdapter(getDrillsList());
        adapter.swapRvDrillsAdapterDataOnDrillType(ALL);

        assertEquals(expectedList.size(), adapter.drillList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertTrue(expectedList.get(i).getTitle().equals(adapter.drillList.get(i).getTitle()));
        }
    }

    @Test
    public void canSwapRvDrillsAdapterDataOnQuery() throws Exception {
        ArrayList<Drill> expectedList = getQueryResultList(getDrillsList(), "3 Man Weave");

        adapter = new DrillsRvAdapter(getDrillsList());
        adapter.swapRvDrillsAdapterDataOnQuery("3 Man Weave");

        assertEquals(expectedList.size(), adapter.drillList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertTrue(expectedList.get(i).getTitle().equals(adapter.drillList.get(i).getTitle()));
        }
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        adapter = null;
        holder = null;
        mockView = null;
    }

}