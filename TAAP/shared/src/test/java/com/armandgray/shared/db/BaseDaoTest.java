package com.armandgray.shared.db;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import io.reactivex.Single;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class BaseDaoTest {

    @Test
    public void testBaseDao() {

        BaseDao dao = new BaseDao() {
            @Override
            public Single<List<Long>> insert(Object[] arr) {
                return null;
            }

            @Override
            public Single<Integer> update(Object[] arr) {
                return null;
            }

            @Override
            public Single<Integer> delete(Object[] arr) {
                return null;
            }
        };

        Assert.assertThat(dao, is(notNullValue()));
    }
}
