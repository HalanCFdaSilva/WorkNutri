package com.example.nutricoop.ui.formularios.formularioClinica;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nutricoop.R;

import com.example.nutricoop.sqlLite.domain.clinica.DayOfWork;
import com.example.nutricoop.sqlLite.domain.clinica.Clinica;
import com.example.nutricoop.ui.formularios.FormularioAdapter;
import com.example.nutricoop.ui.formularios.ValidaFormulario;
import com.example.nutricoop.ui.formularios.editTextKeysListener.CepKeyListener;
import com.example.nutricoop.ui.formularios.editTextKeysListener.FoneKeyListener;
import com.example.nutricoop.ui.popUp.hourDatePopUp.DatePickerFragment;
import com.example.nutricoop.ui.popUp.hourDatePopUp.HourDateFragment;


import java.util.ArrayList;
import java.util.List;

public class FormularioClinicaAdapter extends FormularioAdapter {


    private final InsertionClinicaFormulario insertion;
    private  Clinica clinica;
    private  List<DayOfWork> daysOfWork;


    public FormularioClinicaAdapter(Context context) {
        super(context);
        insertion = new InsertionClinicaFormulario(context);
        this.clinica = new Clinica();
        daysOfWork = new ArrayList<>();
    }

    public void insertClinicaInlayout(Clinica clinica,ViewGroup viewGroup,LayoutInflater inflater){
        this.clinica = clinica;
        insertion.InsertInFormulario(viewGroup,clinica);
        daysOfWork = getDataBase().dayOfWorkDao().getDaysforClinicaId(clinica.getId());
        insertion.insertDaysOfWork(viewGroup.findViewById(R.id.formulario_clinica_horario_atendimento_layout),
                daysOfWork,inflater);
    }



    public boolean validaFormulario(ViewGroup viewGroup, TextView textViewError){
        if (  validaPreenchimentoObrigatorio(viewGroup.findViewById(R.id.formulario_clinica_linear_layout),
                textViewError)){
            if (ValidaFormulario.validaTelefone(viewGroup.findViewById(R.id.formulario_clinica_dados_pessoais_fone),
                    textViewError)){
                return ValidaFormulario.validaEmail(viewGroup.findViewById(R.id.formulario_clinica_dados_pessoais_email),
                        textViewError);
            }
        }
        return false;
    }

    private boolean validaPreenchimentoObrigatorio(ViewGroup viewGroup, TextView textViewError){
        boolean formularioPreenchido = !ValidaFormulario.checaEditText(viewGroup.findViewById(R.id.formulario_clinica_dados_gerais_name),
                viewGroup.findViewById(R.id.formulario_clinica_dados_gerais_name_obrigatorio), textViewError);
        if (ValidaFormulario.checaEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_rua),
                viewGroup.findViewById(R.id.formulario_clinica_endereco_rua_obrigatorio),textViewError)){
            formularioPreenchido = false;

        }
        if (ValidaFormulario.checaEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_numero),
                viewGroup.findViewById(R.id.formulario_clinica_endereco_numero_obrigatorio),textViewError)){
            formularioPreenchido = false;
        }
        if (ValidaFormulario.checaEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_bairro),
                viewGroup.findViewById(R.id.formulario_clinica_endereco_bairro_obrigatorio),textViewError)){
            formularioPreenchido = false;
        }

        if (ValidaFormulario.checaEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_cidade),
                viewGroup.findViewById(R.id.formulario_clinica_endereco_cidade_obrigatorio),textViewError)){
            formularioPreenchido = false;
        }
        return formularioPreenchido;
    }



    public void saveInDataBase(ViewGroup viewGroup){

        if (clinica.getId() == 0){
            insertion.InsertInClinica(viewGroup, clinica);
            getDataBase().clinicaDao().insertAll(clinica);
            int id = getDataBase().clinicaDao().findIdByName(clinica.getNome());
            for (DayOfWork dayOfWork : daysOfWork) {
                dayOfWork.setIdClinica(id);
                getDataBase().dayOfWorkDao().insert(dayOfWork);
            }
        }else {
            insertion.InsertInClinica(viewGroup, clinica);
            getDataBase().clinicaDao().update(clinica);

            for (DayOfWork dayOfWork : daysOfWork) {

                getDataBase().dayOfWorkDao().updateDayOfWork(dayOfWork.getDayOfWeek(),dayOfWork.getHoraInicio(),
                        dayOfWork.getHoraFim(), dayOfWork.getId());
            }
        }
    }

    public void configureKeyListeners(ViewGroup viewGroup) {
        EditText viewById = viewGroup.findViewById(R.id.formulario_clinica_dados_pessoais_fone);
        viewById.setOnKeyListener(new FoneKeyListener(viewById));

        viewById = viewGroup.findViewById(R.id.formulario_clinica_endereco_cep);
        viewById.setOnKeyListener(new CepKeyListener(viewById));
    }

    public void newDay(LayoutInflater inflater,ViewGroup viewGroupOfActivity) {
        ViewGroup view =(ViewGroup) inflater.inflate(R.layout.date_picker_pop_up, null);
        DatePickerFragment datePickerFragment = new DatePickerFragment(view,daysOfWork);
        HourDateFragment hourDateFragment = new HourDateFragment(inflater);
        datePickerFragment.layoutGenerate(hourDateFragment,viewGroupOfActivity.findViewById(R.id.formulario_clinica_horario_atendimento_layout));
        datePickerFragment.getPopUpWindow().showAtLocation(viewGroupOfActivity, Gravity.CENTER, -1, -1);

    }

}
