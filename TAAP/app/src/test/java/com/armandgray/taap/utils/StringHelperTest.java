package com.armandgray.taap.utils;

import android.text.SpannableStringBuilder;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

public class StringHelperTest {

    public static String getFormattedHeaderTextString(String[] headers, String[] text) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(headers[0]);
        return stringBuilder.toString();
    }

    @Test
    public void canGetFormattedHeaderTextString() throws Exception {
        String[] headers = {"Evox Images", "LibphoneNumber"};
        String[] text = new String[2];
        text[0] = "Evox Images (C) 2014.\n\nhttp://www.evoximages.com";
        text[1] = "Evox Images (C) 2014.\n\nhttp://www.evoximages.com";
        assertNotNull(getFormattedHeaderTextString(headers, text));
    }
}
