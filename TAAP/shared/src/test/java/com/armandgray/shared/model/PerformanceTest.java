package com.armandgray.shared.model;

import com.armandgray.shared.R;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;

public class PerformanceTest {

    private static final String FREE_THROWS = "FREE_THROWS";
    private static final Drill TEST_DRILL;

    static {
        TEST_DRILL = new Drill(FREE_THROWS, R.drawable.ic_add_white_24dp,
                Drill.Type.SHOOTING_ONLY);
    }
    
    private Performance testPerformance;
    
    @Before
    public void setUp() {
        testPerformance = new Performance(TEST_DRILL);
    }

    @Test
    public void testConstructor_Defaults() throws IllegalArgumentException {
        Assert.assertThat(testPerformance.getDrillId(), is(TEST_DRILL.getId()));
        Assert.assertThat(testPerformance.getCount(), is(0));
        Assert.assertThat(testPerformance.getTotal(), is(0));
        Assert.assertThat(testPerformance.getReps(), is(TEST_DRILL.getReps()));
        Assert.assertThat(testPerformance.getGoal(), is(TEST_DRILL.getGoal()));
        // TODO Replace with real test
        Assert.assertThat(testPerformance.getLocation(), is(notNullValue()));
        Assert.assertThat(testPerformance.getStartTime() <= System.currentTimeMillis(), is(true));
    }

    @Test
    public void testCloneConstructor_ReturnsShallowCopy() throws IllegalArgumentException {
        Performance clone = new Performance(testPerformance);
        Assert.assertThat(clone, is(not(testPerformance)));
        Assert.assertThat(clone.getDrillId(), is(testPerformance.getDrillId()));
        Assert.assertThat(clone.getCount(), is(testPerformance.getCount()));
        Assert.assertThat(clone.getTotal(), is(testPerformance.getTotal()));
        Assert.assertThat(clone.getReps(), is(testPerformance.getReps()));
        Assert.assertThat(clone.getGoal(), is(testPerformance.getGoal()));
        Assert.assertThat(clone.getLocation(), is(testPerformance.getLocation()));
        Assert.assertThat(clone.getStartTime(), is(testPerformance.getStartTime()));
        Assert.assertThat(clone.getEndTime(), is(testPerformance.getEndTime()));
    }

    @Test
    public void testGetId() {
        Assert.assertThat(testPerformance.getId(), is(0));
    }

    @Test
    public void testGetDrillId() {
        Assert.assertThat(testPerformance.getDrillId(), is(TEST_DRILL.getId()));
    }
    
    @Test
    public void testGetCount() {
        Assert.assertThat(testPerformance.getCount(), is(0));
    }
    
    @Test
    public void testGetTotal() {
        Assert.assertThat(testPerformance.getTotal(), is(0));
    }

    @Test
    public void testGetReps() {
        Assert.assertThat(testPerformance.getReps(), is(10));
    }

    @Ignore
    @Test
    public void testGetLocation() {
        Assert.assertThat(testPerformance.getLocation(), is(notNullValue()));
    }

    @Test
    public void testGetStartTime() {
        Assert.assertThat(testPerformance.getStartTime() <= System.currentTimeMillis(), is(true));
    }

    @Test
    public void testGetEndTime() {
        Assert.assertThat(testPerformance.getEndTime(), is(0L));
    }

    @Test
    public void testGetGoal() {
        Assert.assertThat(testPerformance.getGoal(), is(0.7));
    }

    @Test
    public void testRaiseCount() {
        testPerformance.raiseCount();
        Assert.assertThat(testPerformance.getCount(), is(1));
    }

    @Test
    public void testRaiseTotal() {
        testPerformance.raiseTotal();
        Assert.assertThat(testPerformance.getTotal(), is(1));
    }

    @Test
    public void testIsSuccess() {
        testPerformance.raiseCount();
        testPerformance.raiseCount();
        testPerformance.raiseCount();
        testPerformance.raiseTotal();
        testPerformance.raiseTotal();
        testPerformance.raiseTotal();
        testPerformance.raiseTotal();
        Assert.assertThat(testPerformance.isSuccess(), is(true));
    }

    @Test
    public void testIsNotSuccess() {
        testPerformance.raiseTotal();
        Assert.assertThat(testPerformance.isSuccess(), is(false));
    }

    @Test
    public void testSetId() {
        int expected = 12;
        testPerformance.setId(expected);
        Assert.assertThat(testPerformance.getId(), is(expected));
    }

    @Test
    public void testSetDrillId() {
        int expected = 12;
        testPerformance.setDrillId(expected);
        Assert.assertThat(testPerformance.getDrillId(), is(expected));
    }

    @Test
    public void testSetCount() {
        int expected = 12;
        testPerformance.setCount(expected);
        Assert.assertThat(testPerformance.getCount(), is(expected));
    }

    @Test
    public void testSetTotal() {
        int expected = 12;
        testPerformance.setTotal(expected);
        Assert.assertThat(testPerformance.getTotal(), is(expected));
    }

    @Test
    public void testSetReps() {
        int expected = 12;
        testPerformance.setReps(expected);
        Assert.assertThat(testPerformance.getReps(), is(expected));
    }

    @Test
    public void testSetLocation() {
        WorkoutLocation expected = new WorkoutLocation("TEST");
        testPerformance.setLocation(expected);
        Assert.assertThat(testPerformance.getLocation(), is(expected));
    }

    @Test
    public void testSetStartTime() {
        long expected = 12;
        testPerformance.setStartTime(expected);
        Assert.assertThat(testPerformance.getStartTime(), is(expected));
    }

    @Test
    public void testSetEndTime() {
        long expected = 12;
        testPerformance.setEndTime(expected);
        Assert.assertThat(testPerformance.getEndTime(), is(expected));
    }

    @Test
    public void testSetGoal() {
        double expected = 12.01;
        testPerformance.setGoal(expected);
        Assert.assertThat(testPerformance.getGoal(), is(expected));
    }

    @Test
    public void testClear() {
        // Arrange
        testPerformance.raiseCount();
        testPerformance.raiseTotal();
        Assert.assertThat(testPerformance.getCount(), is(not(0)));
        Assert.assertThat(testPerformance.getTotal(), is(not(0)));

        // Act
        testPerformance.clear();

        // Assert
        Assert.assertThat(testPerformance.getCount(), is(0));
        Assert.assertThat(testPerformance.getTotal(), is(0));
    }

    @Test
    public void testToString() {
        Assert.assertThat(testPerformance.toString(), is("0/0"));
    }

    @After
    public void tearDown() {
        testPerformance = null;
    }
}
