package com.armandgray.taap;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DrillActivityViewsTest {

    @Rule
    public ActivityTestRule<DrillActivity> activityTestRule =
            new ActivityTestRule<>(DrillActivity.class);

    @Test
    public void viewExistsTest_ContainsFab_ReturnsTrue() throws Exception {

    }
}
