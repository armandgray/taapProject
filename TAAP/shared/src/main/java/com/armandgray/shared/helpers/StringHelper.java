package com.armandgray.shared.helpers;

public class StringHelper {

    private StringHelper() {
        // Strict Helper Class
    }

    public static String toLogTag(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }

        String camelCaseRegex = "(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])";
        StringBuilder builder = new StringBuilder();
        for (String w : s.split(camelCaseRegex)) {
            builder.append(w.toUpperCase());
            builder.append('_');
        }

        return builder.deleteCharAt(builder.length() - 1).toString();
    }

    public static String toSpacedUpperCamel(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }

        s = s.toLowerCase().replaceAll("_", " ");

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            builder.append(i == 0 || s.charAt(i - 1) == ' ' ? Character.toUpperCase(ch) : ch);
        }

        return builder.toString();
    }
}
