package com.armandgray.taap.utils;

import org.junit.Test;

import static com.armandgray.taap.utils.StringsHelper.getArrayAsString;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class StringsHelperTest {

    @Test
    public void canGetArrayAsString() throws Exception {
        String[] array = {"a", "ab", "aab", "abc"};
        String arrayAsString = getArrayAsString(array);
        assertNotNull(arrayAsString);
        assertEquals("a,ab,aab,abc", arrayAsString);
    }

}