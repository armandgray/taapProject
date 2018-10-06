package com.armandgray.shared.navigation;

import android.graphics.drawable.Drawable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

public class NavigationDrawerItemTest {

    private static final String DESTINATION_STRING = "DESTINATION_STRING";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    Drawable mockDrawable;

    private TAAPDestination<?> testDestination =
            new TAAPDestination<TAAPDestination>(TAAPDestination.class) {
                @Override
                protected Class<TAAPDestination> getDestinationClass() {
                    return TAAPDestination.class;
                }
            };

    private TAAPDestination<?> testUnequalDestination =
            new TAAPDestination<TAAPDestination>(TAAPDestination.class) {
                @Override
                protected Class<TAAPDestination> getDestinationClass() {
                    return TAAPDestination.class;
                }
            };

    private NavigationDrawerItem<TAAPDestination<?>> testItem;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        testItem = new NavigationDrawerItem<>(testDestination, mockDrawable);
    }

    @Test
    public void testGetDrawable() {
        Assert.assertThat(testItem.getDrawable(), is(mockDrawable));
    }

    @Test
    public void testGetDestination() {
        Assert.assertThat(testItem.getDestination(), is(testDestination));
    }

    @Test
    public void testHashCode() {
        Assert.assertThat(testItem.hashCode(), is(TAAPDestination.class.hashCode()));
    }

    @Test
    public void testEquals() {
        NavigationDrawerItem<TAAPDestination<?>> equalItem =
                new NavigationDrawerItem<>(testDestination, null);
        Assert.assertThat(testItem, is(equalItem));
    }

    @Test
    public void testNotEquals() {
        NavigationDrawerItem<TAAPDestination<?>> equalItem =
                new NavigationDrawerItem<>(testUnequalDestination, null);
        Assert.assertThat(testItem, is(not(equalItem)));
    }

    @Test
    public void testToString() {
        Assert.assertThat(testItem.toString(), is("NavAction{Destination{TAAPDestination}}"));
    }

    @Test
    public void testGetAction_ReturnsExistingAction() {
        Assert.assertThat(NavigationDrawerItem.getAction(testDestination), is(testItem));
    }

    @Test
    public void testGetAction_ReturnsNullForNonRegisteredDestinations() {
        Assert.assertThat(NavigationDrawerItem.getAction(testUnequalDestination), is(nullValue()));
    }

    @After
    public void tearDown() {
        testItem = null;
    }
}