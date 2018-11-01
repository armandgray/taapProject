package com.armandgray.shared.sensors;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class LinearAccelerationActionTest {

    @Test
    public void testLinearAccelerationAction_Instances() {
        LinearAccelerationAction[] values = LinearAccelerationAction.values();
        Assert.assertThat(values.length, is(5));
        Assert.assertThat(Arrays.asList(values), containsInAnyOrder(
                LinearAccelerationAction.NONE,
                LinearAccelerationAction.MISSING_HARDWARE,
                LinearAccelerationAction.ACTIVE,
                LinearAccelerationAction.INACTIVE,
                LinearAccelerationAction.HORIZONTAL_FLING_AWAY));
    }
}
