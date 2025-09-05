package com.example.worknutri.ui.formularios.formularioPaciente;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
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
import com.example.worknutri.ui.formularios.formularioPaciente.insertionsOfPacienteFormulario.AntropometryInsertionPacienteForm;
import com.example.worknutri.ui.formularios.formularioPaciente.insertionsOfPacienteFormulario.PacienteInsertionPacienteForm;
import com.example.worknutri.ui.formularios.formularioPaciente.insertionsOfPacienteFormulario.PathologyInsertionPacienteForm;
import com.example.worknutri.ui.popUp.anthropometry.ActivityLevelDetailPopUp;
import com.example.worknutri.ui.popUp.anthropometry.AntropometriaDetaillPopUp;
import com.example.worknutri.ui.popUp.factory.PopUpFactoryImpl;
import com.example.worknutri.ui.popUp.pathology.PathologyField;
import com.example.worknutri.ui.popUp.pathology.addPopUp.PathologyAddPopUp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormularioPacienteAdapter extends FormularioAdapter {

    private Patologia patologia;
    private Antropometria antropometria;
    private Paciente paciente;
    private final List<Clinica> clinicasInOrder;
    private final List<PathologyField> pathologyCategories = new ArrayList<>(Arrays.asList(PathologyField.values()));

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

            new PacienteInsertionPacienteForm(viewRootFormulario.findViewById(R.id.formulario_paciente_dados_pessoais_layout))
                    .insertPacienteInViewGroup( paciente);
            PacienteInsertionPacienteForm.SelectClinica(viewRootFormulario.findViewById(R.id.formulario_paciente_dados_pessoais_clinica_spinner),
                    paciente.getClinicaId(), clinicasInOrder);

            new AntropometryInsertionPacienteForm(viewRootFormulario.findViewById(R.id.formulario_paciente_antropometria_layout))
                    .insertAntropometria( antropometria);

            new PathologyInsertionPacienteForm(viewRootFormulario.findViewById(R.id.formulario_paciente_patologia_layout_content),patologia)
                    .InsertPatologiaValuesInViewGroup(getContext(), pathologyCategories);


        }
    }


    public void savePaciente(ViewGroup viewGroup) {

        new PacienteInsertionPacienteForm(viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_layout))
                .insertViewGroupInPaciente( paciente);
        PacienteInsertionPacienteForm.insertObservationsInPaciente(paciente, viewGroup.findViewById(R.id.formulario_paciente_observation));
        PacienteInsertionPacienteForm.insertClinicaInPaciente(viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_clinica_spinner), paciente, clinicasInOrder);

        new AntropometryInsertionPacienteForm(viewGroup.findViewById(R.id.formulario_paciente_antropometria_layout))
                .insertViewGroupInAntropometria(antropometria, paciente);

        new PathologyInsertionPacienteForm(viewGroup.findViewById(R.id.formulario_paciente_patologia_layout_content),patologia)
                .insertViewGroupInPatologia();

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

            new PacienteInsertionPacienteForm(viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_layout))
                    .insertViewGroupInPaciente( paciente);

            new AntropometryInsertionPacienteForm(viewGroup.findViewById(R.id.formulario_paciente_antropometria_layout))
                    .insertViewGroupInAntropometria(antropometria, paciente);

            AntropometriaDetaillPopUp antropometriaDetaillPopUp = new PopUpFactoryImpl(getContext()).generateAntropometriaPopUp();
            antropometriaDetaillPopUp.generateSmall(antropometria);
            antropometriaDetaillPopUp.getPopUpWindow().
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

    public void OpenNivelAtividadePopUpOnClick(ImageView button, ViewGroup viewGroup) {
        button.setOnClickListener(onClick -> {
            ActivityLevelDetailPopUp popUp = new PopUpFactoryImpl(getContext()).generateActivityLevelDetailPopUp();
            popUp.configureLayout();
            popUp.getPopUpWindow().showAtLocation(viewGroup, Gravity.CENTER, -1, -1);

        });
    }
    public void generatePatologiaView(ViewGroup viewRoot, ViewGroup viewWereAdd) {

        if (!pathologyCategories.isEmpty()){
            PopUpFactoryImpl popUpFactory = new PopUpFactoryImpl(getContext());
            PathologyAddPopUp pathologyAddPopUp = popUpFactory.generatePopUpPatologiaAdd( pathologyCategories);
            pathologyAddPopUp.configurePopUp(viewWereAdd,patologia);
            pathologyAddPopUp.getPopUpWindow().showAtLocation(viewRoot, Gravity.CENTER, -1, -1);
        }else
            Toast.makeText(getContext(),"Não há patologias disponíveis", Toast.LENGTH_SHORT).show();

    }
}
