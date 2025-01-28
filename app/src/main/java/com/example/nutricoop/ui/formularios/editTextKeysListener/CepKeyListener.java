package com.example.nutricoop.ui.formularios.editTextKeysListener;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.nutricoop.R;

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
            getEditText().setTextColor(ContextCompat.getColor(getEditText().getContext(), R.color.black));
            getEditText().setText(text);
            getEditText().setSelection(getEditText().getText().length());

        }

        return false;
    }
}
