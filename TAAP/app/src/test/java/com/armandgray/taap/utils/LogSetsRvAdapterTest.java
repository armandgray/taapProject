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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.armandgray.taap.utils.LogSetsRvAdapter.IMAGE_RESOURCE_ID;
import static com.armandgray.taap.utils.LogSetsRvAdapter.ITEM_DATA;
import static com.armandgray.taap.utils.LogSetsRvAdapter.STRING_RESOURCE_ID;
import static com.armandgray.taap.utils.LogSetsRvAdapter.TINT_COLOR;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LogSetsRvAdapterTest {

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
                .setsCompleted(0)
                .repsCompleted(0)
                .successRate(0.47)
                .successRecord(0.0)
                .create();
        testLogList = new ArrayList<>();
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
    public void onBindViewHolder_DoesSetViewsForSessionLogItem() {
        adapter = new LogSetsRvAdapter(testLogList);
        LayoutInflater inflater = (LayoutInflater) RuntimeEnvironment.application
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LogSetsRvAdapter.LogSetsViewHolder holder =
                new LogSetsRvAdapter.LogSetsViewHolder(
                        inflater.inflate(R.layout.session_log_listitem, null, false));
        adapter.onBindViewHolder(holder, 1);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(testLogList.getSessionLength());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        SimpleDateFormat simpleDateFormat =
                hour == 0
                        ? new SimpleDateFormat("00:mm:ss", Locale.US)
                        : new SimpleDateFormat("hh:mm:ss", Locale.US);

        assertEquals("Session Length", holder.tvHeader.getText());
        assertEquals(simpleDateFormat.format(calendar.getTime()), holder.tvText.getText());
        assertEquals(RuntimeEnvironment.application.getResources().getDrawable(
                R.drawable.ic_timer_white_24dp),
                holder.ivImage.getDrawable());
    }

    @Test
    public void canGetItemCount() throws Exception {
        adapter = new LogSetsRvAdapter(testLogList);
        assertEquals(9, adapter.getItemCount());
    }

    @Test
    public void canGetItemAtPosition() throws Exception {
        assertEquals(hashMap, adapter.getItemAtPosition(0));
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        adapter = null;
        mockView = null;
        testLogList = null;
    }

}