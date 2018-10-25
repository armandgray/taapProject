package com.armandgray.shared.db;

import com.armandgray.shared.model.Drill;
import com.armandgray.shared.model.Performance;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import edu.emory.mathcs.backport.java.util.Collections;
import io.reactivex.Single;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class PerformanceDaoTest {

    private static final Performance TEST_PERFORMANCE = new Performance();

    private PerformanceDao.DaoLog testDaoLog;

    @Before
    public void setUp() {
        testDaoLog = new PerformanceDao.DaoLog();
        testDaoLog.setPerformance(TEST_PERFORMANCE);
        testDaoLog.setType(Drill.Type.SHOOTING_FUNDAMENTALS);
    }

    @Test
    public void testPerformanceDao() {
        PerformanceDao dao = new PerformanceDao() {

            @Override
            public Single<List<Long>> insert(Performance... arr) {
                return null;
            }

            @Override
            public Single<Integer> update(Performance... arr) {
                return null;
            }

            @Override
            public Single<Integer> delete(Performance... arr) {
                return null;
            }

            @Override
            public Single<List<Performance>> all() {
                return null;
            }

            @Override
            public Single<List<DaoLog>> logsBetween(long startTime, long endTime) {
                return null;
            }

            @Override
            public void deleteAll() {
            }
        };

        Assert.assertThat(dao, is(notNullValue()));
    }

    @Test
    public void testDaoLog_GetType() {
        Assert.assertThat(testDaoLog.getType(),
                containsInAnyOrder(Drill.Type.FUNDAMENTALS, Drill.Type.SHOOTING));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDaoLog_SetType() {
        List<Drill.Type> expected = Collections.singletonList(Drill.Type.SHOOTING);
        testDaoLog.setType(expected);
        Assert.assertThat(testDaoLog.getType(), is(expected));
    }

    @Test
    public void testDaoLog_GetPerformance() {
        Assert.assertThat(testDaoLog.getPerformance(), is(TEST_PERFORMANCE));
    }

    @Test
    public void testDaoLog_SetPerformance() {
        Performance expected = new Performance(TEST_PERFORMANCE);
        testDaoLog.setPerformance(expected);
        Assert.assertThat(testDaoLog.getPerformance(), is(expected));
    }

    @Ignore
    @Test
    public void testDaoLog_CompareTo_ReturnsLongCompare() {
        // TODO
    }

    @Test
    public void testDaoLog_CompareTo_ReturnsOneIfPassedNull() {
        Assert.assertThat(testDaoLog.compareTo(null), is(1));
    }

    @Ignore
    @Test
    public void testDaoWrapper() {

    }

    @After
    public void tearDown() {
        testDaoLog = null;
    }
}
