package com.armandgray.shared.helpers;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class StringHelperTest {

    @Test
    public void testToLogTag_CamelCase() {
        Assert.assertThat(StringHelper.toLogTag("testString"), is("TEST_STRING"));
    }

    @Test
    public void testToLogTag_UpperCase() {
        Assert.assertThat(StringHelper.toLogTag("TESTString"), is("TEST_STRING"));
    }

    @Test
    public void testToLogTag_LowerCase() {
        Assert.assertThat(StringHelper.toLogTag("teststring"), is("TESTSTRING"));
    }
}
