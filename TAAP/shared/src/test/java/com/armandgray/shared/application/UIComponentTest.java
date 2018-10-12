package com.armandgray.shared.application;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.is;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class UIComponentTest {

    private UIComponent testUIComponent;
    private boolean[] setupContentFlags;

    @Before
    public void setUp() {
        System.out.println("Running Set Up!");
        setupContentFlags = new boolean[4];
        testUIComponent = new UIComponent() {
            @Override
            public void assignGlobalFields() {
                setupContentFlags[0] = true;
            }

            @Override
            public void setupVisualElements() {
                setupContentFlags[1] = true;
            }

            @Override
            public void setupEventListeners() {
                setupContentFlags[2] = true;
            }

            @Override
            public void setupViewModel() {
                setupContentFlags[3] = true;
            }
        };
    }

    @Test
    public void testOnSetupContent_CallsInterfaceMethods() {
        testUIComponent.onSetupContent();
        for (boolean flag : setupContentFlags)   {
            Assert.assertThat(flag, is(true));
        }
    }

    @After
    public void tearDown() {
        System.out.println("Running TearDown!");
        testUIComponent = null;
    }
}
