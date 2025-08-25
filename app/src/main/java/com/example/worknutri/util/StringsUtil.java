package com.example.worknutri.util;

public abstract class  StringsUtil {

    public static int convertHourStringInInt(String string){
        return Integer.parseInt(string.substring(0, 2));
    }

    public static String formatDouble(String string) {
        int i = string.indexOf(".");
        if (i >= 0 && string.length() > i + 3) {
            return string.substring(0, i + 3);
        }
        return string;
    }
}
