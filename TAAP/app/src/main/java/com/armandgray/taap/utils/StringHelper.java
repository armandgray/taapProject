package com.armandgray.taap.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

public class StringHelper {

    public static String getFormattedHeaderTextString(String[] headers, String[] text) {
        String content = "";
        for (int i = 0; i < headers.length; i++) {
            content += "\n\n";
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(headers[i] + "\n\n" + text[i]);
            StyleSpan boldStyleSpan = new StyleSpan(android.graphics.Typeface.BOLD);
            stringBuilder.setSpan(boldStyleSpan, 0, headers[0].length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            content += stringBuilder.toString();
        }
        return content;
    }
}
