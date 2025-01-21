package com.example.nutricoop.ui.editTextKeysListener;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class CepKeyListener extends EditTextKeyListener{
    public CepKeyListener(EditText editText) {
        super(editText);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        String text = getEditText().getText().toString();
        text = text.replaceAll("-","");
        if(keyCode >= KeyEvent.KEYCODE_0 &&  keyCode <= KeyEvent.KEYCODE_9 ){
            text = text.length() > 5 ? text.substring(0,5).concat("-").
                    concat(text.substring(5)) : text;

            getEditText().setText(text);
            getEditText().setSelection(getEditText().getText().length());

        }

        return false;
    }
}
