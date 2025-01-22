package com.example.nutricoop.ui;

import android.annotation.SuppressLint;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import com.example.nutricoop.R;

public abstract class ValidaFormulario {

    public static boolean formularioClinica(ViewGroup viewGroup,TextView textViewError){
        boolean formularioPreenchido = true;
        if (checaEditText(viewGroup.findViewById(R.id.formulario_clinica_dados_gerais_name),
                viewGroup.findViewById(R.id.formulario_clinica_dados_gerais_name_obrigatorio),textViewError)){
            formularioPreenchido = false;
        }
        if (checaEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_rua),
                viewGroup.findViewById(R.id.formulario_clinica_endereco_rua_obrigatorio),textViewError)){
            formularioPreenchido = false;

        }
        if (checaEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_numero),
                viewGroup.findViewById(R.id.formulario_clinica_endereco_numero_obrigatorio),textViewError)){
            formularioPreenchido = false;
        }
        if (checaEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_bairro),
                viewGroup.findViewById(R.id.formulario_clinica_endereco_bairro_obrigatorio),textViewError)){
            formularioPreenchido = false;
        }

        if (checaEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_cidade),
                viewGroup.findViewById(R.id.formulario_clinica_endereco_cidade_obrigatorio),textViewError)){
            formularioPreenchido = false;
        }
        return formularioPreenchido;
    }


    private static boolean checaEditText(EditText editText , TextView asteristico,TextView textViewError){
        String string = editText.getText().toString().strip();
        if (!string.isEmpty() && !string.isBlank()){
            asteristico.setVisibility(View.INVISIBLE);
            editText.setHintTextColor(ContextCompat.getColor(editText.getContext(),R.color.black));
            return false;
        }else{
            textViewError.setVisibility(View.VISIBLE);
            asteristico.setVisibility(View.VISIBLE);
            editText.setHintTextColor(ContextCompat.getColor(editText.getContext(),R.color.obrigatorio));
            return true;
        }
    }


}
