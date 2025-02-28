package com.example.worknutri.ui;

public abstract class TextInViewSupport {

    public static String formatDouble(String string) {
        int i = string.indexOf(".");
        if (i >= 0 && string.length() > i + 3) {
            return string.substring(0, i + 3);
        }
        return string;
    }
}
