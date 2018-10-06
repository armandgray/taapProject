package com.armandgray.taap.application;

import com.armandgray.taap.activity.ActiveDrillActivity;
import com.armandgray.taap.activity.CourtActivity;
import com.armandgray.taap.activity.DrillPickerActivity;

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

            @Override
            CourtActivity bindCourtActivity() {
                return null;
            }

            @Override
            DrillPickerActivity bindDrillPickerActivity() {
                return null;
            }
        };

        Assert.assertThat(builder, is(notNullValue()));
    }
}
