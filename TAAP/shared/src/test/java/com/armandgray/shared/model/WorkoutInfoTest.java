package com.armandgray.shared.model;

import com.armandgray.shared.db.PerformanceDao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

public class WorkoutInfoTest {

    private static final WorkoutLocation TEST_LOCATION = new WorkoutLocation("TEST");
    private static final int TEST_COUNT = 5;
    private static final int TEST_REPS = 10;

    private WorkoutInfo testWorkout;

    @Before
    public void setUp() {
        Performance performance = new Performance(Drill.Defaults.getDefault());
        performance.setLocation(TEST_LOCATION);
        performance.setCount(TEST_COUNT);
        performance.setReps(TEST_REPS);

        PerformanceDao.DaoLog log = new PerformanceDao.DaoLog();
        log.setType(Drill.Type.SHOOTING_FUNDAMENTALS);
        log.setPerformance(performance);
        testWorkout = new WorkoutInfo(Collections.singletonList(log));
    }

    @Test
    public void testGetTypes() {
        Assert.assertThat(testWorkout.getTypes(),
                containsInAnyOrder(Drill.Type.SHOOTING, Drill.Type.FUNDAMENTALS));
    }

    @Test
    public void testGetLocation() {
        Assert.assertThat(testWorkout.getLocation(), is(TEST_LOCATION));
    }

    @Test
    public void testGetOverallPerformance() {
        Assert.assertThat(testWorkout.getOverallPerformance(), is("50%"));
    }

    @Test
    public void testGetOverallReps() {
        Assert.assertThat(testWorkout.getOverallReps(), is(TEST_REPS));
    }

    @Test
    public void testGetLength() {
        // TODO implement
    }

    @Test
    public void testToString() {
        Assert.assertThat(testWorkout.toString(),
                is(String.format(Locale.getDefault(), "Log{@%s: %s, %s, %s}",
                        testWorkout.getLocation(), testWorkout.getOverallPerformance(),
                        testWorkout.getLength(), testWorkout.getTypes())));
    }

    @After
    public void tearDown() {
        testWorkout = null;
    }
}
