package com.armandgray.shared.permission;

import android.Manifest;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class DangerousPermissionTest {

    @Test
    public void testDangerousPermission_Instances() {
        DangerousPermission[] values = DangerousPermission.values();
        Assert.assertThat(values.length, is(2));
        Assert.assertThat(Arrays.asList(values), containsInAnyOrder(
                DangerousPermission.ERROR,
                DangerousPermission.LOCATION));
    }

    @Test
    public void testGetTitle() {
        Assert.assertThat(DangerousPermission.LOCATION.getTitle(), is("Location Access"));
    }

    @Test
    public void testGetRationale() {
        Assert.assertThat(DangerousPermission.LOCATION.getRationale(),
                is("Location access used for Auto Tracking & Gyms"));
    }

    @Test
    public void testGetKey() {
        Assert.assertThat(DangerousPermission.LOCATION.getKey(),
                is(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    @Test
    public void testGetCode() {
        Assert.assertThat(DangerousPermission.LOCATION.getCode(), is(1));
    }

    @Test
    public void testGetPermission() {
        Assert.assertThat(DangerousPermission.getPermission(1),
                is(DangerousPermission.LOCATION));
    }
}