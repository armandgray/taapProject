package com.armandgray.taap.utils;

public class StringsHelper {

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
        String[] array = string.split(",");
        return array;
    }

}
