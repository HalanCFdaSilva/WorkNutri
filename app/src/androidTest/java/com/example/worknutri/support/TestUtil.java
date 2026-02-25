package com.example.worknutri.support;

import android.content.Context;
import android.view.ContextThemeWrapper;

import androidx.core.content.ContextCompat;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.worknutri.R;

public abstract class TestUtil {

    public static Context getThemedContext() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        context = new ContextThemeWrapper(context, R.style.Theme_NutriCoop);
        return context;
    }

    public static int colorFromRes(int resId) {
        return ContextCompat.getColor(getThemedContext(), resId);
    }

    public static String formatDoubleToString(double value) {
        String stringValue = String.valueOf( value).replace(',', '.');
        if (stringValue.endsWith(".00")) {
            stringValue = stringValue.substring(0, stringValue.length() - 1);
        }
        return stringValue;
    }

    public static String getStringFromRes(int resId) {
        return getThemedContext().getString(resId);
    }

    public static String getStringFromDoubleWith2dot(Double doubleValue) {
        String stringValue = String.valueOf(doubleValue);
        int indexOfDot = stringValue.indexOf('.');
        if (indexOfDot + 3 <= stringValue.length()) {
            return stringValue.substring(0, indexOfDot + 3);
        }
        return stringValue;

    }
}
