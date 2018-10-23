package com.armandgray.shared.viewModel;

import com.armandgray.shared.application.TAAPAppComponent;
import com.armandgray.shared.application.TAAPApplication;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.UXPreference;

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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import io.reactivex.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@Config(manifest = Config.NONE)
@PrepareForTest({DrillViewModelTest.class, TAAPApplication.class})
@RunWith(PowerMockRunner.class)
public class PreferencesViewModelTest {

    @SuppressWarnings("ConstantConditions")
    private static final Drill TEST_DRILL = new Drill("TEST_TITLE", 3, null);

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    TAAPAppComponent mockComponent;

    @Mock
    PreferencesRepository mockRepository;

    @Mock
    Observable<UXPreference> mockPreferenceObservable;

    @Mock
    Observable<UXPreference.Value> mockPreferenceValueObservable;

    @Mock
    UXPreference mockPreference;

    @Mock
    UXPreference.Value mockPreferenceValue;

    private PreferencesViewModel testViewModel;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(TAAPApplication.class);
        PowerMockito.when(TAAPApplication.getAppComponent()).thenReturn(mockComponent);

        testViewModel = new PreferencesViewModel();
        testViewModel.repository = mockRepository;

        Mockito.when(mockRepository.getActivePreferenceObservable()).thenReturn(mockPreferenceObservable);
        Mockito.when(mockRepository.getActiveValueObservable()).thenReturn(mockPreferenceValueObservable);
    }

    @Test
    public void testConstructor_DoesInjectDependencies() {
        // TODO Implement remaining tests
        Mockito.verify(mockComponent, Mockito.times(1)).inject(testViewModel);
    }

    @Test
    public void testGetActivePreference() {
        Assert.assertThat(testViewModel.getActivePreference(), is(notNullValue()));
    }

    @Test
    public void testGetActivePreference_DoesSubscribeToObservable() {
        testViewModel.getActivePreference();
        Mockito.verify(mockRepository, Mockito.times(1)).getActivePreferenceObservable();
        // TODO implement subscribe testing
    }

    @Test
    public void testSetActivePreference() {
        testViewModel.setActivePreference(mockPreference);
        Mockito.verify(mockRepository, Mockito.times(1)).setActivePreference(mockPreference);
    }

    @Test
    public void testGetActiveValue() {
        Assert.assertThat(testViewModel.getActiveValue(), is(notNullValue()));
    }

    @Test
    public void testGetActiveValue_DoesSubscribeToObservable() {
        testViewModel.getActiveValue();
        Mockito.verify(mockRepository, Mockito.times(1)).getActiveValueObservable();
        // TODO implement subscribe testing
    }

    @Test
    public void testSetActiveValue() {
        testViewModel.setActiveValue(mockPreferenceValue);
        Mockito.verify(mockRepository, Mockito.times(1)).setActiveValue(mockPreferenceValue);
    }

    @Test
    public void testOnPreferenceUpdated() {
        testViewModel.onPreferenceUpdated();
        Mockito.verify(mockRepository, Mockito.times(1)).onPreferenceUpdated();
    }

    @Test
    public void testToString() {
        Assert.assertThat(testViewModel.toString(),
                is("PreferencesViewModel@" + Integer.toHexString(testViewModel.hashCode())));
    }

    @Test
    public void testPreferenceObserver() {
        // TODO Implement Test
//        PreferenceViewModel.PreferenceObserver observer = testViewModel.new PreferenceViewModel.PreferenceObserver() {
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
