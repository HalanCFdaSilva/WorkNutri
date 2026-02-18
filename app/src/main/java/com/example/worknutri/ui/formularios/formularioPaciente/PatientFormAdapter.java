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
import com.example.worknutri.ui.formularios.FormValidator;
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

public class PatientFormAdapter extends FormularioAdapter {

    private Patologia pathology;
    private Antropometria anthropometry;
    private Paciente patient;
    private final List<Clinica> clinicsInOrder;
    private final List<PathologyField> pathologyCategories = new ArrayList<>(Arrays.asList(PathologyField.values()));

    public PatientFormAdapter(Context context) {
        super(context);
        patient = new Paciente();
        anthropometry = new Antropometria();
        pathology = new Patologia();
        clinicsInOrder = getDataBase().clinicaDao().getAllInOrder();

    }

    public void insertInFormulario(Intent intent, ViewGroup viewRootFormulario) {
        if (intent.hasExtra(ExtrasActivities.PACIENTE_EXTRA.getKey())) {
            patient = (Paciente) intent.getSerializableExtra(ExtrasActivities.PACIENTE_EXTRA.getKey());
            anthropometry = getDataBase().antropometriaDao().getByPacienteId(patient.getId());
            pathology = getDataBase().patologiaDao().loadAllByIdPaciente(patient.getId()).get(0);

            new PacienteInsertionPacienteForm(viewRootFormulario.findViewById(R.id.formulario_paciente_dados_pessoais_layout))
                    .insertPacienteInViewGroup(patient);

            PacienteInsertionPacienteForm.insertObservationsInViewGroup(
                    viewRootFormulario.findViewById(R.id.formulario_paciente_observation), patient);

            PacienteInsertionPacienteForm.SelectClinica(viewRootFormulario.findViewById(R.id.formulario_paciente_dados_pessoais_clinica_spinner),
                    patient.getClinicaId(), clinicsInOrder);

            new AntropometryInsertionPacienteForm(viewRootFormulario.findViewById(R.id.formulario_paciente_antropometria_layout))
                    .insertAntropometria(anthropometry);

            new PathologyInsertionPacienteForm(viewRootFormulario.findViewById(R.id.formulario_paciente_patologia_layout_content), pathology)
                    .InsertPatologiaValuesInViewGroup(getContext(), pathologyCategories);


        }
    }


    public void savePaciente(ViewGroup viewGroup) {

        new PacienteInsertionPacienteForm(viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_layout))
                .insertViewGroupInPaciente(patient);
        PacienteInsertionPacienteForm.insertObservationsInPaciente(patient, viewGroup.findViewById(R.id.formulario_paciente_observation));
        PacienteInsertionPacienteForm.insertClinicaInPaciente(viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_clinica_spinner), patient, clinicsInOrder);

        new AntropometryInsertionPacienteForm(viewGroup.findViewById(R.id.formulario_paciente_antropometria_layout))
                .insertViewGroupInAntropometria(anthropometry, patient);

        new PathologyInsertionPacienteForm(viewGroup.findViewById(R.id.formulario_paciente_patologia_layout_content), pathology)
                .insertViewGroupInPatologia();

        PacienteDao pacienteDao = getDataBase().pacienteDao();
        if (patient.getId() == 0) pacienteDao.insertAll(patient);
        else pacienteDao.update(patient);

        AntropometriaDao antropometriaDao = getDataBase().antropometriaDao();
        if (anthropometry.getIdPaciente() == 0) antropometriaDao.insertAll(anthropometry);
        else antropometriaDao.update(anthropometry);

        PatologiaDao patologiaDao = getDataBase().patologiaDao();
        if (pathology.getIdPaciente() == 0) patologiaDao.insert(pathology);
        else patologiaDao.update(pathology);
    }

    public void setClinicas(Spinner spinner) {

        spinner.setAdapter(new ClinicaArrayAdapter(getContext(), clinicsInOrder));
    }

    public boolean validaFormulario(ViewGroup viewRoot, TextView textViewError) {
        boolean validado = validaObrigatorios(viewRoot, textViewError);
        if (validado && !FormValidator.validateDate(viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_nascimento),
                textViewError)) {
            validado = false;
        }
        if (validado && !FormValidator.validateEmailAdress(viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_email),
                textViewError)) {
            validado = false;
        }
        if (validado && !FormValidator.validatePhoneNumber(viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_fone),
                textViewError)) {
            validado = false;
        }

        return validado;
    }

    private boolean validaObrigatorios(ViewGroup viewRoot, TextView textViewError) {
        boolean validado = !FormValidator.editTextIsEmpty(viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_name),
                viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_name_obrigatorio), textViewError);
        if (FormValidator.editTextIsEmpty(viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_nascimento),
                viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_idade_obrigatorio), textViewError)) {
            validado = false;
        }
        if (FormValidator.editTextIsEmpty(viewRoot.findViewById(R.id.formulario_paciente_antropometria_altura),
                viewRoot.findViewById(R.id.formulario_paciente_antropometria_altura_obrigatorio), textViewError)) {
            validado = false;
        }
        if (FormValidator.editTextIsEmpty(viewRoot.findViewById(R.id.formulario_paciente_antropometria_peso_atual),
                viewRoot.findViewById(R.id.formulario_paciente_antropometria_peso_atual_obrigatorio), textViewError)) {
            validado = false;
        }

        if (FormValidator.editTextIsEmpty(viewRoot.findViewById(R.id.formulario_paciente_antropometria_peso_ideal),
                viewRoot.findViewById(R.id.formulario_paciente_antropometria_peso_ideal_obrigatorio), textViewError)) {
            validado = false;
        }


        return validado;
    }

    public void getCalculosAntropometricos( ViewGroup viewGroup) {
        if (validaCalculosAntropometricos(viewGroup)) {
            viewGroup.findViewById(R.id.formulario_paciente_antropometria_calculos_error).setVisibility(View.GONE);

            new PacienteInsertionPacienteForm(viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_layout))
                    .insertViewGroupInPaciente(patient);

            new AntropometryInsertionPacienteForm(viewGroup.findViewById(R.id.formulario_paciente_antropometria_layout))
                    .insertViewGroupInAntropometria(anthropometry, patient);

            AntropometriaDetaillPopUp antropometriaDetaillPopUp = new PopUpFactoryImpl(getContext()).generateAntropometriaPopUp();
            antropometriaDetaillPopUp.generateSmall(anthropometry);
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
            pathologyAddPopUp.configurePopUp(viewWereAdd, pathology);
            pathologyAddPopUp.getPopUpWindow().showAtLocation(viewRoot, Gravity.CENTER, -1, -1);
        }else
            Toast.makeText(getContext(),"Não há patologias disponíveis", Toast.LENGTH_SHORT).show();

    }
}
