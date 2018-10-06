package com.armandgray.shared.navigation;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

public class NavigatorTest {

    @Test
    public void testNavigator_DeclaredMethods() {
        Navigator testNavigator = new Navigator() {
            @Override
            public void observeDestinationActivity() {
            }

            @Override
            public <D extends TAAPDestination<?>> void goTo(D destination) {
            }

            @Override
            public <D extends TAAPDestination<?>> Class<?> getDestinationClass(D destination) {
                return null;
            }
        };

        Assert.assertThat(testNavigator, is(notNullValue()));
    }
}