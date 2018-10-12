package com.armandgray.shared.viewModel;

import com.armandgray.shared.db.DrillDatabase;
import com.armandgray.shared.db.PerformanceDao;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;
import com.armandgray.shared.model.UXPreference;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import io.reactivex.functions.Consumer;

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
    private DrillDatabase mockDatabase;

    @Mock
    private PerformanceDao mockPerformanceDao;

    @Mock
    private PreferencesRepository mockPreferencesRepository;

    @Mock
    private UXPreference mockPreference;

    private DrillRepository testRepository;
    private Consumer<UXPreference> testConsumer;

    @SuppressWarnings("ConstantConditions")
    @Before
    public void setUp() {
        Mockito.doAnswer((Answer<Void>) invocation -> {
            testConsumer = invocation.getArgument(0);
            return null;
        }).when(mockPreferencesRepository).addPreferenceConsumer(Mockito.any());

        Mockito.when(mockPreference.getCategory()).thenReturn(UXPreference.Category.REPS_BASED);

        testRepository = new DrillRepository(mockPreferencesRepository);
        testRepository.database = mockDatabase;
        testRepository.completion.setValue(null);
        testRepository.getPerformance().getValue().clear();

        Mockito.when(mockDatabase.performanceDao()).thenReturn(mockPerformanceDao);
    }

    @Test
    public void testConstructor_DoesAddPreferenceConsumer() {
        Mockito.verify(mockPreferencesRepository, Mockito.times(1))
                .addPreferenceConsumer(Mockito.any());
    }

    @Test
    public void testConstructor_DoesSetDrills() {
        Assert.assertThat(testRepository.getDrills().getValue(), is(notNullValue()));
    }

    @Test
    public void testConstructor_DoesSetActiveDrill() {
        Assert.assertThat(testRepository.getActiveDrill().getValue(), is(notNullValue()));
    }

    @Test
    public void testConstructor_DoesSetPerformance() {
        Assert.assertThat(testRepository.getPerformance().getValue(), is(notNullValue()));
    }

    @Test
    public void testAddPreferenceConsumer_DoesUpdatePerformance() throws Exception {
        Performance previous = testRepository.getPerformance().getValue();
        testConsumer.accept(mockPreference);
        Assert.assertThat(testRepository.getPerformance().getValue(), is(not(previous)));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddPreferenceConsumer_DoesNothing_IfCategoryIsNotDrill() throws Exception {
        Mockito.when(mockPreference.getCategory()).thenReturn(UXPreference.Category.DATA);
        Performance previous = testRepository.getPerformance().getValue();
        testConsumer.accept(mockPreference);
        Assert.assertThat(testRepository.getPerformance().getValue(), is(previous));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddPreferenceConsumer_DoesNothing_IfActiveDrillIsNull() throws Exception {
        testRepository.activeDrill.setValue(null);
        Performance previous = testRepository.getPerformance().getValue();
        testConsumer.accept(mockPreference);
        Assert.assertThat(testRepository.getPerformance().getValue(), is(previous));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddPreferenceConsumer_DoesUseActiveDrill() throws Exception {
        int expected = testRepository.getActiveDrill().getValue().getId();
        testConsumer.accept(mockPreference);
        Assert.assertThat(testRepository.getPerformance().getValue().getDrillId(), is(expected));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddPreferenceConsumer_DoesCopyCount() throws Exception {
        int expected = 14;
        testRepository.getPerformance().getValue().setCount(expected);
        testConsumer.accept(mockPreference);
        Assert.assertThat(testRepository.getPerformance().getValue().getCount(), is(expected));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddPreferenceConsumer_DoesCopyTotal() throws Exception {
        int expected = 6;
        testRepository.getPerformance().getValue().setTotal(expected);
        testConsumer.accept(mockPreference);
        Assert.assertThat(testRepository.getPerformance().getValue().getTotal(), is(expected));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddPreferenceConsumer_DoesCopyStartTime() throws Exception {
        long expected = 14;
        testRepository.getPerformance().getValue().setStartTime(expected);
        testConsumer.accept(mockPreference);
        Assert.assertThat(testRepository.getPerformance().getValue().getStartTime(), is(expected));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddPreferenceConsumer_DoesNotCopyFromNull() throws Exception {
        testRepository.performance.setValue(null);
        testConsumer.accept(mockPreference);
        Assert.assertThat(testRepository.getPerformance().getValue().getTotal(), is(0));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddPreferenceConsumer_DoesAssignCompletionObserver() throws Exception {
        // Arrange
        int expected = 5;
        Drill testDrill = new Drill("TEST_TITLE", 3, null);
        testDrill.getPreference().getValues().forEach(value -> value.setValue(expected));
        testRepository.setActiveDrill(testDrill);
        testRepository.getPerformance().getValue().setTotal(expected);
        testRepository.getPerformance().getValue().setCount(expected);

        // Act
        testConsumer.accept(mockPreference);

        // Assert
        Assert.assertThat(testRepository.getCompletionObserver().getValue(), is(notNullValue()));
    }

    @Test
    public void testGetDrills() {
        Assert.assertThat(testRepository.getDrills(), is(notNullValue()));
        Assert.assertThat(testRepository.getDrills().getValue(), is(Drill.Defaults.getDefaults()));
    }

    @Test
    public void testGetActiveDrill() {
        Assert.assertThat(testRepository.getActiveDrill(), is(notNullValue()));
        Assert.assertThat(testRepository.getActiveDrill().getValue(),
                is(Drill.Defaults.getDefaults().get(0)));
    }

    @Test
    public void testGetCurrentRate() {
        Assert.assertThat(testRepository.getPerformance(), is(notNullValue()));
        Assert.assertThat(testRepository.getPerformance().getValue(), is(notNullValue()));
    }

    @Test
    public void testGetCompletionObserver() {
        Assert.assertThat(testRepository.getCompletionObserver(), is(notNullValue()));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddMake_DoesIncrementLiveDataValue() {
        // Arrange
        Performance performance = testRepository.getPerformance().getValue();
        Assert.assertThat(performance.getCount(), is(0));
        Assert.assertThat(performance.getTotal(), is(0));

        // Act
        testRepository.addMake();

        // Assert
        performance = testRepository.getPerformance().getValue();
        Assert.assertThat(performance.getCount(), is(1));
        Assert.assertThat(performance.getTotal(), is(1));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddMake_OnCompletion_DoesAssignCompletionObserver() {
        // Arrange
        Assert.assertThat(testRepository.getCompletionObserver().getValue(), is(nullValue()));

        // Act
        for (int i = 0; i < 10; i++) {
            testRepository.addMake();
        }

        // Assert
        Performance performance = testRepository.getCompletionObserver().getValue();
        Assert.assertThat(performance.getCount(), is(10));
        Assert.assertThat(performance.getTotal(), is(10));
    }

    @Test
    public void testAddMake_OnCompletion_DoesStorePerformance() {
        // Arrange
        // TODO Complete Test

        // Act

        // Assert
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddMake_OnCompletion_DoesClearRate() {
        // Arrange
        Performance performance;
        for (int i = 1; i < 10; i++) {
            testRepository.addMake();
            performance = testRepository.getPerformance().getValue();
            Assert.assertThat(performance.getCount(), is(i));
            Assert.assertThat(performance.getTotal(), is(i));
        }

        // Act
        testRepository.addMake(); // Now total == max

        // Assert
        performance = testRepository.getPerformance().getValue();
        Assert.assertThat(performance.getCount(), is(0));
        Assert.assertThat(performance.getTotal(), is(0));
    }

    @Test
    public void testAddMake_DoesNothing_IfPerformanceIsNull() {
        testRepository.performance.setValue(null);
        testRepository.addMake();
        Assert.assertThat(testRepository.getPerformance().getValue(), is(nullValue()));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddMake_BeforeCompletion_DoesNotAssignCompletionObserver() {
        // Arrange
        Assert.assertThat(testRepository.getCompletionObserver().getValue(), is(nullValue()));

        // Act
        for (int i = 0; i < 9; i++) {
            testRepository.addMake();
        }

        // Assert
        Assert.assertThat(testRepository.getCompletionObserver().getValue(), is(nullValue()));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddMiss_DoesIncrementLiveDataValue() {
        // Arrange
        Performance performance = testRepository.getPerformance().getValue();
        performance.clear();
        Assert.assertThat(performance.getCount(), is(0));
        Assert.assertThat(performance.getTotal(), is(0));

        // Act
        testRepository.addMiss();

        // Assert
        performance = testRepository.getPerformance().getValue();
        Assert.assertThat(performance.getCount(), is(0));
        Assert.assertThat(performance.getTotal(), is(1));
    }

    @Test
    public void testAddMiss_OnCompletion_DoesAssignCompletionObserver() {
        // Arrange
        Assert.assertThat(testRepository.getCompletionObserver().getValue(), is(nullValue()));

        // Act
        for (int i = 0; i < 10; i++) {
            testRepository.addMiss();
        }

        // Assert
        Performance performance = testRepository.getCompletionObserver().getValue();
        Assert.assertThat(performance.getCount(), is(0));
        Assert.assertThat(performance.getTotal(), is(10));
    }

    @Test
    public void testAddMiss_OnCompletion_DoesStorePerformance() {
        // Arrange
        // TODO Complete Test

        // Act

        // Assert
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddMiss_OnCompletion_DoesClearRate() {
        // Arrange
        Performance performance = testRepository.getPerformance().getValue();
        performance.clear();

        for (int i = 1; i < 10; i++) {
            testRepository.addMiss();
            Assert.assertThat(performance.getCount(), is(0));
            Assert.assertThat(performance.getTotal(), is(i));
        }

        // Act
        testRepository.addMiss(); // Now total == max

        // Assert
        performance = testRepository.getPerformance().getValue();
        Assert.assertThat(performance.getCount(), is(0));
        Assert.assertThat(performance.getTotal(), is(0));
    }

    @Test
    public void testAddMiss_DoesNothing_IfPerformanceIsNull() {
        testRepository.performance.setValue(null);
        testRepository.addMiss();
        Assert.assertThat(testRepository.getPerformance().getValue(), is(nullValue()));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddMiss_BeforeCompletion_DoesNotAssignCompletionObserver() {
        // Arrange
        Assert.assertThat(testRepository.getCompletionObserver().getValue(), is(nullValue()));

        // Act
        for (int i = 0; i < 9; i++) {
            testRepository.addMiss();
        }

        // Assert
        Assert.assertThat(testRepository.getCompletionObserver().getValue(), is(nullValue()));
    }

    @Test
    public void testSetActiveDrill_DoesSetValue() {
        testRepository.setActiveDrill(TEST_DRILL);
        Assert.assertThat(testRepository.getActiveDrill().getValue(), is(TEST_DRILL));
    }

    @Test
    public void testSetActiveDrill_DoesSetPerformance_IfPerformanceIsNull() {
        testRepository.performance.setValue(null);
        testRepository.setActiveDrill(TEST_DRILL);
        Assert.assertThat(testRepository.getPerformance().getValue(), is(notNullValue()));
    }

    @Test
    public void testSetActiveDrill_OnCompletion_DoesAssignCompletionObserver() {
        // Arrange
        Assert.assertThat(testRepository.getCompletionObserver().getValue(), is(nullValue()));
        testRepository.addMiss();

        // Act
        testRepository.setActiveDrill(TEST_DRILL);

        // Assert
        Performance performance = testRepository.getCompletionObserver().getValue();
        Assert.assertThat(performance.getCount(), is(0));
        Assert.assertThat(performance.getTotal(), is(1));
    }

    @Test
    public void testSetActiveDrill_OnCompletion_DoesStorePerformance() {
        // Arrange
        // TODO Complete Test

        // Act

        // Assert
    }

    @Test
    public void testSetActiveDrill_DoesNotStorePerformance_IfTotalIsNotZero() {
        // Arrange
        // TODO Complete Test

        // Act

        // Assert
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testSetActiveDrill_OnCompletion_DoesClearRate() {
        // Arrange
        Performance performance = testRepository.getPerformance().getValue();
        performance.clear();
        testRepository.addMiss();

        // Act
        testRepository.setActiveDrill(TEST_DRILL);

        // Assert
        performance = testRepository.getPerformance().getValue();
        Assert.assertThat(performance.getCount(), is(0));
        Assert.assertThat(performance.getTotal(), is(0));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testSetActiveDrill_DoesChangePerformanceDrillId() {
        Assert.assertThat(testRepository.getPerformance().getValue().getDrillId(),
                is(not(TEST_DRILL.getId())));
        testRepository.setActiveDrill(TEST_DRILL);
        Assert.assertThat(testRepository.getPerformance().getValue().getDrillId(),
                is(TEST_DRILL.getId()));
    }

    @Test
    public void testToString() {
        Assert.assertThat(testRepository.toString(),
                is("DrillRepository@" + Integer.toHexString(testRepository.hashCode())));
    }

    @After
    public void tearDown() {
        testRepository = null;
    }
}
