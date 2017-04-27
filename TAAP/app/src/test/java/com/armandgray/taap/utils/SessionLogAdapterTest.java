package com.armandgray.taap.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.armandgray.taap.BuildConfig;
import com.armandgray.taap.R;
import com.armandgray.taap.models.SessionLog;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SessionLogAdapterTest {

    private SessionLogRvAdapter adapter;
    private SessionLogRvAdapter.SessionLogViewHolder holder;
    private View mockView;
    private SessionLog defaultSessionLog;
    
    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        adapter = new SessionLogRvAdapter(null);
        mockView = mock(View.class);
        defaultSessionLog = new SessionLog.Builder()
                .sessionLength(new Date(0))
                .sessionGoal(new Date(0))
                .activeWork(new Date(0))
                .restTime(new Date(0))
                .setsCompleted(0)
                .repsCompleted(0)
                .successRate(0.0)
                .successRecord(0.0)
                .create();
    }

    @Test
    public void doesImplementAdapter() throws Exception {
        RecyclerView.Adapter<SessionLogRvAdapter.SessionLogViewHolder> adapter =
                new SessionLogRvAdapter(defaultSessionLog);
        assertNotNull(adapter);
    }

    @Test
    public void onCreateViewHolder_ReturnsNewSessionLogViewHolderOfCorrectLayout() {
        TestableRvSummaryAdapter testableAdapter = new TestableRvSummaryAdapter();
        testableAdapter.setMockView(mockView);
        SessionLogRvAdapter.SessionLogViewHolder sessionLogViewHolder = testableAdapter
                .onCreateViewHolder(new FrameLayout(RuntimeEnvironment.application), 0);
        assertEquals(mockView, sessionLogViewHolder.itemView);
    }

    static class TestableRvSummaryAdapter extends SessionLogRvAdapter {
        View mockView;

        void setMockView(View mockView) {
            this.mockView = mockView;
        }

        @Override
        View getLayout(ViewGroup parent) {
            return mockView;
        }
    }

    @SuppressLint("InflateParams")
    @Test
    public void onBindViewHolder_DoesSetViewsForSessionLogItem() {
        adapter = new SessionLogRvAdapter(defaultSessionLog);
        LayoutInflater inflater = (LayoutInflater) RuntimeEnvironment.application
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        holder = new SessionLogRvAdapter.SessionLogViewHolder(
                inflater.inflate(R.layout.session_log_listitem, null, false));
        adapter.onBindViewHolder(holder, 0);

        assertEquals("1-Ball Pound Dribble", holder.tvTitle.getText());
        assertEquals(RuntimeEnvironment.application.getResources().getDrawable(
                R.drawable.ic_fitness_center_white_24dp),
                holder.ivImage.getDrawable());
    }

    @Test
    public void canGetItemCount() throws Exception {
        adapter = new SessionLogRvAdapter(defaultSessionLog);
        assertEquals(9, adapter.getItemCount());
    }

    @Test
    public void canGetItemAtPosition() throws Exception {
        adapter = new SessionLogRvAdapter(defaultSessionLog);
        assertEquals(defaultSessionLog.getSessionDate(), adapter.getItemAtPosition(0));
        assertEquals(defaultSessionLog.getSessionLength(), adapter.getItemAtPosition(1));
        assertEquals(defaultSessionLog.getActiveWork(), adapter.getItemAtPosition(2));
    }

    @Test
    public void canGetItemAtPosition_NullDrillList() throws Exception {
        adapter = new SessionLogRvAdapter(null);
        assertNull(adapter.getItemAtPosition(0));
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        adapter = null;
        mockView = null;
        defaultSessionLog = null;
    }

}