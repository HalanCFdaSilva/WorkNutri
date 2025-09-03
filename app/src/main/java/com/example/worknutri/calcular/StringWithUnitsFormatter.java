package com.example.worknutri.calcular;

import com.example.worknutri.util.StringsUtil;

public abstract class StringWithUnitsFormatter {


    public static String withKcal(String string) {
        return StringsUtil.formatDouble(string).concat(" Kcal");
    }

    public static String withCm(String string) {
        return StringsUtil.formatDouble(string).concat(" cm");
    }

    public static String withKg( String string) {
        return StringsUtil.formatDouble(string).concat(" kg");
    }

    public static String withMeters( String string) {
        return StringsUtil.formatDouble(string).concat(" m");
    }

    public static String withMl( String string) {
        return StringsUtil.formatDouble(string).concat(" ml");
    }

    public static String withKcalDia( String string) {
        return StringsUtil.formatDouble(string).concat(" Kcal/Dia");
    }
}