package com.armandgray.shared.ui;

import android.view.View;

import com.armandgray.shared.db.PerformanceDao;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;
import com.armandgray.shared.model.WorkoutInfo;
import com.armandgray.shared.model.WorkoutLocation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class WorkoutAdapterTest {

    private static final String FREE_THROWS = "Free Throws";
    private static final WorkoutInfo TEST_WORKOUT;
    private static final WorkoutLocation TEST_LOCATION = new WorkoutLocation("TEST");
    private static final int TEST_COUNT = 5;
    private static final int TEST_REPS = 10;

    static {
        Performance performance = new Performance(Drill.Defaults.getDefault());
        performance.setLocation(TEST_LOCATION);
        performance.setCount(TEST_COUNT);
        performance.setReps(TEST_REPS);

        PerformanceDao.DaoLog log = new PerformanceDao.DaoLog();
        log.setType(Drill.Type.SHOOTING_FUNDAMENTALS);
        log.setPerformance(performance);
        TEST_WORKOUT = new WorkoutInfo(Collections.singletonList(log));
    }

    private WorkoutAdapter adapter;
    private WorkoutAdapter.WorkoutViewHolder holder;
    private View mockView;

    @Before
    public void setUp() {

        adapter = new WorkoutAdapter();
        mockView = mock(View.class);
    }

    @Ignore
    @Test
    public void canGetItemCount() throws Exception {
        // TODO implement tests
        Assert.assertThat(true, is(true));
    }

    @After
    public void tearDown() {

        adapter = null;
        holder = null;
        mockView = null;
    }

}