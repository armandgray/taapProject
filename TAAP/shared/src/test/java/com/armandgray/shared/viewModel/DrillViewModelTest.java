package com.armandgray.shared.viewModel;

import com.armandgray.shared.application.TAAPAppComponent;
import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.model.Drill;

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
import io.reactivex.Observable;

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
    private TAAPAppComponent mockComponent;

    @Mock
    private DrillRepository mockRepository;

    @Mock
    private Observable<List<Drill>> mockDrillsObservable;

    @Mock
    private Observable<Drill> mockActiveDrillObservable;

    private DrillViewModel testViewModel;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(TAAPApplication.class);
        PowerMockito.when(TAAPApplication.getAppComponent()).thenReturn(mockComponent);

        testViewModel = new DrillViewModel();
        testViewModel.repository = mockRepository;

        Mockito.when(mockRepository.getDrillsObservable()).thenReturn(mockDrillsObservable);
        Mockito.when(mockRepository.getActiveDrillObservable())
                .thenReturn(mockActiveDrillObservable);
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
    public void testGetDrills_DoesSubscribeToObservable() {
        testViewModel.getDrills();
        Mockito.verify(mockRepository, Mockito.times(1)).getDrillsObservable();
        // TODO implement subscribe testing
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
    public void testOnDrillSelected_DoesSetActiveDrill() {
        testViewModel.onDrillSelected(TEST_DRILL);
        Mockito.verify(mockRepository, Mockito.times(1)).setActiveDrill(TEST_DRILL);
    }

    @Test
    public void testToString() {
        Assert.assertThat(testViewModel.toString(),
                is("DrillViewModel@" + Integer.toHexString(testViewModel.hashCode())));
    }

    @Test
    public void testDrillObserver() {
        // TODO Implement Test
//        DrillViewModel.DrillObserver observer = testViewModel.new DrillViewModel.DrillObserver() {
//            @Override
//            public void onNext(Object o) {
//            }
//        };
//
//        Assert.assertThat(observer, is(notNullValue()));
    }

    @After
    public void tearDown() {
        testViewModel = null;
    }
}
