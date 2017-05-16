package com.armandgray.taap.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

public class StringHelper {

    public static SpannableStringBuilder getFormattedHeaderTextString(String[] headers, String[] text) {
        SpannableStringBuilder contentsStringBuilder = new SpannableStringBuilder();
        for (int i = 0; i < headers.length; i++) {
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(headers[i] + "\n\n" + text[i]);
            StyleSpan boldStyleSpan = new StyleSpan(android.graphics.Typeface.BOLD);
            stringBuilder.setSpan(boldStyleSpan, 0, headers[i].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            contentsStringBuilder.append(stringBuilder);
            contentsStringBuilder.append("\n\n");
        }
        return contentsStringBuilder;
    }

    public static String getArrayAsString(String[] array) {
        if (array == null || array.length == 0) { return null; }
        StringBuilder builder = new StringBuilder();
        for (String s : array ) {
            if (s != null) { builder.append(s); }
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public static String[] getStringAsArray(String string) {
        if (string == null || string.length() == 0) { return null; }
        return string.split(",");
    }
}
