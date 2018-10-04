package com.armandgray.shared.application;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.hamcrest.CoreMatchers.is;

public class TAAPApplicationTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    TAAPAppComponent mockAppComponent;

    @Test
    public void testGetAppComponent() {
        TAAPApplication.appComponent = mockAppComponent;
        Assert.assertThat(TAAPApplication.getAppComponent(), is(TAAPApplication.appComponent));
    }
}