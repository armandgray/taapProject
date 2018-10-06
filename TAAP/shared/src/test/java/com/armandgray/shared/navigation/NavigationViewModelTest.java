package com.armandgray.shared.navigation;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

public class NavigationViewModelTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    TAAPDestination mockDestination;

    private NavigationViewModel testViewModel;

    @Before
    public void setUp() {
        testViewModel = new NavigationViewModel();
    }

    @Test
    public void testGetDestination() {
        Assert.assertThat(testViewModel.getDestination(), is(notNullValue()));
        Assert.assertThat(testViewModel.getDestination().getValue(), is(nullValue()));
    }

    @Test
    public void testOnNavigate() {
        testViewModel.onNavigate(mockDestination);
        Assert.assertThat(testViewModel.getDestination().getValue(), is(mockDestination));
    }

    @After
    public void tearDown() {
        testViewModel = null;
    }
}