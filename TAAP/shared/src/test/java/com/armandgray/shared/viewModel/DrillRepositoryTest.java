package com.armandgray.shared.viewModel;

import com.armandgray.shared.db.DrillDatabase;
import com.armandgray.shared.db.PerformanceDao;
import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

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
    DrillDatabase mockDatabase;

    @Mock
    PerformanceDao mockPerformanceDao;

    private DrillRepository testRepository;

    @SuppressWarnings("ConstantConditions")
    @Before
    public void setUp() {
        testRepository = new DrillRepository();
        testRepository.database = mockDatabase;
        testRepository.completion.setValue(null);
        testRepository.getPerformance().getValue().clear();

        Mockito.when(mockDatabase.performanceDao()).thenReturn(mockPerformanceDao);
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
    public void testConstructor_DoesSetCurrentRate() {
        Assert.assertThat(testRepository.getPerformance().getValue(), is(notNullValue()));
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
    public void testSetActiveDrill_DoesNothing_IfPerformanceIsNull() {
        testRepository.performance.setValue(null);
        testRepository.setActiveDrill(TEST_DRILL);
        Assert.assertThat(testRepository.getActiveDrill().getValue(), is(not(TEST_DRILL)));
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
