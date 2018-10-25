package com.armandgray.shared.db;

import com.armandgray.shared.model.Drill;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import io.reactivex.Single;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class DrillDaoTest {

    @Test
    public void testDrillDao() {
        DrillDao dao = new DrillDao() {

            @Override
            public Single<List<Long>> insert(Drill... arr) {
                return null;
            }

            @Override
            public Single<Integer> update(Drill... arr) {
                return null;
            }

            @Override
            public Single<Integer> delete(Drill... arr) {
                return null;
            }

            @Override
            public Single<List<Drill>> all() {
                return null;
            }

            @Override
            public Single<Drill> drill(int id) {
                return null;
            }
        };

        Assert.assertThat(dao, is(notNullValue()));
    }

    @Ignore
    @Test
    public void testDaoWrapper() {

    }
}
