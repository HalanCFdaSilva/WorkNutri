package com.example.worknutri.ui.formularios.formularioPaciente;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.dao.paciente.AntropometriaDao;
import com.example.worknutri.sqlLite.dao.paciente.PacienteDao;
import com.example.worknutri.sqlLite.dao.paciente.PatologiaDao;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.formularios.FormularioAdapter;
import com.example.worknutri.ui.formularios.ValidaFormulario;
import com.example.worknutri.ui.popUp.NivelAtividadeDescritpionPopUp;
import com.example.worknutri.ui.popUp.factory.PopUpFactoryImpl;
import com.example.worknutri.ui.popUp.formsPopUp.PathologyCategory;
import com.example.worknutri.ui.popUp.formsPopUp.PopUpPathologyAdd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormularioPacienteAdapter extends FormularioAdapter {

    private Patologia patologia;
    private Antropometria antropometria;
    private Paciente paciente;
    private final List<Clinica> clinicasInOrder;
    private final List<PathologyCategory> pathologyCategories = new ArrayList<>(Arrays.asList(PathologyCategory.values()));

    public FormularioPacienteAdapter(Context context) {
        super(context);
        paciente = new Paciente();
        antropometria = new Antropometria();
        patologia = new Patologia();
        clinicasInOrder = getDataBase().clinicaDao().getAllInOrder();

    }

    public void insertInFormulario(Intent intent, ViewGroup viewRootFormulario) {
        if (intent.hasExtra(ExtrasActivities.PACIENTE_EXTRA.getKey())) {
            paciente = (Paciente) intent.getSerializableExtra(ExtrasActivities.PACIENTE_EXTRA.getKey());
            antropometria = getDataBase().antropometriaDao().getByPacienteId(paciente.getId());
            patologia = getDataBase().patologiaDao().loadAllByIdPaciente(paciente.getId()).get(0);
            InsertionPacienteFormulario insertion = new InsertionPacienteFormulario();
            insertion.insertPacienteInViewGroup(viewRootFormulario.findViewById(R.id.formulario_paciente_dados_pessoais_layout), paciente);
            insertion.insertAntropometria(viewRootFormulario.findViewById(R.id.formulario_paciente_antropometria_layout), antropometria);
            insertion.InsertPatologia(viewRootFormulario.findViewById(R.id.formulario_paciente_patologia_layout), patologia);
            insertion.SelectClinica(viewRootFormulario.findViewById(R.id.formulario_paciente_dados_pessoais_clinica_spinner),
                    paciente.getClinicaId(), getDataBase().clinicaDao().getAllInOrder());
        }
    }


    public void savePaciente(ViewGroup viewGroup) {
        InsertionPacienteFormulario generator = new InsertionPacienteFormulario();
        generator.insertViewGroupInPaciente(viewGroup, paciente);
        generator.insertClinicaInPaciente(viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_clinica_spinner), paciente, clinicasInOrder);
        generator.insertViewGroupInAntropometria(viewGroup, antropometria, paciente);
        generator.insertViewGroupInPatologia(viewGroup, patologia);

        PacienteDao pacienteDao = getDataBase().pacienteDao();
        if (paciente.getId() == 0) pacienteDao.insertAll(paciente);
        else pacienteDao.update(paciente);

        AntropometriaDao antropometriaDao = getDataBase().antropometriaDao();
        if (antropometria.getIdPaciente() == 0) antropometriaDao.insertAll(antropometria);
        else antropometriaDao.update(antropometria);

        PatologiaDao patologiaDao = getDataBase().patologiaDao();
        if (patologia.getIdPaciente() == 0) patologiaDao.insertAll(patologia);
        else patologiaDao.update(patologia);
    }

    public void setClinicas(Spinner spinner) {

        spinner.setAdapter(new ClinicaArrayAdapter(getContext(), clinicasInOrder));
    }

    public boolean validaFormulario(ViewGroup viewRoot, TextView textViewError) {
        boolean validado = validaObrigatorios(viewRoot, textViewError);
        if (validado && !ValidaFormulario.validaData(viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_nascimento),
                textViewError)) {
            validado = false;
        }
        if (validado && !ValidaFormulario.validaEmail(viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_email),
                textViewError)) {
            validado = false;
        }
        if (validado && !ValidaFormulario.validaTelefone(viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_fone),
                textViewError)) {
            validado = false;
        }

        return validado;
    }

    private boolean validaObrigatorios(ViewGroup viewRoot, TextView textViewError) {
        boolean validado = !ValidaFormulario.checaEditText(viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_name),
                viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_name_obrigatorio), textViewError);
        if (ValidaFormulario.checaEditText(viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_nascimento),
                viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_idade_obrigatorio), textViewError)) {
            validado = false;
        }
        if (ValidaFormulario.checaEditText(viewRoot.findViewById(R.id.formulario_paciente_antropometria_altura),
                viewRoot.findViewById(R.id.formulario_paciente_antropometria_altura_obrigatorio), textViewError)) {
            validado = false;
        }
        if (ValidaFormulario.checaEditText(viewRoot.findViewById(R.id.formulario_paciente_antropometria_peso_atual),
                viewRoot.findViewById(R.id.formulario_paciente_antropometria_peso_atual_obrigatorio), textViewError)) {
            validado = false;
        }

        if (ValidaFormulario.checaEditText(viewRoot.findViewById(R.id.formulario_paciente_antropometria_peso_ideal),
                viewRoot.findViewById(R.id.formulario_paciente_antropometria_peso_ideal_obrigatorio), textViewError)) {
            validado = false;
        }


        return validado;
    }

    public void getCalculosAntropometricos( ViewGroup viewGroup) {
        if (validaCalculosAntropometricos(viewGroup)) {
            viewGroup.findViewById(R.id.formulario_paciente_antropometria_calculos_error).setVisibility(View.GONE);
            InsertionPacienteFormulario generator = new InsertionPacienteFormulario();
            generator.insertViewGroupInPaciente(viewGroup, paciente);
            generator.insertViewGroupInAntropometria(viewGroup, antropometria, paciente);
            new PopUpFactoryImpl(getContext()).generateSmallAntropometriaPopUp(antropometria).getPopUpWindow().
                    showAtLocation(viewGroup, Gravity.CENTER, -1, -1);
        } else {
            viewGroup.findViewById(R.id.formulario_paciente_antropometria_calculos_error).setVisibility(View.VISIBLE);
        }
    }

    private boolean validaCalculosAntropometricos(ViewGroup viewGroup) {
        EditText editText = viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_nascimento);
        if (!editText.getText().toString().isBlank()) {
            editText = viewGroup.findViewById(R.id.formulario_paciente_antropometria_altura);
            if (!editText.getText().toString().isBlank()) {
                editText = viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_atual);
                if (!editText.getText().toString().isBlank()) {
                    editText = viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_ideal);
                    return !editText.getText().toString().isBlank();
                }
            }
        }
        return false;
    }

    public void OpenNivelAtividadePopUpOnClick(ImageView button, LayoutInflater inflater, ViewGroup viewGroup) {
        button.setOnClickListener(onClick -> {
            NivelAtividadeDescritpionPopUp popUp = new NivelAtividadeDescritpionPopUp(inflater);
            popUp.getPopUpWindow().showAtLocation(viewGroup, Gravity.CENTER, -1, -1);

        });
    }
    public void generatePatologiaView(ViewGroup viewRoot, ViewGroup viewWereAdd) {

        if (!pathologyCategories.isEmpty()){
            PopUpFactoryImpl popUpFactory = new PopUpFactoryImpl(getContext());
            PopUpPathologyAdd popUpPathologyAdd = popUpFactory.generatePopUpPatologiaAdd( pathologyCategories);
            popUpPathologyAdd.configurePopUp(viewWereAdd);
            popUpPathologyAdd.getPopUpWindow().showAtLocation(viewRoot, Gravity.CENTER, -1, -1);
        }else
            Toast.makeText(getContext(),"Não há patologias disponíveis", Toast.LENGTH_SHORT).show();

    }
}
