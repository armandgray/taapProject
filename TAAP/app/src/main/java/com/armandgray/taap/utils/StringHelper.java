package com.armandgray.taap.utils;

public class StringHelper {

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

    static String[] getStringAsArray(String string) {
        if (string == null || string.length() == 0) { return null; }
        return string.split(",");
    }
}
