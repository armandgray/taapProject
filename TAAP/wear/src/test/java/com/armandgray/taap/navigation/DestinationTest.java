package com.armandgray.taap.navigation;

import android.app.Activity;

import com.armandgray.taap.activity.ActiveDrillActivity;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;

public class DestinationTest {

    @Rule
    public ExpectedException exceptionGrabber = ExpectedException.none();

    @Test
    public void testDestinationConstructor_DoesAddDestinationsToSet() {
        Assert.assertThat(Destination.DESTINATIONS.contains(Destination.ACTIVE_DRILL), is(true));
    }

    @Test
    public void testGetDestination() {
        Assert.assertThat(Destination.getDestination(ActiveDrillActivity.class),
                is(Destination.ACTIVE_DRILL));
    }

    @Test
    public void testGetDestination_ThrowsException_IfPassedClassIsNotInSet() {
        exceptionGrabber.expect(IllegalStateException.class);
        Destination.getDestination(Activity.class);
    }
}