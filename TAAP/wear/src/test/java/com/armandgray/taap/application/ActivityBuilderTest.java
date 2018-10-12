package com.armandgray.taap.application;

import com.armandgray.taap.activity.ActiveDrillActivity;
import com.armandgray.taap.activity.CourtActivity;
import com.armandgray.taap.ui.DrillPickerDialog;
import com.armandgray.taap.activity.LogsActivity;
import com.armandgray.taap.ui.PreferenceSeekBarDialog;
import com.armandgray.taap.ui.PreferencesDialog;
import com.armandgray.taap.activity.SettingsActivity;

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
            DrillPickerDialog bindDrillPickerActivity() {
                return null;
            }

            @Override
            PreferenceSeekBarDialog bindPreferenceSeekBarDialog() {
                return null;
            }

            @Override
            SettingsActivity bindSettingsActivity() {
                return null;
            }

            @Override
            LogsActivity bindLogsActivity() {
                return null;
            }

            @Override
            PreferencesDialog bindTargetsActivity() {
                return null;
            }
        };

        Assert.assertThat(builder, is(notNullValue()));
    }
}
