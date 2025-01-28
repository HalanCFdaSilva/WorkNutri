package com.example.nutricoop.ui;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nutricoop.R;

public abstract class InsertSelectViewSupport {

    public static void insertInTextView(TextView textView,String stringToInsert ){
        if (stringToInsert != null){
            textView.setText(stringToInsert);
        }else{
            textView.setText(" ");
        }
    }

    public static String getStringOfEditText(EditText editText){

        String string = editText.getText().toString();
        return !string.isEmpty() ? string : "";

    }

    public static int getIntOfEditText(EditText editText){

        String string = editText.getText().toString();
        return !string.isEmpty() ? Integer.parseInt(string) : 0;

    }

    public static void insertInEditText(EditText editText,String stringToInsert ){
        if (stringToInsert != null){
            editText.setText(stringToInsert);
        }
    }




}
