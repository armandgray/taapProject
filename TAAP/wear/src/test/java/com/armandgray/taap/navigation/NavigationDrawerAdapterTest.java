package com.armandgray.taap.navigation;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.armandgray.shared.navigation.NavigationDrawerItem;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

@Config(manifest = Config.NONE)
@PrepareForTest({NavigationDrawerAdapter.class, Log.class})
@RunWith(PowerMockRunner.class)
public class NavigationDrawerAdapterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    NavigationDrawerItem<Destination<?>> mockItem;

    @Mock
    Destination mockDestination;

    @Mock
    Drawable mockDrawable;

    private List<NavigationDrawerItem<Destination<?>>> testItems;
    private NavigationDrawerAdapter testAdapter;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        PowerMockito.mockStatic(Log.class);

        testItems = new ArrayList<>();
        testItems.add(mockItem);

        testAdapter = new NavigationDrawerAdapter();
        testAdapter.updateItems(testItems);

        Mockito.when(mockItem.getDestination()).thenReturn(mockDestination);
        Mockito.when(mockItem.getDrawable()).thenReturn(mockDrawable);
    }

    @Test
    public void testGetItemText() {
        Assert.assertThat(testAdapter.getItemText(0), is(""));
    }

    @Test
    public void testGetItemDrawable() {
        Assert.assertThat(testAdapter.getItemDrawable(0), is(mockDrawable));
    }

    @Test
    public void testGetItemDestination() {
        Assert.assertThat(testAdapter.getItemDestination(0), is(mockDestination));
    }

    @Test
    public void testGetCount() {
        Assert.assertThat(testAdapter.getCount(), is(1));
    }

    @Test
    public void testUpdateItems() {
        Assert.assertThat(testAdapter.getCount(), is(1));
        testAdapter.updateItems(new ArrayList<>());
        Assert.assertThat(testAdapter.getCount(), is(0));
        testAdapter.updateItems(testItems);
        Assert.assertThat(testAdapter.getCount(), is(1));
    }

    @After
    public void tearDown() {
        testAdapter = null;
    }
}