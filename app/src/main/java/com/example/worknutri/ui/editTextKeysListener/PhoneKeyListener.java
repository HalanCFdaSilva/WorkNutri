package com.example.worknutri.ui.editTextKeysListener;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.worknutri.R;

public class PhoneKeyListener implements View.OnKeyListener {


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
            String DDD = text;
            String numberPhoneModiFied = "";
            if (text.length() >= 2){
                DDD = "(".concat(text.substring(0, 2).concat(") "));
                String numberPhone = text.substring(2);
                numberPhoneModiFied = numberPhone;
                if (numberPhone.length() >= 5 && numberPhone.length() <= 8) {
                    numberPhoneModiFied = numberPhone.substring(0, 4).concat("-").concat(numberPhone.substring(4));
                }
                if (numberPhone.length() >= 9) {
                    if (text.charAt(2) != '9') {

                        numberPhoneModiFied = numberPhone.substring(0, 4).concat("-").concat(numberPhone.substring(4,8));
                    } else {
                        numberPhoneModiFied = numberPhone.substring(0, 5).concat("-").concat(numberPhone.substring(5,9));
                    }
                }
            }


            editText.setTextColor(ContextCompat.getColor(editText.getContext(), R.color.black));
            editText.setText(DDD.concat(numberPhoneModiFied));
            editText.setSelection(editText.getText().length());

        }

        return false;
    }

}
