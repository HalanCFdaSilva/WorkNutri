package com.example.worknutri.util;

public abstract class  StringsUtil {

    public static int convertHourStringInInt(String string){
        return Integer.parseInt(string.substring(0, 2));
    }
}
