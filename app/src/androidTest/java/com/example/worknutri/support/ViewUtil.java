package com.example.worknutri.support;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;

import com.example.worknutri.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

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

    @NonNull
    public static Chip getChipFromCategory(ViewGroup categoryLayout, int index) {
        ChipGroup chipGroup = categoryLayout.findViewById(R.id.filter_category_chipgroup);
        return (Chip) chipGroup.getChildAt(index);
    }


    public static ViewAction setSliderValue(float position) {
        return new GeneralSwipeAction(
                Swipe.FAST,
                view -> {
                    int[] coords = new int[2];
                    view.getLocationOnScreen(coords);

                    float x = coords[0] + view.getWidth() * position;
                    float y = coords[1] + view.getHeight() / 2f;

                    return new float[]{x, y};
                },
                view -> {
                    int[] coords = new int[2];
                    view.getLocationOnScreen(coords);

                    float x = coords[0] + view.getWidth() * position;
                    float y = coords[1] + view.getHeight() / 2f;

                    return new float[]{x, y};
                },
                Press.FINGER
        );
    }
}
