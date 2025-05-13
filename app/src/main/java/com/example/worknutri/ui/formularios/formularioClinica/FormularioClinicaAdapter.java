package com.example.worknutri.ui.formularios.formularioClinica;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.dao.clinica.DayOfWorkDao;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.ui.editTextKeysListener.CepKeyListener;
import com.example.worknutri.ui.editTextKeysListener.FoneKeyListener;
import com.example.worknutri.ui.formularios.FormularioAdapter;
import com.example.worknutri.ui.formularios.ValidaFormulario;
import com.example.worknutri.ui.popUp.hourDatePopUp.DayOfWorkUiService;

import java.util.List;

public class FormularioClinicaAdapter extends FormularioAdapter {


    private final InsertionClinicaFormulario insertion;
    private Clinica clinica;
    private DayOfWorkUiService dayOfWorkUiService;
    public FormularioClinicaAdapter(Context context) {
        super(context);
        dayOfWorkUiService = new DayOfWorkUiService(((Activity) context).findViewById(R.id.formulario_clinica_linear_layout),
                ((Activity) context).findViewById(R.id.formulario_clinica_horario_atendimento_layout));
        insertion = new InsertionClinicaFormulario(context);
        this.clinica = new Clinica();

    }

    public void insertClinicaInlayout(Clinica clinica,ViewGroup viewGroup) {
        this.clinica = clinica;
        insertion.InsertInFormulario(viewGroup, clinica);
        DayOfWorkDao dayOfWorkDao = getDataBase().dayOfWorkDao();
        dayOfWorkUiService.insertAllDayOfWork(dayOfWorkDao,clinica.getId());


    }




    public boolean validaFormulario(ViewGroup viewGroup, TextView textViewError) {
        if (validaPreenchimentoObrigatorio(viewGroup.findViewById(R.id.formulario_clinica_linear_layout),
                textViewError)) {
            if (ValidaFormulario.validaTelefone(viewGroup.findViewById(R.id.formulario_clinica_dados_pessoais_fone),
                    textViewError)) {
                return ValidaFormulario.validaEmail(viewGroup.findViewById(R.id.formulario_clinica_dados_pessoais_email),
                        textViewError);
            }
        }
        return false;
    }

    private boolean validaPreenchimentoObrigatorio(ViewGroup viewGroup, TextView textViewError) {
        boolean formularioPreenchido = !ValidaFormulario.checaEditText(viewGroup.findViewById(R.id.formulario_clinica_dados_gerais_name),
                viewGroup.findViewById(R.id.formulario_clinica_dados_gerais_name_obrigatorio), textViewError);
        if (ValidaFormulario.checaEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_rua),
                viewGroup.findViewById(R.id.formulario_clinica_endereco_rua_obrigatorio), textViewError)) {
            formularioPreenchido = false;

        }
        if (ValidaFormulario.checaEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_numero),
                viewGroup.findViewById(R.id.formulario_clinica_endereco_numero_obrigatorio), textViewError)) {
            formularioPreenchido = false;
        }
        if (ValidaFormulario.checaEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_bairro),
                viewGroup.findViewById(R.id.formulario_clinica_endereco_bairro_obrigatorio), textViewError)) {
            formularioPreenchido = false;
        }

        if (ValidaFormulario.checaEditText(viewGroup.findViewById(R.id.formulario_clinica_endereco_cidade),
                viewGroup.findViewById(R.id.formulario_clinica_endereco_cidade_obrigatorio), textViewError)) {
            formularioPreenchido = false;
        }
        return formularioPreenchido;
    }


    public void saveInDataBase(ViewGroup viewGroup) {
        List<DayOfWork> dayOfWorks = dayOfWorkUiService.getAllDayOfWork();
        if (clinica.getId() == 0) {
            insertion.InsertInClinica(viewGroup, clinica);
            getDataBase().clinicaDao().insertAll(clinica);
            int id = getDataBase().clinicaDao().findIdByName(clinica.getNome());
            saveDayofWork(dayOfWorks,id);
        } else {
            insertion.InsertInClinica(viewGroup, clinica);
            getDataBase().clinicaDao().update(clinica);
            saveDayofWork(dayOfWorks,clinica.getId());
        }
    }

    private void saveDayofWork(List<DayOfWork> dayOfWorkList, long clinicaId){
        for (DayOfWork dayOfWork : dayOfWorkList) {

            if (dayOfWork.getId()!= 0){
                getDataBase().dayOfWorkDao().update(dayOfWork);
            }else {
                dayOfWork.setIdClinica(clinicaId);
                getDataBase().dayOfWorkDao().insert(dayOfWork);
            }

        }
    }

    public void configureKeyListeners(ViewGroup viewGroup) {
        EditText viewById = viewGroup.findViewById(R.id.formulario_clinica_dados_pessoais_fone);
        viewById.setOnKeyListener(new FoneKeyListener());

        viewById = viewGroup.findViewById(R.id.formulario_clinica_endereco_cep);
        viewById.setOnKeyListener(new CepKeyListener());
    }

    public DayOfWorkUiService getDayOfWorkUiSave() {
        return dayOfWorkUiService;
    }
}
