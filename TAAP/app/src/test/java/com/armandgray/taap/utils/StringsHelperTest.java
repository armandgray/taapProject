package com.armandgray.taap.utils;

import org.junit.Test;

import static com.armandgray.taap.utils.StringsHelper.getArrayAsString;
import static com.armandgray.taap.utils.StringsHelper.getStringAsArray;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StringsHelperTest {

    @Test
    public void canGetArrayAsString() throws Exception {
        String[] array = {"a", "ab", "aab", "abc"};
        String arrayAsString = getArrayAsString(array);
        assertNotNull(arrayAsString);
        assertEquals("a,ab,aab,abc", arrayAsString);
    }

    @Test
    public void canReturnNullWhenPassedNull_GetArrayAsString() throws Exception {
        assertNull(getArrayAsString(null));
    }

    @Test
    public void canConvertNullToEmptyString_GetArrayAsString() throws Exception {
        String[] array = new String[3];
        assertNotNull(getArrayAsString(array));
        assertEquals(",,", getArrayAsString(array));
    }

    @Test
    public void canGetStringAsArray() throws Exception {
        String[] expectedArray = {"a", "ab", "aab", "abc"};
        String string = "a,ab,aab,abc";
        String[] stringAsArray = getStringAsArray(string);
        assertNotNull(stringAsArray);
        assertThat(expectedArray, is(stringAsArray));
    }

    @Test
    public void canReturnNullWhenPassedNull_GetStringAsArray() throws Exception {
        assertNull(getStringAsArray(null));
    }

    @Test
    public void canConvertEmptyStringToNull_GetArrayAsString() throws Exception {
        String[] expectedArray = new String[3];
        String string = ",,";
        String[] stringAsArray = getStringAsArray(string);
        assertNotNull(stringAsArray);
        for (int i = 0; i < stringAsArray.length; i++) {
            assertEquals(expectedArray[i], stringAsArray[i]);
        }
    }

}