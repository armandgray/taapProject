package com.armandgray.taap;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class DrillActivityViewsTest {

    @Rule
    public ActivityTestRule<DrillActivity> activityTestRule =
            new ActivityTestRule<>(DrillActivity.class);

    @Test
    public void testViewExists_ContainsFab() throws Exception {
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
    }
}
