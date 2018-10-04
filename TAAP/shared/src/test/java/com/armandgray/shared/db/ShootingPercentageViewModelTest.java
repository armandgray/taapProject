package com.armandgray.shared.db;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;

import com.armandgray.shared.model.PerformanceRate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ShootingPercentageViewModelTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private ShootingPerformanceRepository mockRepository;

    @Mock
    private LiveData<PerformanceRate> mockPerformanceRate;

    private ShootingPercentageViewModel testViewModel;

    @Before
    public void setUp() {
        testViewModel = new ShootingPercentageViewModel();
        testViewModel.repository = mockRepository;

        Mockito.when(mockRepository.getCurrentRate()).thenReturn(mockPerformanceRate);
        Mockito.when(mockRepository.getCompletionObserver()).thenReturn(mockPerformanceRate);
    }

    @Test
    public void testConstructor_DoesInjectDependencies() {
        // TODO Implement test
    }

    @Test
    public void testGetCurrentRate() {
        Assert.assertThat(testViewModel.getCurrentRate(), is(notNullValue()));
    }

    @Test
    public void testGetCompletionObserver() {
        Assert.assertThat(testViewModel.getCompletionObserver(), is(notNullValue()));
    }

    @Test
    public void testAddMake() {
        testViewModel.addMake();
        Mockito.verify(mockRepository, Mockito.only()).addMake();
    }

    @Test
    public void testAddMiss() {
        testViewModel.addMiss();
        Mockito.verify(mockRepository, Mockito.only()).addMiss();
    }

    @Test
    public void testOnCleared() {
        testViewModel.onCleared();
        // Nothing
    }

    @After
    public void tearDown() {
        testViewModel = null;
    }
}
