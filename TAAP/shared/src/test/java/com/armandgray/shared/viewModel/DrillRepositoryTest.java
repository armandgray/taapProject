package com.armandgray.shared.viewModel;

import com.armandgray.shared.db.DatabaseManager;
import com.armandgray.shared.db.DrillDao;
import com.armandgray.shared.db.PerformanceDao;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;
import com.armandgray.shared.model.UXPreference;
import com.armandgray.shared.rx.SchedulerProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.TestScheduler;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

public class DrillRepositoryTest {

    @SuppressWarnings("ConstantConditions")
    private static final Drill TEST_DRILL = new Drill("TEST_TITLE", 3, null);

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private DatabaseManager mockDatabaseManager;

    @Mock
    private DrillDao mockDrillDao;

    @Mock
    private PerformanceDao mockPerformanceDao;

    @Mock
    private PreferencesRepository mockPreferencesRepository;
    
    @Mock
    private Observable<UXPreference> mockPreferenceUpdateObservable;

    @Mock
    private UXPreference mockPreference;

    private DrillRepository testRepository;
    private Consumer<UXPreference> testConsumer;
    private Scheduler testScheduler = new TestScheduler();
    private SchedulerProvider schedulerProvider = new SchedulerProvider() {
        @Override
        public Scheduler ui() {
            return testScheduler;
        }

        @Override
        public Scheduler io() {
            return testScheduler;
        }

        @Override
        public Scheduler trampoline() {
            return testScheduler;
        }

        @Override
        public Scheduler computation() {
            return testScheduler;
        }

        @Override
        public Scheduler newThread() {
            return testScheduler;
        }

        @Override
        public Scheduler single() {
            return testScheduler;
        }
    };

    @SuppressWarnings("ConstantConditions")
    @Before
    public void setUp() {
        //noinspection ResultOfMethodCallIgnored,unchecked
//        Mockito.doAnswer((Answer<Void>) invocation -> {
//            testConsumer = invocation.getArgument(0);
//            return null;
//        }).when(mockPreferenceUpdateObservable).subscribe(Mockito.any(Consumer.class));
        
        Mockito.when(mockPreferencesRepository.getPreferenceUpdateObservable())
                .thenReturn(mockPreferenceUpdateObservable);
        Mockito.when(mockPreference.getCategory()).thenReturn(UXPreference.Category.REPS_BASED);
        Mockito.when(mockDatabaseManager.getDrillDao()).thenReturn(mockDrillDao);
        Mockito.when(mockDrillDao.drill(Mockito.anyInt())).thenReturn(Single.just(TEST_DRILL));
        Mockito.when(mockDatabaseManager.getPerformanceDao()).thenReturn(mockPerformanceDao);

        testRepository = new DrillRepository(
                mockPreferencesRepository, mockDatabaseManager, schedulerProvider);
        testRepository.updateDrillSubscribers(Drill.Defaults.getDefaults());
//        testRepository.completionSubject.setValue(null);
    }

    @Ignore
    @Test
    public void testConstructor_DoesSubscribeToPreferenceUpdateObserver() {
        //noinspection unchecked,ResultOfMethodCallIgnored
        Mockito.verify(mockPreferenceUpdateObservable, Mockito.times(1))
                .subscribe(Mockito.any(Consumer.class));
    }

    @Test
    public void testConstructor_DoesSetDrills() {
        // TODO Implement test
    }

    @Test
    public void testConstructor_DoesSetActiveDrill() {
        // TODO Implement test
    }

    @Test
    public void testConstructor_DoesSetPerformance() {
        // TODO Implement test
    }

    @Ignore
    @Test
    public void testAddPreferenceConsumer_DoesUpdatePerformance() throws Exception {
        Performance previous = testRepository.performanceSubject.getValue();
        testConsumer.accept(mockPreference);
        Assert.assertThat(testRepository.performanceSubject.getValue(), is(not(previous)));
    }

