package com.example.worknutri.ui.formularios.editTextKeysListener;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.worknutri.R;

public class CalendarioKeyListener extends EditTextKeyListener{
    public CalendarioKeyListener(EditText editText) {
        super(editText);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        EditText editText = getEditText();
        editText.setTextColor(ContextCompat.getColor(editText.getContext(), R.color.black));
        String text = editText.getText().toString().replaceAll("/","");



        if(keyCode >= KeyEvent.KEYCODE_0 &&  keyCode <= KeyEvent.KEYCODE_9 ){

            if (text.length() >= 2 ){
                text = text.substring(0,2).concat("/").concat(text.substring(2));
                if ( text.length() >= 5){
                    text = text.substring(0,5).concat("/").concat(text.substring(5));
                }
            }
            editText.setText(text);
            editText.setSelection(editText.getText().length());


        }

        return false;
    }
}
