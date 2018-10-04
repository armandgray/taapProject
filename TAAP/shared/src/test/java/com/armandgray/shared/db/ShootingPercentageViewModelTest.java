package com.armandgray.shared.db;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;

import com.armandgray.shared.application.TAAPAppComponent;
import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.model.PerformanceRate;

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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@Config(manifest = Config.NONE)
@PrepareForTest({ShootingPercentageViewModelTest.class, TAAPApplication.class})
@RunWith(PowerMockRunner.class)
public class ShootingPercentageViewModelTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private TAAPAppComponent mockComponent;

    @Mock
    private ShootingPerformanceRepository mockRepository;

    @Mock
    private LiveData<PerformanceRate> mockPerformanceRate;

    private ShootingPercentageViewModel testViewModel;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(TAAPApplication.class);
        PowerMockito.when(TAAPApplication.getAppComponent()).thenReturn(mockComponent);

        testViewModel = new ShootingPercentageViewModel();
        testViewModel.repository = mockRepository;

        Mockito.when(mockRepository.getCurrentRate()).thenReturn(mockPerformanceRate);
        Mockito.when(mockRepository.getCompletionObserver()).thenReturn(mockPerformanceRate);
    }

    @Test
    public void testConstructor_DoesInjectDependencies() {
        // TODO Implement remaining tests
        Mockito.verify(mockComponent, Mockito.times(1)).inject(testViewModel);
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
