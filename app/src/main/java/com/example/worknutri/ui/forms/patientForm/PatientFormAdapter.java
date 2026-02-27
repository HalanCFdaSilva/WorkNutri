package com.example.worknutri.ui.forms.patientForm;

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
import com.example.worknutri.ui.forms.FormularioAdapter;
import com.example.worknutri.ui.forms.FormValidator;
import com.example.worknutri.ui.forms.formInserters.AnthropometryFormInserter;
import com.example.worknutri.ui.forms.formInserters.PatientFormInserter;
import com.example.worknutri.ui.forms.formInserters.PathologyFormInserter;
import com.example.worknutri.ui.popUp.anthropometry.ActivityLevelDetailPopUp;
import com.example.worknutri.ui.popUp.anthropometry.AntropometriaDetaillPopUp;
import com.example.worknutri.ui.popUp.factory.PopUpFactoryImpl;
import com.example.worknutri.ui.popUp.pathology.PathologyField;
import com.example.worknutri.ui.popUp.pathology.addPopUp.AddPathologyPopUp;

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

            PatientFormInserter.create(viewRootFormulario.findViewById(R.id.patient_form_activity_personal_data_layout))
                    .insertEntityInViewGroup(patient);

            PatientFormInserter.insertObservationsInViewGroup(
                    viewRootFormulario.findViewById(R.id.patient_form_activity_observation), patient);

            PatientFormInserter.SelectClinica(viewRootFormulario.findViewById(R.id.patient_form_activity_personal_data_clinic_spinner),
                    patient.getClinicaId(), clinicsInOrder);

            AnthropometryFormInserter.create(viewRootFormulario.findViewById(R.id.patient_form_activity_anthropometry_layout))
                    .insertEntityInViewGroup(anthropometry);

            PathologyFormInserter.create(viewRootFormulario.findViewById(R.id.patient_form_activity_pathological_layout_content), pathologyCategories)
                    .insertEntityInViewGroup(pathology);


        }
    }


    public void savePatientInDb(ViewGroup viewGroup) {

        PatientFormInserter.create(viewGroup.findViewById(R.id.patient_form_activity_personal_data_layout))
                .insertViewGroupInEntity(patient);
        PatientFormInserter.insertObservationsInPaciente(patient, viewGroup.findViewById(R.id.patient_form_activity_observation));
        PatientFormInserter.insertClinicaInPaciente(viewGroup.findViewById(R.id.patient_form_activity_personal_data_clinic_spinner), patient, clinicsInOrder);

        AnthropometryFormInserter.create(viewGroup.findViewById(R.id.patient_form_activity_anthropometry_layout))
                .insertViewGroupInEntity(anthropometry, patient);

        PathologyFormInserter.create(viewGroup.findViewById(R.id.patient_form_activity_pathological_layout_content), null)
                .insertViewGroupInEntity(pathology);

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

    public void setClinics(Spinner spinner) {

        spinner.setAdapter(new ClinicaArrayAdapter(getContext(), clinicsInOrder));
    }

    public boolean validateForm(ViewGroup viewRoot, TextView textViewError) {
        boolean validado = validateMandatoryFields(viewRoot, textViewError);
        if (validado && !FormValidator.validateDate(viewRoot.findViewById(R.id.patient_form_activity_personal_data_birthday),
                textViewError)) {
            validado = false;
        }
        if (validado && !FormValidator.validateEmailAdress(viewRoot.findViewById(R.id.patient_form_activity_personal_data_email),
                textViewError)) {
            validado = false;
        }
        if (validado && !FormValidator.validatePhoneNumber(viewRoot.findViewById(R.id.patient_form_activity_personal_data_phone),
                textViewError)) {
            validado = false;
        }

        return validado;
    }

    private boolean validateMandatoryFields(ViewGroup viewRoot, TextView textViewError) {
        boolean validado = !FormValidator.editTextIsEmpty(viewRoot.findViewById(R.id.patient_form_activity_personal_data_name),
                viewRoot.findViewById(R.id.patient_form_activity_personal_data_name_mandatory), textViewError);
        if (FormValidator.editTextIsEmpty(viewRoot.findViewById(R.id.patient_form_activity_personal_data_birthday),
                viewRoot.findViewById(R.id.patient_form_activity_personal_data_birthday_mandatory), textViewError)) {
            validado = false;
        }
        if (FormValidator.editTextIsEmpty(viewRoot.findViewById(R.id.patient_form_activity_anthropometry_height),
                viewRoot.findViewById(R.id.patient_form_activity_anthropometry_height_mandatory), textViewError)) {
            validado = false;
        }
        if (FormValidator.editTextIsEmpty(viewRoot.findViewById(R.id.patient_form_activity_anthropometry_weight_current),
                viewRoot.findViewById(R.id.patient_form_activity_anthropometry_weight_current_mandatory), textViewError)) {
            validado = false;
        }

        if (FormValidator.editTextIsEmpty(viewRoot.findViewById(R.id.patient_form_activity_anthropometry_weight_ideal),
                viewRoot.findViewById(R.id.patient_form_activity_anthropometry_weight_ideal_mandatory), textViewError)) {
            validado = false;
        }


        return validado;
    }

    public void generateAnthropometricDataPopUp(ViewGroup viewGroup) {
        if (validateAnthropometricData(viewGroup)) {
            viewGroup.findViewById(R.id.patient_form_activity_anthropometry_calculations_error).setVisibility(View.GONE);

            PatientFormInserter.create(viewGroup.findViewById(R.id.patient_form_activity_personal_data_layout))
                    .insertViewGroupInEntity(patient);

            AnthropometryFormInserter.create(viewGroup.findViewById(R.id.patient_form_activity_anthropometry_layout))
                    .insertViewGroupInEntity(anthropometry, patient);

            AntropometriaDetaillPopUp antropometriaDetaillPopUp = new PopUpFactoryImpl(getContext()).generateAntropometriaPopUp();
            antropometriaDetaillPopUp.generateSmall(anthropometry);
            antropometriaDetaillPopUp.getPopUpWindow().
                    showAtLocation(viewGroup, Gravity.CENTER, -1, -1);
        } else {
            viewGroup.findViewById(R.id.patient_form_activity_anthropometry_calculations_error).setVisibility(View.VISIBLE);
        }
    }

    private boolean validateAnthropometricData(ViewGroup viewGroup) {
        EditText editText = viewGroup.findViewById(R.id.patient_form_activity_personal_data_birthday);
        if (!editText.getText().toString().isBlank()) {
            editText = viewGroup.findViewById(R.id.patient_form_activity_anthropometry_height);
            if (!editText.getText().toString().isBlank()) {
                editText = viewGroup.findViewById(R.id.patient_form_activity_anthropometry_weight_current);
                if (!editText.getText().toString().isBlank()) {
                    editText = viewGroup.findViewById(R.id.patient_form_activity_anthropometry_weight_ideal);
                    return !editText.getText().toString().isBlank();
                }
            }
        }
        return false;
    }

    public void OpenActivityLevelPopUpOnClickIn(ImageView button, ViewGroup viewGroup) {
        button.setOnClickListener(onClick -> {
            ActivityLevelDetailPopUp popUp = new PopUpFactoryImpl(getContext()).generateActivityLevelDetailPopUp();
            popUp.configureLayout();
            popUp.getPopUpWindow().showAtLocation(viewGroup, Gravity.CENTER, -1, -1);

        });
    }
    public void generateAddPatologyPopup(ViewGroup viewRoot, ViewGroup viewWereAdd) {

        if (!pathologyCategories.isEmpty()){
            PopUpFactoryImpl popUpFactory = new PopUpFactoryImpl(getContext());
            AddPathologyPopUp addPathologyPopUp = popUpFactory.generateAddPathologyPopUp( pathologyCategories);
            addPathologyPopUp.configurePopUp(viewWereAdd, pathology);
            addPathologyPopUp.getPopUpWindow().showAtLocation(viewRoot, Gravity.CENTER, -1, -1);
        }else
            Toast.makeText(getContext(),"Não há patologias disponíveis", Toast.LENGTH_SHORT).show();

    }
}
