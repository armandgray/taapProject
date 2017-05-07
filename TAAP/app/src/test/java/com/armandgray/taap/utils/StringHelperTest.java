package com.armandgray.taap.utils;

import com.armandgray.taap.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.armandgray.taap.utils.StringHelper.getArrayAsString;
import static com.armandgray.taap.utils.StringHelper.getFormattedHeaderTextString;
import static com.armandgray.taap.utils.StringHelper.getStringAsArray;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class StringHelperTest {

    @Test
    public void canGetFormattedHeaderTextString() throws Exception {
        String[] headers = {"Evox Images", "LibphoneNumber"};
        String[] text = new String[2];
        text[0] = "Evox Images (C) 2014.\n\nhttp://www.evoximages.com";
        text[1] = "Evox Images (C) 2014.\n\nhttp://www.evoximages.com";
        assertNotNull(getFormattedHeaderTextString(headers, text));
    }

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
    public void canReturnNullWhenPassedNullOrEmptyString_GetStringAsArray() throws Exception {
        assertNull(getStringAsArray(null));
        assertNull(getStringAsArray(""));
    }

    @Test
    public void canConvertEmptyStringToNull_GetArrayAsString() throws Exception {
        @SuppressWarnings("all") String[] expectedArray = new String[3];
        String string = ",,";
        String[] stringAsArray = getStringAsArray(string);
        assertNotNull(stringAsArray);
        for (int i = 0; i < stringAsArray.length; i++) {
            assertEquals(expectedArray[i], stringAsArray[i]);
        }
    }
}
