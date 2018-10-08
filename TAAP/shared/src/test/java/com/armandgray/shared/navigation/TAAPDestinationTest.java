package com.armandgray.shared.navigation;

import android.app.Activity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class TAAPDestinationTest {

    private TAAPDestination<Activity> testDestination;

    @Before
    public void setUp() {
        testDestination = new TAAPDestination<Activity>(Activity.class) {};
    }

    @Test
    public void testGetDestinationClass() {
        Assert.assertThat(testDestination.getDestinationClass(), equalTo(Activity.class));
    }

    @Test
    public void testHashCode() {
        Assert.assertThat(testDestination.hashCode(), is(Activity.class.hashCode()));
    }

    @Test
    public void testEquals() {
        TAAPDestination<Activity> expected = new TAAPDestination<Activity>(Activity.class) {};
        Assert.assertThat(testDestination, is(expected));
    }

    @Test
    public void testToString() {
        Assert.assertThat(testDestination.toString(), is("Destination{Activity}"));
    }

    @After
    public void tearDown() {
        testDestination = null;
    }
}