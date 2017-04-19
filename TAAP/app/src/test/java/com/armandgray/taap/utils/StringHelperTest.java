package com.armandgray.taap.utils;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

public class StringHelperTest {

    @Test
    public void canGetFormattedHeaderTextString() throws Exception {
        String[] headers = {"Evox Images", "LibphoneNumber", "Lottie for iOS"};
        String[] text = new String[3];
        text[0] = "Vehicle photos licensed from Evox Images (C) 2014.\n\nhttp://www.evoximages.com";
        assertNotNull(getFormattedHeaderTextString(headers, text));
    }
}
