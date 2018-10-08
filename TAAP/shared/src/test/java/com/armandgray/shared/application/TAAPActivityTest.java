package com.armandgray.shared.application;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.is;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class TAAPActivityTest {

    @Rule
    public ExpectedException exceptionGrabber = ExpectedException.none();

    private TAAPActivity testActivity;
    private boolean[] setupContentFlags;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        setupContentFlags = new boolean[4];
        testActivity = new TAAPActivity() {
            @Override
            protected void assignGlobalFields() {
                setupContentFlags[0] = true;
            }

            @Override
            protected void setupVisualElements(boolean showActionDrawer) {
                setupContentFlags[1] = true;
            }

            @Override
            protected void setupEventListeners() {
                setupContentFlags[2] = true;
            }

            @Override
            protected void setupViewModel() {
                setupContentFlags[3] = true;
            }
        };
    }

    @Test
    public void testOnSetupContent_CallsAbstractMethods() {
        testActivity.onSetupContent();
        for (boolean flag : setupContentFlags) {
            Assert.assertThat(flag, is(true));
        }
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        testActivity = null;
    }
}
