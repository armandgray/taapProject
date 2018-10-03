package com.armandgray.shared.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

public class PerformanceRateTest {

    private static final String TEST_DRILL = "TEST_DRILL";
    private static final int TEST_MAX = 20;
    private static final float TEST_SUCCESS_RATE = 0.50f;

    @Rule
    public ExpectedException exceptionGrabber = ExpectedException.none();
    
    private PerformanceRate testRate;
    
    @Before
    public void setUp() {
        testRate = new PerformanceRate(TEST_DRILL, TEST_MAX, TEST_SUCCESS_RATE);
    }

    @Test
    public void testCloneConstructor_ReturnsShallowCopy() throws IllegalArgumentException {
        PerformanceRate clone = new PerformanceRate(testRate);
        Assert.assertThat(clone, is(not(testRate)));
        Assert.assertThat(clone.getDrill(), is(testRate.getDrill()));
        Assert.assertThat(clone.getCount(), is(testRate.getCount()));
        Assert.assertThat(clone.getTotal(), is(testRate.getTotal()));
        Assert.assertThat(clone.getMax(), is(testRate.getMax()));
        Assert.assertThat(clone.getSuccessRate(), is(testRate.getSuccessRate()));
    }

    @Test
    public void testConstructor_ThrowsException_IfMaxIsLessThan1() throws IllegalArgumentException {
        exceptionGrabber.expect(IllegalArgumentException.class);
        new PerformanceRate(TEST_DRILL, 0, TEST_SUCCESS_RATE);
    }

    @Test
    public void testGetDrill() {
        Assert.assertThat(testRate.getDrill(), is(TEST_DRILL));
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
    public void testGetMax() {
        Assert.assertThat(testRate.getMax(), is(TEST_MAX));
    }

    @Test
    public void testGetSuccessRate() {
        Assert.assertThat(testRate.getSuccessRate(), is(TEST_SUCCESS_RATE));
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
