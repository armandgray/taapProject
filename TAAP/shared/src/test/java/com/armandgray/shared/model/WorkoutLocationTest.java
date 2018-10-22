package com.armandgray.shared.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;

public class WorkoutLocationTest {

    private static final String TEST_TITLE = "TEST_TITLE";

    private WorkoutLocation testLocation;

    @Before
    public void setUp() {
        testLocation = new WorkoutLocation(TEST_TITLE);
    }

    @Test
    public void testGetTitle() {
        Assert.assertThat(testLocation.getTitle(), is(TEST_TITLE));
    }

    @Test
    public void testToString() {
        Assert.assertThat(testLocation.toString(),
                is(String.format(Locale.getDefault(), "Location{%s}", TEST_TITLE)));
    }

    @After
    public void tearDown() {
        testLocation = null;
    }
}