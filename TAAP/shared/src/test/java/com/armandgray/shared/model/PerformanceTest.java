package com.armandgray.shared.model;

import com.armandgray.shared.R;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

public class PerformanceTest {

    private static final String FREE_THROWS = "FREE_THROWS";
    private static final Drill TEST_DRILL;

    static {
        TEST_DRILL = new Drill(FREE_THROWS, R.drawable.ic_add_white_24dp,
                Drill.Type.SHOOTING_ONLY);
    }
    
    private Performance testRate;
    
    @Before
    public void setUp() {
        testRate = new Performance(TEST_DRILL);
    }

    @Test
    public void testCloneConstructor_Defaults() throws IllegalArgumentException {
        Assert.assertThat(testRate.getDrillId(), is(TEST_DRILL.getId()));
        Assert.assertThat(testRate.getCount(), is(0));
        Assert.assertThat(testRate.getTotal(), is(0));
        Assert.assertThat(testRate.getReps(), is(TEST_DRILL.getReps()));
        Assert.assertThat(testRate.getGoal(), is(TEST_DRILL.getGoal()));
        Assert.assertThat(testRate.getStartTime() <= System.currentTimeMillis(), is(true));
    }

    @Test
    public void testCloneConstructor_ReturnsShallowCopy() throws IllegalArgumentException {
        Performance clone = new Performance(testRate);
        Assert.assertThat(clone, is(not(testRate)));
        Assert.assertThat(clone.getDrillId(), is(testRate.getDrillId()));
        Assert.assertThat(clone.getCount(), is(testRate.getCount()));
        Assert.assertThat(clone.getTotal(), is(testRate.getTotal()));
        Assert.assertThat(clone.getReps(), is(testRate.getReps()));
        Assert.assertThat(clone.getGoal(), is(testRate.getGoal()));
        Assert.assertThat(clone.getStartTime(), is(testRate.getStartTime()));
    }

    @Test
    public void testGetId() {
        Assert.assertThat(testRate.getId(), is(0));
    }

    @Test
    public void testGetDrillId() {
        Assert.assertThat(testRate.getDrillId(), is(TEST_DRILL.getId()));
    }
    
    @Test
    public void testGetCount() {
        Assert.assertThat(testRate.getCount(), is(0));
    }
    
    @Test
    public void testGetTotal() {
        Assert.assertThat(testRate.getTotal(), is(0));
    }

    @Test
    public void testGetReps() {
        Assert.assertThat(testRate.getReps(), is(10));
    }

    @Test
    public void testGetStartTime() {
        Assert.assertThat(testRate.getStartTime() <= System.currentTimeMillis(), is(true));
    }

    @Test
    public void testGetEndTime() {
        Assert.assertThat(testRate.getEndTime(), is(0L));
    }

    @Test
    public void testGetGoal() {
        Assert.assertThat(testRate.getGoal(), is(0.7));
    }

    @Test
    public void testRaiseCount() {
        testRate.raiseCount();
        Assert.assertThat(testRate.getCount(), is(1));
    }

    @Test
    public void testRaiseTotal() {
        testRate.raiseTotal();
        Assert.assertThat(testRate.getTotal(), is(1));
    }

    @Test
    public void testIsSuccess() {
        testRate.raiseCount();
        testRate.raiseCount();
        testRate.raiseCount();
        testRate.raiseTotal();
        testRate.raiseTotal();
        testRate.raiseTotal();
        testRate.raiseTotal();
        Assert.assertThat(testRate.isSuccess(), is(true));
    }

    @Test
    public void testIsNotSuccess() {
        testRate.raiseTotal();
        Assert.assertThat(testRate.isSuccess(), is(false));
    }

    @Test
    public void testSetId() {
        int expected = 12;
        testRate.setId(expected);
        Assert.assertThat(testRate.getId(), is(expected));
    }

    @Test
    public void testSetDrillId() {
        int expected = 12;
        testRate.setDrillId(expected);
        Assert.assertThat(testRate.getDrillId(), is(expected));
    }

    @Test
    public void testSetCount() {
        int expected = 12;
        testRate.setCount(expected);
        Assert.assertThat(testRate.getCount(), is(expected));
    }

    @Test
    public void testSetTotal() {
        int expected = 12;
        testRate.setTotal(expected);
        Assert.assertThat(testRate.getTotal(), is(expected));
    }

    @Test
    public void testSetReps() {
        int expected = 12;
        testRate.setReps(expected);
        Assert.assertThat(testRate.getReps(), is(expected));
    }

    @Test
    public void testSetStartTime() {
        long expected = 12;
        testRate.setStartTime(expected);
        Assert.assertThat(testRate.getStartTime(), is(expected));
    }

    @Test
    public void testSetEndTime() {
        long expected = 12;
        testRate.setEndTime(expected);
        Assert.assertThat(testRate.getEndTime(), is(expected));
    }

    @Test
    public void testSetGoal() {
        double expected = 12.01;
        testRate.setGoal(expected);
        Assert.assertThat(testRate.getGoal(), is(expected));
    }

    @Test
    public void testClear() {
        // Arrange
        testRate.raiseCount();
        testRate.raiseTotal();
        Assert.assertThat(testRate.getCount(), is(not(0)));
        Assert.assertThat(testRate.getTotal(), is(not(0)));

        // Act
        testRate.clear();

        // Assert
        Assert.assertThat(testRate.getCount(), is(0));
        Assert.assertThat(testRate.getTotal(), is(0));
    }

    @Test
    public void testToString() {
        Assert.assertThat(testRate.toString(), is("0/0"));
    }

    @After
    public void tearDown() {
        testRate = null;
    }
}
