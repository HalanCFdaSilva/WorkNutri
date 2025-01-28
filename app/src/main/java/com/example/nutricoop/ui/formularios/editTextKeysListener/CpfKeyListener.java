package com.example.nutricoop.ui.formularios.editTextKeysListener;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.nutricoop.R;

public class CpfKeyListener extends EditTextKeyListener{
    public CpfKeyListener(EditText editText) {
        super(editText);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        EditText editText = getEditText();
        String text = editText.getText().toString();


        if(keyCode >= KeyEvent.KEYCODE_0 &&  keyCode <= KeyEvent.KEYCODE_9 ){
            switch (text.length()){
                case 3:
                case 7: {
                    editText.setText(text.concat("."));
                    break;
                }
                case 11: {
                    editText.setText(text.concat("-"));
                    break;
                }
                default: editText.setText(text);
            }
            getEditText().setTextColor(ContextCompat.getColor(getEditText().getContext(), R.color.black));
            getEditText().setSelection(getEditText().getText().length());


        }

        return false;
    }
}
