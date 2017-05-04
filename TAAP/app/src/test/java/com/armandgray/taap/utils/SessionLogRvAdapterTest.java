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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.armandgray.taap.utils.SessionLogRvAdapter.IMAGE_RESOURCE_ID;
import static com.armandgray.taap.utils.SessionLogRvAdapter.ITEM_DATA;
import static com.armandgray.taap.utils.SessionLogRvAdapter.STRING_RESOURCE_ID;
import static com.armandgray.taap.utils.SessionLogRvAdapter.TYPE_HEADER;
import static com.armandgray.taap.utils.SessionLogRvAdapter.TYPE_ITEM;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SessionLogRvAdapterTest {

    private SessionLogRvAdapter adapter;
    private View mockView;
    private SessionLog testSessionLog;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        adapter = new SessionLogRvAdapter(null);
        mockView = mock(View.class);
        testSessionLog = new SessionLog.Builder()
                .sessionLength(new Date(1, 1, 1, 1, 5, 30))
                .sessionGoal("")
                .activeWork(new Date(0))
                .restTime(new Date(0))
                .setsCompleted(0)
                .repsCompleted(0)
                .successRate(0.47)
                .successRecord(0.0)
                .create();
    }

    @Test
    public void doesImplementAdapter() throws Exception {
        RecyclerView.Adapter<SessionLogRvAdapter.SessionLogViewHolder> adapter =
                new SessionLogRvAdapter(testSessionLog);
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
    public void onBindViewHolder_DoesSetViewsForSessionLogHeader() {
        adapter = new SessionLogRvAdapter(testSessionLog);
        LayoutInflater inflater = (LayoutInflater) RuntimeEnvironment.application
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        SessionLogRvAdapter.SessionLogViewHolder holder =
                new SessionLogRvAdapter.SessionLogHeaderViewHolder(
                        inflater.inflate(R.layout.session_log_header_layout, null, false));
        adapter.onBindViewHolder(holder, 0);

        Date date = testSessionLog.getSessionDate();
        String expectedDate = new SimpleDateFormat("EEE, MMM d, ''yy", Locale.US)
                .format(date);

        assertEquals(R.string.session_date, adapter.getItemAtPosition(0).get(STRING_RESOURCE_ID));
        assertEquals(expectedDate, holder.tvText.getText());
    }

    @SuppressLint("InflateParams")
    @Test
    public void onBindViewHolder_DoesSetViewsForSessionLogItem() {
        adapter = new SessionLogRvAdapter(testSessionLog);
        LayoutInflater inflater = (LayoutInflater) RuntimeEnvironment.application
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        SessionLogRvAdapter.SessionLogViewHolder holder =
                new SessionLogRvAdapter.SessionLogViewHolder(
                        inflater.inflate(R.layout.session_log_listitem, null, false));
        adapter.onBindViewHolder(holder, 1);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(testSessionLog.getSessionLength());
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

    @SuppressLint("InflateParams")
    @Test
    public void onBindViewHolder_DoesSetViewsForSessionLogItem_Ints() {
        adapter = new SessionLogRvAdapter(testSessionLog);
        LayoutInflater inflater = (LayoutInflater) RuntimeEnvironment.application
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        SessionLogRvAdapter.SessionLogViewHolder holder =
                new SessionLogRvAdapter.SessionLogViewHolder(
                        inflater.inflate(R.layout.session_log_listitem, null, false));
        adapter.onBindViewHolder(holder, 5);

        assertEquals("Sets Completed", holder.tvHeader.getText());
        assertEquals(String.valueOf(testSessionLog.getSetsCompleted()), holder.tvText.getText());
        assertEquals(RuntimeEnvironment.application.getResources().getDrawable(
                R.drawable.ic_fitness_center_white_24dp),
                holder.ivImage.getDrawable());
    }

    @SuppressLint("InflateParams")
    @Test
    public void onBindViewHolder_DoesSetViewsForSessionLogItem_Percents() {
        adapter = new SessionLogRvAdapter(testSessionLog);
        LayoutInflater inflater = (LayoutInflater) RuntimeEnvironment.application
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        SessionLogRvAdapter.SessionLogViewHolder holder =
                new SessionLogRvAdapter.SessionLogViewHolder(
                        inflater.inflate(R.layout.session_log_listitem, null, false));
        adapter.onBindViewHolder(holder, 7);
        Double rate = testSessionLog.getSuccessRate() * 100;

        assertEquals("Success Rate", holder.tvHeader.getText());
        assertEquals(String.format(Locale.US, "%d%", rate.intValue()), holder.tvText.getText());
        assertEquals(RuntimeEnvironment.application.getResources().getDrawable(
                R.drawable.ic_timer_white_24dp),
                holder.ivImage.getDrawable());
    }

    @Test
    public void canGetItemCount() throws Exception {
        adapter = new SessionLogRvAdapter(testSessionLog);
        assertEquals(9, adapter.getItemCount());
    }

    @Test
    public void canGetItemAtPosition() throws Exception {
        adapter = new SessionLogRvAdapter(testSessionLog);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(STRING_RESOURCE_ID, R.string.session_date);
        hashMap.put(ITEM_DATA, testSessionLog.getSessionDate());
        hashMap.put(IMAGE_RESOURCE_ID, R.drawable.ic_timer_white_24dp);
        assertEquals(hashMap, adapter.getItemAtPosition(0));
    }

    @Test
    public void canGetItemViewType() throws Exception {
        adapter = new SessionLogRvAdapter(testSessionLog);
        assertEquals(TYPE_HEADER, adapter.getItemViewType(0));
        assertEquals(TYPE_ITEM, adapter.getItemViewType(1));
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        adapter = null;
        mockView = null;
        testSessionLog = null;
    }

}