package com.armandgray.shared.viewModel;

import com.armandgray.shared.application.TAAPAppComponent;
import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;

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

import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@Config(manifest = Config.NONE)
@PrepareForTest({DrillViewModelTest.class, TAAPApplication.class})
@RunWith(PowerMockRunner.class)
public class DrillViewModelTest {

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
    LiveData<Performance> mockPerformanceRate;

    @Mock
    LiveData<List<Drill>> mockDrills;

    @Mock
    LiveData<Drill> mockActiveDrill;

    private DrillViewModel testViewModel;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(TAAPApplication.class);
        PowerMockito.when(TAAPApplication.getAppComponent()).thenReturn(mockComponent);

        testViewModel = new DrillViewModel();
        testViewModel.repository = mockRepository;

        Mockito.when(mockRepository.getDrills()).thenReturn(mockDrills);
        Mockito.when(mockRepository.getActiveDrill()).thenReturn(mockActiveDrill);
        Mockito.when(mockRepository.getPerformance()).thenReturn(mockPerformanceRate);
        Mockito.when(mockRepository.getCompletionObserver()).thenReturn(mockPerformanceRate);
    }

    @Test
    public void testConstructor_DoesInjectDependencies() {
        // TODO Implement remaining tests
        Mockito.verify(mockComponent, Mockito.times(1)).inject(testViewModel);
    }

    @Test
    public void testGetDrills() {
        Assert.assertThat(testViewModel.getDrills(), is(notNullValue()));
    }

    @Test
    public void testGetActiveDrill() {
        Assert.assertThat(testViewModel.getActiveDrill(), is(notNullValue()));
    }

    @Test
    public void testGetCurrentRate() {
        Assert.assertThat(testViewModel.getPerformance(), is(notNullValue()));
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
    public void testOnDrillSelected_DoesSetActiveDrill() {
        testViewModel.onDrillSelected(TEST_DRILL);
        Mockito.verify(mockRepository, Mockito.times(1)).setActiveDrill(TEST_DRILL);
    }

    @Test
    public void testOnCleared() {
        testViewModel.onCleared();
        // Nothing
    }

    @Test
    public void testToString() {
        Assert.assertThat(testViewModel.toString(),
                is("DrillViewModel@" + Integer.toHexString(testViewModel.hashCode())));
    }

    @After
    public void tearDown() {
        testViewModel = null;
    }
}