    @SuppressWarnings("ConstantConditions")
    @Ignore
    @Test
    public void testAddPreferenceConsumer_DoesNothing_IfCategoryIsNotDrill() throws Exception {
        Mockito.when(mockPreference.getCategory()).thenReturn(UXPreference.Category.DATA);
        Performance previous = testRepository.performanceSubject.getValue();
        testConsumer.accept(mockPreference);
        Assert.assertThat(testRepository.performanceSubject.getValue(), is(previous));
    }

    @SuppressWarnings("ConstantConditions")
    @Ignore
    @Test
    public void testAddPreferenceConsumer_DoesNothing_IfActiveDrillIsNull() throws Exception {
//        testRepository.activeDrillSubject;
        Performance previous = testRepository.performanceSubject.getValue();
        testConsumer.accept(mockPreference);
        Assert.assertThat(testRepository.performanceSubject.getValue(), is(previous));
    }

    @SuppressWarnings("ConstantConditions")
    @Ignore
    @Test
    public void testAddPreferenceConsumer_DoesUseActiveDrill() throws Exception {
        int expected = testRepository.activeDrillSubject.getValue().getId();
        testConsumer.accept(mockPreference);
        Assert.assertThat(testRepository.performanceSubject.getValue().getDrillId(), is(expected));
    }

    @SuppressWarnings("ConstantConditions")
    @Ignore
    @Test
    public void testAddPreferenceConsumer_DoesCopyCount() throws Exception {
        int expected = 14;
        testRepository.performanceSubject.getValue().setCount(expected);
        testConsumer.accept(mockPreference);
        Assert.assertThat(testRepository.performanceSubject.getValue().getCount(), is(expected));
    }

    @SuppressWarnings("ConstantConditions")
    @Ignore
    @Test
    public void testAddPreferenceConsumer_DoesCopyTotal() throws Exception {
        int expected = 6;
        testRepository.performanceSubject.getValue().setTotal(expected);
        testConsumer.accept(mockPreference);
        Assert.assertThat(testRepository.performanceSubject.getValue().getTotal(), is(expected));
    }

    @SuppressWarnings("ConstantConditions")
    @Ignore
    @Test
    public void testAddPreferenceConsumer_DoesCopyStartTime() throws Exception {
        long expected = 14;
        testRepository.performanceSubject.getValue().setStartTime(expected);
        testConsumer.accept(mockPreference);
        Assert.assertThat(testRepository.performanceSubject.getValue().getStartTime(), is(expected));
    }

    @SuppressWarnings("ConstantConditions")
    @Ignore
    @Test
    public void testAddPreferenceConsumer_DoesNotCopyFromNull() throws Exception {
//        testRepository.performanceSubject.setValue(null);
        testConsumer.accept(mockPreference);
        Assert.assertThat(testRepository.performanceSubject.getValue().getTotal(), is(0));
    }

    @Ignore
    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddPreferenceConsumer_DoesAssignCompletionObserver() throws Exception {
        // Arrange
        int expected = 5;
        Drill testDrill = new Drill("TEST_TITLE", 3, null);
        testDrill.getPreference().getValues().forEach(value -> value.setValue(expected));
        testRepository.setActiveDrill(testDrill);
        testRepository.performanceSubject.getValue().setTotal(expected);
        testRepository.performanceSubject.getValue().setCount(expected);

        // Act
        testConsumer.accept(mockPreference);

        // Assert
        Assert.assertThat(testRepository.completionSubject.blockingLast(), is(notNullValue()));
    }

    @Ignore
    @Test
    public void testGetDrills() {
        Assert.assertThat(testRepository.drillsSubject, is(notNullValue()));
        Assert.assertThat(testRepository.drillsSubject.getValue(), is(Drill.Defaults.getDefaults()));
    }

    @Ignore
    @Test
    public void testGetActiveDrill() {
        Assert.assertThat(testRepository.activeDrillSubject, is(notNullValue()));
        Assert.assertThat(testRepository.activeDrillSubject.getValue(),
                is(Drill.Defaults.getDefaults().get(0)));
    }

