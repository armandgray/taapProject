package com.armandgray.shared.viewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

public class LocationViewModelTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private LocationViewModel testViewModel;

    @Before
    public void setUp() {
        testViewModel = new LocationViewModel();
    }

    @Ignore
    @Test
    public void testGetRecentWorkouts() {
    }

    @After
    public void tearDown() {
        testViewModel = null;
    }
}