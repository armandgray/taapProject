package com.armandgray.shared.viewModel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

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
@PrepareForTest({PercentageRateViewModelTest.class, TAAPApplication.class})
@RunWith(PowerMockRunner.class)
public class PercentageRateViewModelTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private TAAPAppComponent mockComponent;

    @Mock
    private PerformanceRateRepository mockRepository;

    @Mock
    private LiveData<PerformanceRate> mockPerformanceRate;

    private PercentageRateViewModel testViewModel;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(TAAPApplication.class);
        PowerMockito.when(TAAPApplication.getAppComponent()).thenReturn(mockComponent);

        testViewModel = new PercentageRateViewModel();
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
        testViewModel.onPlusClick();
        Mockito.verify(mockRepository, Mockito.only()).addMake();
    }

    @Test
    public void testAddMiss() {
        testViewModel.onMinusClick();
        Mockito.verify(mockRepository, Mockito.only()).addMiss();
    }

    @Test
    public void testOnSingleInputClick_AddsMake() {
        testViewModel.onSingleInputClick();
        Mockito.verify(mockRepository, Mockito.only()).addMake();
    }

    @Test
    public void testOnDoubleInputClick_AddsMiss() {
        testViewModel.onDoubleInputClick();
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
