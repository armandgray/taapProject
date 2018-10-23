package com.armandgray.shared.viewModel;

import com.armandgray.shared.db.DatabaseManager;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LogsRepositoryTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private DatabaseManager mockDatabaseManager;

    @Mock
    private SchedulerProvider mockSchedulers;

    private LogsRepository testRepository;

    @Before
    public void setUp() {
        this.testRepository = new LogsRepository();
    }

    @Ignore
    @Test
    public void testGetRecentWorkoutObservable() {
        Assert.assertThat(testRepository.getRecentWorkoutObservable(), is(notNullValue()));
        // TODO test remaining
    }

    @After
    public void tearDown() {
        testRepository = null;
    }
}
