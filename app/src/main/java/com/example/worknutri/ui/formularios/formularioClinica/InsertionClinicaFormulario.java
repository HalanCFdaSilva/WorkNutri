package com.example.worknutri.ui.formularios.formularioClinica;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.InsertSelectViewSupport;


public class InsertionClinicaFormulario {

    private final Context context;

    public InsertionClinicaFormulario(Context context) {
        this.context = context;
    }

    public void InsertInClinica(ViewGroup viewGroup, Clinica clinica) {
        String stringOfEditText = InsertSelectViewSupport.getStringOfEditText(viewGroup.findViewById(R.id.formulario_clinica_dados_gerais_name));
        clinica.setNome(stringOfEditText);

        stringOfEditText = InsertSelectViewSupport.getStringOfEditText(viewGroup.findViewById(R.id.formulario_clinica_dados_pessoais_email));
        clinica.setEmail(stringOfEditText);

        stringOfEditText = InsertSelectViewSupport.getStringOfEditText(viewGroup.findViewById(R.id.formulario_clinica_dados_pessoais_fone));
        clinica.setTelefone1(stringOfEditText);

        stringOfEditText = InsertSelectViewSupport.getStringOfEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_rua));
        clinica.setRua(stringOfEditText);
        stringOfEditText = InsertSelectViewSupport.getStringOfEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_numero));
        clinica.setNumero(Integer.parseInt(stringOfEditText));
        stringOfEditText = InsertSelectViewSupport.getStringOfEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_complemento));
        clinica.setComplemento(stringOfEditText);
        stringOfEditText = InsertSelectViewSupport.getStringOfEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_bairro));
        clinica.setBairro(stringOfEditText);
        stringOfEditText = InsertSelectViewSupport.getStringOfEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_cidade));
        clinica.setCidade(stringOfEditText);

        String stringOfSpinner = InsertSelectViewSupport.getStringOfSpinner(viewGroup.findViewById(R.id.formulario_clinica_endereco_estado_spinner),
                context.getResources().getStringArray(R.array.estado_brasil));
        clinica.setEstado(stringOfSpinner);


    }

    public void InsertInFormulario(ViewGroup viewGroup, Clinica clinica) {
        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_clinica_dados_gerais_name), clinica.getNome());

        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_clinica_dados_pessoais_email), clinica.getEmail());

        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_clinica_dados_pessoais_fone), clinica.getTelefone1());

        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_cep), clinica.getCodigoPostal());

        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_rua), clinica.getRua());

        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_numero), String.valueOf(clinica.getNumero()));

        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_complemento), clinica.getComplemento());

        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_bairro), clinica.getBairro());

        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_cidade), clinica.getCidade());

        if (!clinica.getEstado().isBlank()) {
            Spinner spinner = viewGroup.findViewById(R.id.formulario_clinica_endereco_estado_spinner);

            String[] stringArray = context.getResources().getStringArray(R.array.estado_brasil);
            int i = 0;
            for (String estado : stringArray) {
                if (estado.equals(clinica.getEstado())) {
                    break;
                }
                i++;
            }
            spinner.setSelection(i);
        }
    }




}
