package com.armandgray.shared.helpers;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class StringHelpersTest {

    @Test
    public void testToLogTag_CamelCase() {
        Assert.assertThat(StringHelpers.toLogTag("testString"), is("TEST_STRING"));
    }

    @Test
    public void testToLogTag_UpperCase() {
        Assert.assertThat(StringHelpers.toLogTag("TESTString"), is("TEST_STRING"));
    }

    @Test
    public void testToLogTag_LowerCase() {
        Assert.assertThat(StringHelpers.toLogTag("teststring"), is("TESTSTRING"));
    }
}
