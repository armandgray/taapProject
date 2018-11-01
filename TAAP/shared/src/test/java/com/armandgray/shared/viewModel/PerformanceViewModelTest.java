package com.armandgray.shared.viewModel;

import com.armandgray.shared.application.TAAPAppComponent;
import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import io.reactivex.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@Config(manifest = Config.NONE)
@PrepareForTest({DrillViewModelTest.class, TAAPApplication.class})
@RunWith(PowerMockRunner.class)
public class PerformanceViewModelTest {

    @SuppressWarnings("ConstantConditions")
    private static final Drill TEST_DRILL = new Drill("TEST_TITLE", 3, null);

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    TAAPAppComponent mockComponent;

    @Mock
    DrillRepository mockRepository;

    @Mock
    Observable<Performance> mockPerformanceObservable;

    @Mock
    Observable<Drill> mockActiveDrillObservable;

    private PerformanceViewModel testViewModel;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(TAAPApplication.class);
        PowerMockito.when(TAAPApplication.getAppComponent()).thenReturn(mockComponent);

        testViewModel = new PerformanceViewModel();
        testViewModel.drillRepository = mockRepository;

        Mockito.when(mockRepository.getActiveDrillObservable())
                .thenReturn(mockActiveDrillObservable);
        Mockito.when(mockRepository.getPerformanceObservable())
                .thenReturn(mockPerformanceObservable);
        Mockito.when(mockRepository.getCompletionObservable())
                .thenReturn(mockPerformanceObservable);
    }

    @Test
    public void testConstructor_DoesInjectDependencies() {
        // TODO Implement remaining tests
        Mockito.verify(mockComponent, Mockito.times(1)).inject(testViewModel);
    }

    @Test
    public void testGetActiveDrill() {
        Assert.assertThat(testViewModel.getActiveDrill(), is(notNullValue()));
    }

    @Test
    public void testGetActiveDrill_DoesSubscribeToObservable() {
        testViewModel.getActiveDrill();
        Mockito.verify(mockRepository, Mockito.times(1)).getActiveDrillObservable();
        // TODO implement subscribe testing
    }

    @Test
    public void testGetPerformance() {
        Assert.assertThat(testViewModel.getPerformance(), is(notNullValue()));
    }

    @Test
    public void testGetPerformance_DoesSubscribeToObservable() {
        testViewModel.getPerformance();
        Mockito.verify(mockRepository, Mockito.times(1)).getPerformanceObservable();
        // TODO implement subscribe testing
    }

    @Test
    public void testGetCompletionObserver() {
        Assert.assertThat(testViewModel.getCompletionObserver(), is(notNullValue()));
    }

    @Test
    public void testGetCompletionObserver_DoesSubscribeToObservable() {
        testViewModel.getCompletionObserver();
        Mockito.verify(mockRepository, Mockito.times(1)).getCompletionObservable();
        // TODO implement subscribe testing
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

    @Ignore
    @Test
    public void testPerformanceObserver() {
        // TODO Implement Test
//        PerformanceViewModel.PerformanceObserver observer = testViewModel
//                .new PerformanceViewModel.PerformanceObserver() {
//
//            @Override
//            public void onNext(Object o) {
//
//            }
//        };
//
//        Assert.assertThat(observer, is(notNullValue()));
    }

    @Test
    public void testToString() {
        Assert.assertThat(testViewModel.toString(),
                is("PerformanceViewModel@" + Integer.toHexString(testViewModel.hashCode())));
    }

    @After
    public void tearDown() {
        testViewModel = null;
    }
}
