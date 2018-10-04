package com.armandgray.shared.db;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.armandgray.shared.model.PerformanceRate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

public class ShootingPerformanceRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private ShootingPerformanceRepository testRepository;

    @Before
    public void setUp() {
        testRepository = new ShootingPerformanceRepository();
    }

    @Test
    public void testConstructor_DoesSetCurrentRate() {
        Assert.assertThat(testRepository.getCurrentRate().getValue(), is(notNullValue()));
    }

    @Test
    public void testGetCurrentRate() {
        Assert.assertThat(testRepository.getCurrentRate(), is(notNullValue()));
        Assert.assertThat(testRepository.getCurrentRate().getValue(), is(notNullValue()));
    }

    @Test
    public void testGetCompletionObserver() {
        Assert.assertThat(testRepository.getCompletionObserver(), is(notNullValue()));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddMake_DoesIncrementLiveDataValue() {
        // Arrange
        PerformanceRate rate = testRepository.getCurrentRate().getValue();
        Assert.assertThat(rate.getCount(), is(0));
        Assert.assertThat(rate.getTotal(), is(0));

        // Act
        testRepository.addMake();

        // Assert
        rate = testRepository.getCurrentRate().getValue();
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
            rate = testRepository.getCurrentRate().getValue();
            Assert.assertThat(rate.getCount(), is(i));
            Assert.assertThat(rate.getTotal(), is(i));
        }

        // Act
        testRepository.addMake(); // Now total == max

        // Assert
        rate = testRepository.getCurrentRate().getValue();
        Assert.assertThat(rate.getCount(), is(0));
        Assert.assertThat(rate.getTotal(), is(0));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddMiss_DoesIncrementLiveDataValue() {
        // Arrange
        PerformanceRate rate = testRepository.getCurrentRate().getValue();
        Assert.assertThat(rate.getCount(), is(0));
        Assert.assertThat(rate.getTotal(), is(0));

        // Act
        testRepository.addMiss();

        // Assert
        rate = testRepository.getCurrentRate().getValue();
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
        PerformanceRate rate;
        for (int i = 1; i < 10; i++) {
            testRepository.addMiss();
            rate = testRepository.getCurrentRate().getValue();
            Assert.assertThat(rate.getCount(), is(0));
            Assert.assertThat(rate.getTotal(), is(i));
        }

        // Act
        testRepository.addMiss(); // Now total == max

        // Assert
        rate = testRepository.getCurrentRate().getValue();
        Assert.assertThat(rate.getCount(), is(0));
        Assert.assertThat(rate.getTotal(), is(0));
    }

    @After
    public void tearDown() {
        testRepository = null;
    }
}