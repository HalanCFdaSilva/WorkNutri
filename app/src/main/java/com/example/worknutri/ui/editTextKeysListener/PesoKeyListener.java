package com.example.worknutri.ui.editTextKeysListener;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.worknutri.calcular.CalculadorAntropometrico;
import com.example.worknutri.calcular.MeasureConverter;
import com.example.worknutri.calcular.MeasureTypes;
import com.example.worknutri.ui.TextInViewSupport;

public class PesoKeyListener implements View.OnKeyListener {

    private Spinner spinnerAltura;
    private EditText editTextPesoIdeal;
    private Spinner spinnerPesoIdeal;


    public void setSpinnerAltura(Spinner spinnerAltura) {

        this.spinnerAltura = spinnerAltura;
    }

    public void setPesoIdeal(Spinner spinnerPesoIdeal, EditText editTextPesoIdeal) {
        this.editTextPesoIdeal = editTextPesoIdeal;
        this.spinnerPesoIdeal = spinnerPesoIdeal;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        String stringAltura = ((EditText) v).getText().toString();
        if (!stringAltura.isBlank()) {
            double editTextText = Double.parseDouble(stringAltura);
            int positionSpinner = spinnerAltura.getSelectedItemPosition();
            double altura = MeasureConverter.convertToGramOrMeters(MeasureTypes.fromValue(positionSpinner), editTextText);

            double pesoIdeal = CalculadorAntropometrico.generatePesoIdeal(altura);
            editTextPesoIdeal.setText(TextInViewSupport.formatDouble(String.valueOf(pesoIdeal)));
            spinnerPesoIdeal.setSelection(0);
        }
        return false;
    }


}
