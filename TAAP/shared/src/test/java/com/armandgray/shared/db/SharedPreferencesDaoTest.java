package com.armandgray.shared.db;

import com.armandgray.shared.permission.DangerousPermission;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class SharedPreferencesDaoTest {

    @Test
    public void testSharedPreferencesDao_DeclaredMethods() {
        SharedPreferencesDao testDao = new SharedPreferencesDao() {
            @Override
            public boolean hasRequestedPermission(DangerousPermission permission) {
                return false;
            }

            @Override
            public void registerRequestedPermission(DangerousPermission permission) {

            }
        };

        Assert.assertThat(testDao, is(notNullValue()));
    }

}
