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
}