    @Ignore
    @Test
    public void testGetCurrentRate() {
        Assert.assertThat(testRepository.performanceSubject, is(notNullValue()));
        Assert.assertThat(testRepository.performanceSubject.getValue(), is(notNullValue()));
    }

    @Ignore
    @Test
    public void testGetCompletionObserver() {
        Assert.assertThat(testRepository.completionSubject, is(notNullValue()));
    }

    @Ignore
    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddMake_DoesIncrementLiveDataValue() {
        // Arrange
        Performance performance = testRepository.performanceSubject.getValue();
        Assert.assertThat(performance.getCount(), is(0));
        Assert.assertThat(performance.getTotal(), is(0));

        // Act
        testRepository.addMake();

        // Assert
        performance = testRepository.performanceSubject.getValue();
        Assert.assertThat(performance.getCount(), is(1));
        Assert.assertThat(performance.getTotal(), is(1));
    }

    @Ignore
    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddMake_OnCompletion_DoesAssignCompletionObserver() {
        // Arrange
        Assert.assertThat(testRepository.completionSubject.blockingLast(), is(nullValue()));

        // Act
        for (int i = 0; i < 10; i++) {
            testRepository.addMake();
        }

        // Assert
        Performance performance = testRepository.completionSubject.blockingLast();
        Assert.assertThat(performance.getCount(), is(10));
        Assert.assertThat(performance.getTotal(), is(10));
    }

    @Ignore
    @Test
    public void testAddMake_OnCompletion_DoesStorePerformance() {
        // Arrange
        // TODO Complete Test

        // Act

        // Assert
    }

    @Ignore
    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddMake_OnCompletion_DoesClearRate() {
        // Arrange
        Performance performance;
        for (int i = 1; i < 10; i++) {
            testRepository.addMake();
            performance = testRepository.performanceSubject.getValue();
            Assert.assertThat(performance.getCount(), is(i));
            Assert.assertThat(performance.getTotal(), is(i));
        }

        // Act
        testRepository.addMake(); // Now total == max

        // Assert
        performance = testRepository.performanceSubject.getValue();
        Assert.assertThat(performance.getCount(), is(0));
        Assert.assertThat(performance.getTotal(), is(0));
    }

    @Ignore
    @Test
    public void testAddMake_DoesNothing_IfPerformanceIsNull() {
//        testRepository.performanceSubject.setValue(null);
        testRepository.addMake();
        Assert.assertThat(testRepository.performanceSubject.getValue(), is(nullValue()));
    }

    @Ignore
    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddMake_BeforeCompletion_DoesNotAssignCompletionObserver() {
        // Arrange
        Assert.assertThat(testRepository.completionSubject.blockingLast(), is(nullValue()));

        // Act
        for (int i = 0; i < 9; i++) {
            testRepository.addMake();
        }

        // Assert
        Assert.assertThat(testRepository.completionSubject.blockingLast(), is(nullValue()));
    }

    @Ignore
    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddMiss_DoesIncrementLiveDataValue() {
        // Arrange
        Performance performance = testRepository.performanceSubject.getValue();
        performance.clear();
        Assert.assertThat(performance.getCount(), is(0));
        Assert.assertThat(performance.getTotal(), is(0));

        // Act
        testRepository.addMiss();

        // Assert
        performance = testRepository.performanceSubject.getValue();
        Assert.assertThat(performance.getCount(), is(0));
        Assert.assertThat(performance.getTotal(), is(1));
    }

    @Ignore
    @Test
    public void testAddMiss_OnCompletion_DoesAssignCompletionObserver() {
        for (int i = 0; i < 10; i++) {
            testRepository.addMiss();
        }

        Performance performance = testRepository.completionSubject.blockingLast();
        Assert.assertThat(performance.getCount(), is(0));
        Assert.assertThat(performance.getTotal(), is(10));
    }

    @Ignore
    @Test
    public void testAddMiss_OnCompletion_DoesStorePerformance() {
        // Arrange
        // TODO Complete Test

        // Act

        // Assert
    }

