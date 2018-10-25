package com.armandgray.shared.viewModel;

import com.armandgray.shared.db.DatabaseManager;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.UXPreference;
import com.armandgray.shared.rx.SchedulerProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

public class PreferencesRepositoryTest {

    @SuppressWarnings("ConstantConditions")
    private static final Drill TEST_DRILL = new Drill("TEST_TITLE", 3, null);

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private DatabaseManager mockDatabaseManager;

    @Mock
    private SchedulerProvider mockSchedulers;

    private PreferencesRepository testRepository;

    @Before
    public void setUp() {
        this.testRepository = new PreferencesRepository(mockDatabaseManager, mockSchedulers);
    }

    @Ignore
    @Test
    public void testGetActivePreferenceObservable() {
        Assert.assertThat(testRepository.getActivePreferenceObservable(), is(notNullValue()));
    }

    @Ignore
    @Test
    public void testSetActivePreference() {
        Assert.assertThat(testRepository.activePreferenceSubject.getValue(), is(nullValue()));
        UXPreference preference = TEST_DRILL.getPreference();
        testRepository.setActivePreference(preference);
        Assert.assertThat(testRepository.activePreferenceSubject.getValue(), is(preference));
    }

    @Ignore
    @Test
    public void testGetActiveValueObservable() {
        Assert.assertThat(testRepository.getActiveValueObservable(), is(notNullValue()));
    }

    @Ignore
    @Test
    public void testSetActiveValue() {
        Assert.assertThat(testRepository.activeValueSubject.getValue(), is(nullValue()));
        UXPreference.Value value = TEST_DRILL.getPreference().getValues().get(0);
        testRepository.setActiveValue(value);
        Assert.assertThat(testRepository.activeValueSubject.getValue(), is(value));
    }

    @Ignore
    @Test
    public void testGetPreferenceUpdateObservable() {
        Assert.assertThat(testRepository.getPreferenceUpdateObservable(), is(notNullValue()));
    }

    @Ignore
    @Test
    public void testOnPreferenceUpdated_DoesCallUpdateSubjectOnNext() {
        UXPreference preference = TEST_DRILL.getPreference();
        testRepository.activePreferenceSubject.onNext(preference);
        testRepository.onPreferenceUpdated();
        Assert.assertThat(testRepository.activePreferenceSubject.getValue(), is(notNullValue()));
        Assert.assertThat(testRepository.getPreferenceUpdateObservable().blockingLast(),
                is(preference));
    }

    @Ignore
    @SuppressWarnings("ConstantConditions")
    @Test
    public void testOnPreferenceUpdated_DoesNothingIfActivePreferenceIsNull() {
        Assert.assertThat(testRepository.activePreferenceSubject.getValue(), is(nullValue()));
        testRepository.onPreferenceUpdated();
        UXPreference defaultValue = new Drill("test", 0, null).getPreference();
        Assert.assertThat(testRepository.preferenceUpdateSubject.blockingLast(defaultValue),
                is(defaultValue));
    }

    @After
    public void tearDown() {
        testRepository = null;
    }
}
