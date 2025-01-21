package com.example.nutricoop.ui.editTextKeysListener;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class CalendarioKeyListener extends EditTextKeyListener{
    public CalendarioKeyListener(EditText editText) {
        super(editText);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        EditText editText = getEditText();
        String text = editText.getText().toString();


        if(keyCode >= KeyEvent.KEYCODE_0 &&  keyCode <= KeyEvent.KEYCODE_9 ){
            switch (text.length()){
                case 2:
                case 5: editText.setText(text.concat("/"));
                default: getEditText().setSelection(getEditText().getText().length());
            }

        }

        return false;
    }
}
