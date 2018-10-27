package com.armandgray.shared.viewModel;

import com.armandgray.shared.db.DatabaseManager;
import com.armandgray.shared.rx.SchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class LocationRepositoryTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private DatabaseManager mockDatabaseManager;

    @Mock
    private PreferencesRepository mockPreferencesRepository;

    @Mock
    private SchedulerProvider mockSchedulers;

    private LocationRepository testRepository;

    @Before
    public void setUp() {
        this.testRepository = new LocationRepository(mockPreferencesRepository);
    }

    @Ignore
    @Test
    public void testGetRecentWorkoutObservable() {
        // TODO test remaining
    }

    @After
    public void tearDown() {
        testRepository = null;
    }
}
