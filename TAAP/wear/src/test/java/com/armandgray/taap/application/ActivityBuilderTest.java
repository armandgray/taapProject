package com.armandgray.taap.application;

import com.armandgray.taap.activity.ActiveDrillActivity;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ActivityBuilderTest {

    @Test
    public void testActivityBuilder_DeclaresActivityInjectorMethods() {
        ActivityBuilder builder = new ActivityBuilder() {
            @Override
            ActiveDrillActivity bindActiveDrillActivity() {
                return null;
            }
        };

        Assert.assertThat(builder, is(notNullValue()));
    }
}
