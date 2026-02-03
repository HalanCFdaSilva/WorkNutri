package com.example.worknutri.support;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public abstract class TextViewAssertions {



    public static ViewAssertion matchesTextColor(final int expectedColor) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (noViewFoundException != null) {
                    throw noViewFoundException;
                }
                if (!(view instanceof TextView)) {
                    fail("View não é TextView");
                }
                int actual = ((TextView) view).getCurrentTextColor();
                assertEquals("Cor do texto diferente", expectedColor, actual);
            }
        };
    }

    public static ViewAssertion matchesHintColor(final int expectedColor) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (noViewFoundException != null) {
                    throw noViewFoundException;
                }
                if (!(view instanceof TextView)) {
                    fail("View não é TextView");
                }
                int actual = ((TextView) view).getCurrentHintTextColor();
                assertEquals("Cor do texto diferente", expectedColor, actual);
            }
        };
    }

    public static ViewAssertion matchesText(final String expectedText) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (noViewFoundException != null) {
                    throw noViewFoundException;
                }
                if (!(view instanceof TextView)) {
                    fail("View não é TextView");
                }
                String actual = ((TextView) view).getText().toString();
                assertEquals("Texto diferente", expectedText, actual);
            }
        };
    }



    public static ViewAssertion matchesVisibility(final int expectedVisibility) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (noViewFoundException != null) {
                    throw noViewFoundException;
                }
                assertEquals("Cor do texto diferente", expectedVisibility, view.getVisibility());
            }
        };
    }
}
