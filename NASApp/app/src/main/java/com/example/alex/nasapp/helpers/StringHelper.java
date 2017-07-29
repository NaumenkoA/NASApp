package com.example.alex.nasapp.helpers;

public class StringHelper {
    public static String changeNumberOfCharsAfterDot(String string, int charsAfterDot) {
        if (charsAfterDot == 0) {
            return string.substring(0, string.indexOf(".") - 1);
        } else {
            return string.substring(0, string.indexOf(".") + charsAfterDot + 1);
        }
    }
}
