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
}
