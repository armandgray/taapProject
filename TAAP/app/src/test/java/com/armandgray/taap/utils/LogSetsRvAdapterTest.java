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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LogSetsRvAdapterTest {

    public static final int POSITION = 1;
    private LogSetsRvAdapter adapter;
    private ArrayList<SessionLog> testLogList;
    private View mockView;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        adapter = new LogSetsRvAdapter(null);
        mockView = mock(View.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, 1, 1, 1, 5, 30);
        SessionLog testSessionLog = new SessionLog.Builder()
                .sessionLength(calendar.getTime())
                .sessionGoal("")
                .activeWork(new Date(0))
                .restTime(new Date(0))
                .setsCompleted(2)
                .repsCompleted(1)
                .successRate(0.47)
                .successRecord(0.0)
                .create();
        testLogList = new ArrayList<>();
        testLogList.add(testSessionLog);
        testLogList.add(testSessionLog);
    }

    @Test
    public void doesImplementAdapter() throws Exception {
        RecyclerView.Adapter<LogSetsRvAdapter.LogSetsViewHolder> adapter =
                new LogSetsRvAdapter(testLogList);
        assertNotNull(adapter);
    }

    @Test
    public void onCreateViewHolder_ReturnsNewLogSetsViewHolderOfCorrectLayout() {
        TestableRvSummaryAdapter testableAdapter = new TestableRvSummaryAdapter();
        testableAdapter.setMockView(mockView);
        LogSetsRvAdapter.LogSetsViewHolder LogSetsViewHolder = testableAdapter
                .onCreateViewHolder(new FrameLayout(RuntimeEnvironment.application), 0);
        assertEquals(mockView, LogSetsViewHolder.itemView);
    }

    static class TestableRvSummaryAdapter extends LogSetsRvAdapter {
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
    public void onBindViewHolder_DoesSetText() {
        adapter = new LogSetsRvAdapter(testLogList);
        LayoutInflater inflater = (LayoutInflater) RuntimeEnvironment.application
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LogSetsRvAdapter.LogSetsViewHolder holder =
                new LogSetsRvAdapter.LogSetsViewHolder(
                        inflater.inflate(R.layout.log_sets_textview, null, false));
        adapter.onBindViewHolder(holder, POSITION);

        SessionLog log = adapter.getItemAtPosition(POSITION);
        String expected = String.format(Locale.US,
                "%dx%d @%d%%",
                log.getSetsCompleted(),
                log.getRepsCompleted(),
                ((Double) (log.getSuccessRate() * 100)).intValue());
        assertEquals(expected, holder.tvText.getText());
    }

    @Test
    public void canGetItemCount() throws Exception {
        adapter = new LogSetsRvAdapter(testLogList);
        assertEquals(testLogList.size(), adapter.getItemCount());
    }

    @Test
    public void canGetItemAtPosition() throws Exception {
        adapter = new LogSetsRvAdapter(testLogList);
        assertEquals(testLogList.get(0), adapter.getItemAtPosition(0));
    }

    @Test
    public void canAddLogAndUpdateRv() throws Exception {
        adapter.addLog(adapter.getItemAtPosition(0));

        ArrayList<SessionLog> expectedList = new ArrayList<>();
        expectedList.addAll(adapter.logs);
        expectedList.add(adapter.getItemAtPosition(0));
        assertEquals(expectedList.size(), adapter.getItemCount());
        assertEquals(expectedList, adapter.logs);
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        adapter = null;
        mockView = null;
        testLogList = null;
    }

}