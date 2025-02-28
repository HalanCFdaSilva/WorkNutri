package com.example.worknutri.ui.formularios;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.worknutri.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public abstract class ValidaFormulario {


    public static boolean checaEditText(EditText editText, TextView asteristico, TextView textViewError) {
        String string = editText.getText().toString().strip();
        if (!string.isEmpty() && !string.isBlank()) {
            asteristico.setVisibility(View.INVISIBLE);
            editText.setHintTextColor(ContextCompat.getColor(editText.getContext(), R.color.black));
            return false;
        } else {
            textViewError.setText(R.string.error_blank);
            textViewError.setVisibility(View.VISIBLE);
            asteristico.setVisibility(View.VISIBLE);
            editText.setHintTextColor(ContextCompat.getColor(editText.getContext(), R.color.obrigatorio));
            return true;
        }
    }

    /**
     * Método que verifica se um editText foi preenchido no formato de telefones do brasil.
     *
     * @param editText             EditText a ser checado
     * @param textViewErrorMensage textView aonde vai aparecer a mensagem de erro da validação
     */
    public static boolean validaTelefone(EditText editText, TextView textViewErrorMensage) {
        String telefone = editText.getText().toString();
        telefone = telefone.replace(" ", "");
        telefone = telefone.replace("(", "");
        telefone = telefone.replace(")", "");
        telefone = telefone.replace("-", "");
        if (telefone.length() >= 8 && telefone.length() <= 11 || telefone.isEmpty()) {
            return true;
        } else {
            editText.setTextColor(ContextCompat.getColor(editText.getContext(), R.color.obrigatorio));
            textViewErrorMensage.setText(R.string.error_fone);
            textViewErrorMensage.setVisibility(View.VISIBLE);
            return false;
        }

    }

    /**
     * Método que verifica se um editText foi preenchido no formato correto para email.
     *
     * @param editText             EditText a ser checado
     * @param textViewErrorMensage textView aonde vai aparecer a mensagem de erro da validação
     */
    public static boolean validaEmail(EditText editText, TextView textViewErrorMensage) {
        String email = editText.getText().toString();
        String regex = "^[\\w\\-.]+@([\\w-]+\\.)+[\\w-]{2,}$";
        if (email.matches(regex) || email.isEmpty()) {
            editText.setTextColor(ContextCompat.getColor(editText.getContext(), R.color.black));
            return true;
        } else {
            editText.setTextColor(ContextCompat.getColor(editText.getContext(), R.color.obrigatorio));
            textViewErrorMensage.setText(R.string.error_email);
            textViewErrorMensage.setVisibility(View.VISIBLE);
            return false;
        }
    }

    /**
     * Método que verifica se um editText foi preenchido no formato correto para datas.
     *
     * @param editText             EditText a ser checado
     * @param textViewErrorMensage textView aonde vai aparecer a mensagem de erro da validação
     */
    public static boolean validaData(EditText editText, TextView textViewErrorMensage) {
        String data = editText.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            if (!data.isBlank()) {
                dateFormat.parse(data.trim());
            }
            return true;
        } catch (ParseException e) {
            editText.setTextColor(ContextCompat.getColor(editText.getContext(), R.color.obrigatorio));
            textViewErrorMensage.setText(R.string.error_data);
            textViewErrorMensage.setVisibility(View.VISIBLE);
            return false;
        }
    }

    /**
     * Método que verifica se um editText foi preenchido no formato correto para cpf.
     *
     * @param editText             EditText a ser checado
     * @param textViewErrorMensage textView aonde vai aparecer a mensagem de erro da validação
     */
    public static boolean validaCpf(EditText editText, TextView textViewErrorMensage) {
        String cpf = editText.getText().toString();
        String regex = "^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}$";
        if (cpf.matches(regex) || cpf.isEmpty()) {
            return true;
        } else {
            editText.setTextColor(ContextCompat.getColor(editText.getContext(), R.color.obrigatorio));
            textViewErrorMensage.setText(R.string.error_cpf);
            textViewErrorMensage.setVisibility(View.VISIBLE);
            return false;
        }
    }


}
