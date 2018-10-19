package com.armandgray.shared.navigation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class NavigationActivityTest {

    private ActivityController<NavigationActivity> activityController;
    private NavigationActivity activity;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
    }

    @Test
    public void stub_test_testing() {
        // TODO Complete Testing
        assertTrue(true);
    }

    @Test
    public void testNavigationModule_ProvideNavigationViewModel() {
        NavigationActivity.NavigationModule module = new NavigationActivity.NavigationModule() {
        };

        // TODO Implement test

        Assert.assertThat(module, is(notNullValue()));
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
    }
}