    @Ignore
    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddMiss_OnCompletion_DoesClearRate() {
        // Arrange
        Performance performance = testRepository.performanceSubject.getValue();
        performance.clear();

        for (int i = 1; i < 10; i++) {
            testRepository.addMiss();
            Assert.assertThat(performance.getCount(), is(0));
            Assert.assertThat(performance.getTotal(), is(i));
        }

        // Act
        testRepository.addMiss(); // Now total == max

        // Assert
        performance = testRepository.performanceSubject.getValue();
        Assert.assertThat(performance.getCount(), is(0));
        Assert.assertThat(performance.getTotal(), is(0));
    }

    @Ignore
    @Test
    public void testAddMiss_DoesNothing_IfPerformanceIsNull() {
//        testRepository.performanceSubject.setValue(null);
        testRepository.addMiss();
        Assert.assertThat(testRepository.performanceSubject.getValue(), is(nullValue()));
    }

    @Ignore
    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddMiss_BeforeCompletion_DoesNotAssignCompletionObserver() {
        for (int i = 0; i < 9; i++) {
            testRepository.addMiss();
        }

        Assert.assertThat(testRepository.completionSubject.blockingLast(), is(nullValue()));
    }

    @Ignore
    @Test
    public void testSetActiveDrill_DoesSetValue() {
        testRepository.setActiveDrill(TEST_DRILL);
        Assert.assertThat(testRepository.activeDrillSubject.getValue(), is(TEST_DRILL));
    }

    @Ignore
    @Test
    public void testSetActiveDrill_DoesSetPerformance_IfPerformanceIsNull() {
//        testRepository.performanceSubject.setValue(null);
        testRepository.setActiveDrill(TEST_DRILL);
        Assert.assertThat(testRepository.performanceSubject.getValue(), is(notNullValue()));
    }

    @Ignore
    @Test
    public void testSetActiveDrill_OnCompletion_DoesAssignCompletionObserver() {
        // Arrange
        testRepository.addMiss();

        // Act
        testRepository.setActiveDrill(TEST_DRILL);

        // Assert
        Performance performance = testRepository.completionSubject.blockingLast();
        Assert.assertThat(performance.getCount(), is(0));
        Assert.assertThat(performance.getTotal(), is(1));
    }

    @Ignore
    @Test
    public void testSetActiveDrill_OnCompletion_DoesStorePerformance() {
        // Arrange
        // TODO Complete Test

        // Act

        // Assert
    }

    @Ignore
    @Test
    public void testSetActiveDrill_DoesNotStorePerformance_IfTotalIsNotZero() {
        // Arrange
        // TODO Complete Test

        // Act

        // Assert
    }

    @Ignore
    @SuppressWarnings("ConstantConditions")
    @Test
    public void testSetActiveDrill_OnCompletion_DoesClearRate() {
        // Arrange
        Performance performance = testRepository.performanceSubject.getValue();
        performance.clear();
        testRepository.addMiss();

        // Act
        testRepository.setActiveDrill(TEST_DRILL);

        // Assert
        performance = testRepository.performanceSubject.getValue();
        Assert.assertThat(performance.getCount(), is(0));
        Assert.assertThat(performance.getTotal(), is(0));
    }

    @Ignore
    @SuppressWarnings("ConstantConditions")
    @Test
    public void testSetActiveDrill_DoesChangePerformanceDrillId() {
        Assert.assertThat(testRepository.performanceSubject.getValue().getDrillId(),
                is(not(TEST_DRILL.getId())));
        testRepository.setActiveDrill(TEST_DRILL);
        Assert.assertThat(testRepository.performanceSubject.getValue().getDrillId(),
                is(TEST_DRILL.getId()));
    }

    @Test
    public void testToString() {
        Assert.assertThat(testRepository.toString(),
                is("DrillRepository@" + Integer.toHexString(testRepository.hashCode())));
    }

    @SuppressWarnings("ConstantConditions")
    @After
    public void tearDown() throws Exception {
        testRepository = null;
//        Performance performance = testRepository.performanceSubject.getValue();
//        if (performance != null) {
//            performance.clear();
//        }
    }
}
