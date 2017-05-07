package com.armandgray.taap.utils;

public class StringsHelper {

    public static String getArrayAsString(String[] array) {
        StringBuilder builder = new StringBuilder();
        for (String s : array ) {
            builder.append(s);
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

}
