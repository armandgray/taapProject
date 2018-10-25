package com.armandgray.shared.model;

import com.armandgray.shared.R;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Locale;

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

    private long preTestCurrentTime;
    private Performance testPerformance;
    
    @Before
    public void setUp() {
        preTestCurrentTime = System.currentTimeMillis();
        testPerformance = new Performance(TEST_DRILL);
    }

    @Test
    public void testConstructor_Defaults() throws IllegalArgumentException {
        Assert.assertThat(testPerformance.getDrillTitle(), is(TEST_DRILL.getTitle()));
        Assert.assertThat(testPerformance.getCount(), is(0));
        Assert.assertThat(testPerformance.getTotal(), is(0));
        Assert.assertThat(testPerformance.getReps(), is(TEST_DRILL.getReps()));
        Assert.assertThat(testPerformance.getGoal(), is(TEST_DRILL.getGoal()));
        // TODO Replace with real test
        Assert.assertThat(testPerformance.getLocation(), is(notNullValue()));
        Assert.assertThat(testPerformance.getStartTime() >= preTestCurrentTime, is(true));
        Assert.assertThat(testPerformance.getEndTime() >= preTestCurrentTime, is(true));
        Assert.assertThat(testPerformance.getStartTime() <= System.currentTimeMillis(), is(true));
        Assert.assertThat(testPerformance.getEndTime() <= System.currentTimeMillis(), is(true));
    }

    @Test
    public void testCloneConstructor_ReturnsShallowCopy() throws IllegalArgumentException {
        Performance clone = new Performance(testPerformance);
        Assert.assertThat(clone, is(not(testPerformance)));
        Assert.assertThat(clone.getDrillTitle(), is(testPerformance.getDrillTitle()));
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
    public void testGetDrillTitle() {
        Assert.assertThat(testPerformance.getDrillTitle(), is(TEST_DRILL.getTitle()));
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
        Assert.assertThat(testPerformance.getStartTime() >= preTestCurrentTime, is(true));
        Assert.assertThat(testPerformance.getStartTime() <= System.currentTimeMillis(), is(true));
    }

    @Test
    public void testGetEndTime() {
        Assert.assertThat(testPerformance.getEndTime() >= preTestCurrentTime, is(true));
        Assert.assertThat(testPerformance.getEndTime() <= System.currentTimeMillis(), is(true));
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
    public void testRaiseTotal_CapturesStartTimeOnFirstIncrement() throws InterruptedException {
        long preExpectation = System.currentTimeMillis();
        Thread.sleep(10L);
        testPerformance.raiseTotal();
        Assert.assertThat(testPerformance.getStartTime() >= preExpectation, is(true));
        Assert.assertThat(testPerformance.getStartTime() <= System.currentTimeMillis(), is(true));
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
    public void testSetDrillTitle() {
        String expected = "12";
        testPerformance.setDrillTitle(expected);
        Assert.assertThat(testPerformance.getDrillTitle(), is(expected));
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
    public void testCaptureEndTime() throws InterruptedException {
        long preExpectation = System.currentTimeMillis();
        Thread.sleep(10L);
        testPerformance.captureEndTime();
        Assert.assertThat(testPerformance.getEndTime() >= preExpectation, is(true));
        Assert.assertThat(testPerformance.getEndTime() <= System.currentTimeMillis(), is(true));
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

    @Ignore
    @Test
    public void testCompareTo() {

    }

    @Test
    public void testToString() {
        Assert.assertThat(testPerformance.toString(), is(String.format(Locale.getDefault(),
                "Performance(%d){%s: %d/%d for %dms}", testPerformance.getId(),
                testPerformance.getDrillTitle(), testPerformance.getCount(),
                testPerformance.getTotal(),
                testPerformance.getEndTime() % testPerformance.getStartTime())));
    }

    @After
    public void tearDown() {
        testPerformance = null;
    }
}
