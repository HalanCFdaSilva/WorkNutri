package com.example.worknutri.util;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public abstract class EditAndTextViewsUtil {

    public static void insertInTextViewOrTextViewGone(TextView textView, String stringToInsert) {
        if (!stringToInsert.isBlank()) {
            textView.setText(stringToInsert);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    public static void insertInTextView(TextView textView, String stringToInsert) {
        String correctString = stringToInsert != null ? stringToInsert : " ";
       textView.setText(correctString);
    }

    public static String getStringOfEditText(EditText editText) {

        String string = editText.getText().toString();
        return !string.isEmpty() ? string : "";

    }


    public static void insertInEditText(EditText editText, String stringToInsert) {
        if (stringToInsert != null) {
            editText.setText(stringToInsert);
        }
    }

    public static String getStringOfSpinner(Spinner spinner, String[] arrayOfStringgSpinner) {
        int i = spinner.getSelectedItemPosition();
        return arrayOfStringgSpinner[i];
    }


}
