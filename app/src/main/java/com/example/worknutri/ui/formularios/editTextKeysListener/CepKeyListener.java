package com.example.worknutri.ui.formularios.editTextKeysListener;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.worknutri.R;

public class CepKeyListener implements View.OnKeyListener {

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        EditText editText = ((EditText) v);
        String text = editText.getText().toString();
        text = text.replaceAll("-", "");
        if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
            text = text.length() > 5 ? text.substring(0, 5).concat("-").
                    concat(text.substring(5)) : text;
            editText.setTextColor(ContextCompat.getColor(editText.getContext(), R.color.black));
            editText.setText(text);
            editText.setSelection(editText.getText().length());

        }

        return false;
    }
}
