package com.armandgray.taap;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DrillActivityViewsTest {

    @Rule
    public ActivityTestRule<DrillActivity> activityTestRule =
            new ActivityTestRule<>(DrillActivity.class);

    @Test
    public void viewExistsTest_ContainsFab_ReturnsTrue() throws Exception {
        onView(withId(R.id.greetEditText))
                .perform(typeText("Jake"), closeSoftKeyboard());
    }

    @Test
    public void viewExistsTest_ContainsButtonGreet_ReturnsTrue() throws Exception {
        onView(withText("Greet")).perform(click());
    }
}
