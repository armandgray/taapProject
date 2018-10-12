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
import androidx.lifecycle.LiveData;

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
    LiveData<UXPreference> mockPreferenceLiveData;

    @Mock
    LiveData<UXPreference.Value> mockPreferenceValueLiveData;

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

        Mockito.when(mockRepository.getSelectedPreference()).thenReturn(mockPreferenceLiveData);
        Mockito.when(mockRepository.getSelectedValue()).thenReturn(mockPreferenceValueLiveData);
    }

    @Test
    public void testConstructor_DoesInjectDependencies() {
        // TODO Implement remaining tests
        Mockito.verify(mockComponent, Mockito.times(1)).inject(testViewModel);
    }

    @Test
    public void testGetSelectedPreference() {
        Assert.assertThat(testViewModel.getSelectedPreference(), is(notNullValue()));
    }

    @Test
    public void testSetSelectedPreference() {
        testViewModel.setSelectedPreference(mockPreference);
        Mockito.verify(mockRepository, Mockito.times(1)).setSelectedPreference(mockPreference);
    }

    @Test
    public void testGetSelectedValue() {
        Assert.assertThat(testViewModel.getSelectedValue(), is(notNullValue()));
    }

    @Test
    public void testSetSelectedValue() {
        testViewModel.setSelectedValue(mockPreferenceValue);
        Mockito.verify(mockRepository, Mockito.times(1)).setSelectedValue(mockPreferenceValue);
    }

    @Test
    public void testOnPreferenceUpdated() {
        testViewModel.onPreferenceUpdated();
        Mockito.verify(mockRepository, Mockito.times(1)).onPreferenceUpdated();
    }

    @Test
    public void testOnCleared() {
        testViewModel.onCleared();
        // Nothing
    }

    @Test
    public void testToString() {
        Assert.assertThat(testViewModel.toString(),
                is("PreferencesViewModel@" + Integer.toHexString(testViewModel.hashCode())));
    }

    @After
    public void tearDown() {
        testViewModel = null;
    }
}
