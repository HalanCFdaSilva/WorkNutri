package com.example.worknutri.ui.editTextKeysListener;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.worknutri.R;

public class FoneKeyListener implements View.OnKeyListener {




    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        EditText editText = ((EditText) v);
        String text = editText.getText().toString();
        text = text.replaceAll("\\(", "");
        text = text.replaceAll("\\)", "");
        text = text.replaceAll("-", "");
        text = text.replaceAll(" ", "");
        text = text.length() > 10 ? text.substring(0, 11) : text;
        if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
            String DDD = (text.length() >= 2) ? "(".concat(text.substring(0, 2)).concat(")") : text;
            String numero = (text.length() > 2) ? " ".concat(text.substring(2)) : "";
            if (text.length() > 6 && text.length() < 11) {
                if (text.length() == 8 && text.charAt(2) != '9') {
                    DDD = "";
                    numero = text.substring(0, 4).concat("-").concat(text.substring(4));
                } else {
                    numero = " ".concat(text.substring(2, 6)).concat("-").concat(text.substring(6));
                }
            }
            if (text.length() >= 11) {
                numero = " ".concat(text.substring(2, 7)).concat("-").concat(text.substring(7));
            }
            editText.setTextColor(ContextCompat.getColor(editText.getContext(), R.color.black));
            editText.setText(DDD.concat(numero));
            editText.setSelection(editText.getText().length());

        }

        return false;
    }

}
