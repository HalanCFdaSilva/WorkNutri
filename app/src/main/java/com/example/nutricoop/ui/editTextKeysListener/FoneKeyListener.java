package com.example.nutricoop.ui.editTextKeysListener;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class FoneKeyListener extends EditTextKeyListener {



    public FoneKeyListener(EditText editText) {
        super(editText);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        String text = getEditText().getText().toString();
        text = text.replaceAll("\\(","");
        text = text.replaceAll("\\)","");
        text = text.replaceAll("-","");
        text = text.replaceAll(" ","");
        text = text.length() > 10 ? text.substring(0,11) : text;
        if(keyCode >= KeyEvent.KEYCODE_0 &&  keyCode <= KeyEvent.KEYCODE_9 ){
            String DDD =(text.length() >= 2) ? "(".concat(text.substring(0,2)).concat(")") : text;
            String numero = (text.length() > 2) ? " ".concat(text.substring(2)) : "";
            if (text.length() > 6 && text.length() < 11) {
                numero = " ".concat(text.substring(2,6)).concat("-").concat(text.substring(6));
            } if (text.length() >= 11 ) {
                numero = " ".concat(text.substring(2,7)).concat("-").concat(text.substring(7));
            }
            getEditText().setText(DDD.concat(numero));
            getEditText().setSelection(getEditText().getText().length());

        }

        return false;
    }

}
