package com.armandgray.shared.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DrillTest {

    private static final String TEST_TITLE = "TEST_TITLE";

    private Drill testDrill;

    @Before
    public void setUp() {
        testDrill = new Drill(TEST_TITLE);
    }

    @Test
    public void getTitle() {
        Assert.assertThat(testDrill.getTitle(), is(TEST_TITLE));
    }

    @After
    public void tearDown() {
        testDrill = null;
    }
}