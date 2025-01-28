package com.example.nutricoop.ui.formularios.formularioClinica;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.nutricoop.R;
import com.example.nutricoop.sqlLite.clinica.domain.Clinica;
import com.example.nutricoop.sqlLite.clinica.domain.DayOfWork;
import com.example.nutricoop.ui.InsertSelectViewSupport;
import com.example.nutricoop.ui.popUp.hourDatePopUp.HourDateFragment;

import java.util.List;


public class InsertionClinicaFormulario {

    private Context context;

    public InsertionClinicaFormulario(Context context) {
        this.context = context;
    }

    public void InsertInClinica(ViewGroup viewGroup, Clinica clinica){
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
    }

    public void InsertInFormulario(ViewGroup viewGroup, Clinica clinica){
        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_clinica_dados_gerais_name),clinica.getNome());

        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_clinica_dados_pessoais_email),clinica.getEmail());

        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_clinica_dados_pessoais_fone),clinica.getTelefone1());

        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_cep),clinica.getCodigoPostal());

        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_rua),clinica.getRua());

        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_numero),String.valueOf(clinica.getNumero()));

        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_complemento),clinica.getComplemento());

        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_bairro),clinica.getBairro());

        InsertSelectViewSupport.insertInEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_cidade),clinica.getCidade());

        if (clinica.getEstado() != null){
            Spinner spinner = viewGroup.findViewById(R.id.formulario_clinica_endereco_estado_spinner);

            String[] stringArray = context.getResources().getStringArray(R.array.estado_brasil);
            int i = 0;
            for (String estado : stringArray){
                if (estado == clinica.getEstado()) break;
                i++;
            }

            spinner.setSelection(i);
        }
    }


    /**Método que insere os dados de dias de trabalho de uma clinica especifica em uma activity.
     * @param viewGroup  View onde serão inseridos os dados dos DayOfWork.
     * @param dayOfWorkList List de DayOfWork cuja classe serve para quardar asinformações de um dia especifico
     *                     de trabalho para uma clinica
     * @param layoutInflater Classe que será usada para inflar o layout que será inserido no viewGroup
     * @see DayOfWork*/
    public void insertDaysOfWork(ViewGroup viewGroup, List<DayOfWork> dayOfWorkList, LayoutInflater layoutInflater){
        HourDateFragment dateFragment = new HourDateFragment(layoutInflater);
        for (DayOfWork dayOfWork : dayOfWorkList){
            dateFragment.setDayOfweek(dayOfWork.getDayOfWeek());
            dateFragment.setHourBegin(dayOfWork.getHoraInicio());
            dateFragment.setHourEnd(dayOfWork.getHoraFim());
            dateFragment.addLayout(viewGroup);
        }
    }

}
