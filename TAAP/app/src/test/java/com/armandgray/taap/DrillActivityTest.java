package com.armandgray.taap;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DrillActivityTest {

    @Test
    public void testViewExists_ContainsFab() throws Exception {
        Activity activity = Robolectric.buildActivity(DrillActivity.class).create().visible().get();
        assertNotNull(activity);

        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        assertNotNull(fab);
    }

}