package com.armandgray.shared.db;

import com.armandgray.shared.model.Performance;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import io.reactivex.Single;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class PerformanceDaoTest {

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
        };

        Assert.assertThat(dao, is(notNullValue()));
    }
}
