package com.armandgray.shared.navigation;

import android.app.Activity;
import androidx.annotation.NonNull;

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
    public void testToString() {
        Assert.assertThat(testDestination.toString(), is("Destination{Activity}"));
    }

    @After
    public void tearDown() {
        testDestination = null;
    }
}