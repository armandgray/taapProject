package com.armandgray.shared.viewModel;

import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.PerformanceRate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

public class DrillRepositoryTest {

    @SuppressWarnings("ConstantConditions")
    private static final Drill TEST_DRILL = new Drill("TEST_TITLE", 3, null);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private DrillRepository testRepository;

    @Before
    public void setUp() {
        testRepository = new DrillRepository();
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
    public void testGetActiveDrill() {
        Assert.assertThat(testRepository.getActiveDrill(), is(notNullValue()));
        Assert.assertThat(testRepository.getActiveDrill().getValue(),
                is(Drill.Defaults.getDefault()));
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
        PerformanceRate rate = testRepository.getPerformance().getValue();
        rate.clear();
        Assert.assertThat(rate.getCount(), is(0));
        Assert.assertThat(rate.getTotal(), is(0));

        // Act
        testRepository.addMake();

        // Assert
        rate = testRepository.getPerformance().getValue();
        Assert.assertThat(rate.getCount(), is(1));
        Assert.assertThat(rate.getTotal(), is(1));
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
        PerformanceRate rate = testRepository.getCompletionObserver().getValue();
        Assert.assertThat(rate.getCount(), is(10));
        Assert.assertThat(rate.getTotal(), is(10));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddMake_OnCompletion_DoesClearRate() {
        // Arrange
        PerformanceRate rate;
        for (int i = 1; i < 10; i++) {
            testRepository.addMake();
            rate = testRepository.getPerformance().getValue();
            Assert.assertThat(rate.getCount(), is(i));
            Assert.assertThat(rate.getTotal(), is(i));
        }

        // Act
        testRepository.addMake(); // Now total == max

        // Assert
        rate = testRepository.getPerformance().getValue();
        Assert.assertThat(rate.getCount(), is(0));
        Assert.assertThat(rate.getTotal(), is(0));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddMiss_DoesIncrementLiveDataValue() {
        // Arrange
        PerformanceRate rate = testRepository.getPerformance().getValue();
        Assert.assertThat(rate.getCount(), is(0));
        Assert.assertThat(rate.getTotal(), is(0));

        // Act
        testRepository.addMiss();

        // Assert
        rate = testRepository.getPerformance().getValue();
        Assert.assertThat(rate.getCount(), is(0));
        Assert.assertThat(rate.getTotal(), is(1));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddMiss_OnCompletion_DoesAssignCompletionObserver() {
        // Arrange
        Assert.assertThat(testRepository.getCompletionObserver().getValue(), is(nullValue()));

        // Act
        for (int i = 0; i < 10; i++) {
            testRepository.addMiss();
        }

        // Assert
        PerformanceRate rate = testRepository.getCompletionObserver().getValue();
        Assert.assertThat(rate.getCount(), is(0));
        Assert.assertThat(rate.getTotal(), is(10));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddMiss_OnCompletion_DoesClearRate() {
        // Arrange
        PerformanceRate rate = testRepository.getPerformance().getValue();
        rate.clear();

        for (int i = 1; i < 10; i++) {
            testRepository.addMiss();
            Assert.assertThat(rate.getCount(), is(0));
            Assert.assertThat(rate.getTotal(), is(i));
        }

        // Act
        testRepository.addMiss(); // Now total == max

        // Assert
        rate = testRepository.getPerformance().getValue();
        Assert.assertThat(rate.getCount(), is(0));
        Assert.assertThat(rate.getTotal(), is(0));
    }

    @Test
    public void testSetActiveDrill() {
        testRepository.setActiveDrill(TEST_DRILL);
        Assert.assertThat(testRepository.getActiveDrill().getValue(), is(TEST_DRILL));
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
