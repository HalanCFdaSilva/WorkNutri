package com.example.worknutri.ui.formularios;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.worknutri.R;

import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public abstract class FormValidator {


    public static boolean editTextIsEmpty(EditText editText, TextView asteristico, TextView textViewError) {
        String string = editText.getText().toString().strip();
        if (!string.isBlank()) {
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
    public static boolean validatePhoneNumber(EditText editText, TextView textViewErrorMensage) {
        String phoneNumber = editText.getText().toString();
        phoneNumber = phoneNumber.replace(" ", "");
        phoneNumber = phoneNumber.replace("(", "");
        phoneNumber = phoneNumber.replace(")", "");
        phoneNumber = phoneNumber.replace("-", "");
        if (phoneNumber.length() >= 10 && phoneNumber.length() <= 11 || phoneNumber.isEmpty()) {
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
    public static boolean validateEmailAdress(EditText editText, TextView textViewErrorMensage) {
        String email = editText.getText().toString();
        String pattern = "^[\\w\\-.]+@([\\w-]+\\.)+[\\w-]{2,}$";
        if (email.matches(pattern) || email.isEmpty()) {
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
     *  Caso não queira exibir a mensagem de erro na textView basta fornecer um null.
     * @param editText             EditText a ser checado
     * @param textViewErrorMensage textView aonde vai aparecer a mensagem de erro da validação
     */
    public static boolean validateDate(EditText editText, @Nullable TextView textViewErrorMensage) {
        String date = editText.getText().toString();
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT);

        try {
            LocalDate dateParsed = LocalDate.parse(date, formatter);
            if (dateParsed.isAfter(LocalDate.now())) {
                throw new Exception();
            }
            if (dateParsed.getYear() < 1900) {
                throw new Exception();
            }

            return true;

        } catch (Exception e) {
            if (textViewErrorMensage != null) {
                editText.setTextColor(ContextCompat.getColor(editText.getContext(), R.color.obrigatorio));
                textViewErrorMensage.setText(R.string.error_data);
                textViewErrorMensage.setVisibility(View.VISIBLE);
            }
            return false;
        }


    }

}
