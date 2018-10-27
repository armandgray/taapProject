package com.armandgray.shared.db;

import com.armandgray.shared.permission.DangerousPermission;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;

public class SharedPreferencesDaoImplTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private SharedPreferencesWrapper mockWrapper;

    @Mock
    private DatabaseManager.Component mockDatabaseComponent;

    private SharedPreferencesDaoImpl testDao;
    private Set<String> testPermissionsSet;

    @Before
    public void setUp() {
        DatabaseManager.State.setDatabaseComponent(mockDatabaseComponent);
        testPermissionsSet = new HashSet<>();

        testDao = new SharedPreferencesDaoImpl();
        testDao.sharedPreferencesWrapper = mockWrapper;

        Mockito.when(mockWrapper.getRequestedPermissions()).thenReturn(testPermissionsSet);
    }

    @Test
    public void testHasRequestedPermission() {
        testPermissionsSet.add(DangerousPermission.LOCATION.getKey());
        Assert.assertThat(testDao.hasRequestedPermission(DangerousPermission.LOCATION), is(true));
    }

    @Test
    public void testRegisterRequestedPermission() {
        DangerousPermission location = DangerousPermission.LOCATION;
        testDao.registerRequestedPermission(location);
        Assert.assertThat(testPermissionsSet.contains(location.getKey()), is(true));
        Mockito.verify(mockWrapper, Mockito.times(1)).setRequestedPermissions(testPermissionsSet);
    }

    @Test
    public void testToString() {
        Assert.assertThat(testDao.toString(),
                is("SharedPreferencesDaoImpl@" + Integer.toHexString(testDao.hashCode())));
    }

    @SuppressWarnings("ConstantConditions")
    @After
    public void tearDown() {
        testDao = null;
        testPermissionsSet = null;
        DatabaseManager.State.setDatabaseComponent(null);
    }
}
