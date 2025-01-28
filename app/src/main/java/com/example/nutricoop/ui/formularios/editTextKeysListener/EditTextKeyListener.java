package com.example.nutricoop.ui.formularios.editTextKeysListener;

import android.view.View;
import android.widget.EditText;

public abstract class EditTextKeyListener implements View.OnKeyListener {
    private final EditText editText;

    public EditTextKeyListener(EditText editText) {
        this.editText = editText;
    }

    public EditText getEditText() {
        return editText;
    }
}
