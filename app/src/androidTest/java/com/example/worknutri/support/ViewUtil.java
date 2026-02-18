package com.example.worknutri.support;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

import android.widget.EditText;

public class ViewUtil {

    public static void selectSpinnerItemByName(int spinnerId, String itemName) {
        onView(withId(spinnerId)).perform(scrollTo()).perform(click());
        onData(is(itemName)).perform(click());
    }


    public static void assertEditTextCorrectFormatText(EditText editText, String textToInsert, String expected) {
        editText.setText(textToInsert);
        editText.dispatchKeyEvent(new android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, android.view.KeyEvent.KEYCODE_0));
        editText.dispatchKeyEvent(new android.view.KeyEvent(android.view.KeyEvent.ACTION_UP, android.view.KeyEvent.KEYCODE_0));
        assertEquals(expected, editText.getText().toString());
    }
}
