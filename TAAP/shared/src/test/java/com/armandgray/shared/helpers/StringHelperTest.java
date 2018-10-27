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

    @Test
    public void testToSpacedUpperCamel_UpperSnakeCase() {
        Assert.assertThat(StringHelper.toSpacedUpperCamel("TEST_STRING"), is("Test String"));
    }

    @Test
    public void testToSpacedUpperCamel_UpperCase() {
        Assert.assertThat(StringHelper.toSpacedUpperCamel("TESTString"), is("Teststring"));
    }

    @Test
    public void testToSpacedUpperCamel_LowerCase() {
        Assert.assertThat(StringHelper.toSpacedUpperCamel("teststring"), is("Teststring"));
    }
}
