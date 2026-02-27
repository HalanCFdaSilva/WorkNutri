package com.example.worknutri.ui.forms.formInserters;

import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.util.ViewsUtil;

import java.util.List;

public class PatientFormInserter extends FormInserter<Paciente> {
    private PatientFormInserter(ViewGroup viewGroup) {
        super(viewGroup);
        viewGroupIdExpected = R.id.patient_form_activity;
    }

    public static PatientFormInserter create(ViewGroup viewGroup) {
        if (checkViewGroupIsCorrectly(viewGroup))
            return new PatientFormInserter(viewGroup);
        throw new IllegalArgumentException("O viewGroup deve ser do tipo esperado. Esperado: " + viewGroupIdExpected + ", Recebido: " + viewGroup.getId());

    }

    public void insertViewGroupInEntity(Paciente paciente) {


        paciente.setNomePaciente(ViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.patient_form_activity_personal_data_name)));

        int i = ((Spinner)viewGroup.findViewById(R.id.patient_form_activity_personal_data_gender_spinner)).getSelectedItemPosition();
        paciente.setGenero(i == 0 ? 'M' : 'F');

        paciente.setNascimento(ViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.patient_form_activity_personal_data_birthday)));

        paciente.setEmail(ViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.patient_form_activity_personal_data_email)));

        paciente.setTelefone(ViewsUtil.getStringOfEditText(
                viewGroup.findViewById(R.id.patient_form_activity_personal_data_phone)));

    }
    public static void insertObservationsInPaciente(Paciente paciente, EditText editText) {
        paciente.setObservacoes(ViewsUtil.getStringOfEditText(
               editText));
    }

    public static void insertClinicaInPaciente(Spinner spinner, Paciente paciente, List<Clinica> clinicasInOrder) {
        int i = spinner.getSelectedItemPosition();
        Clinica clinica = clinicasInOrder.get(i);
        paciente.setClinicaId(clinica.getId());
    }

    /**
     * Método que preenche o viewGroup de Paciente do FormularioPaciente com os dados que recebe da classe Paciente
     * com a ajuda da classe InsertSelectViewSupport.
     *
     * @see Paciente
     * @see ViewsUtil
     */
    public void insertEntityInViewGroup(Paciente paciente) {
        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.patient_form_activity_personal_data_name)
                , paciente.getNomePaciente());
        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.patient_form_activity_personal_data_birthday)
                , paciente.getNascimento());
        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.patient_form_activity_personal_data_email)
                , paciente.getEmail());
        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.patient_form_activity_personal_data_phone)
                , paciente.getTelefone());

        if (paciente.getGenero() == 'M') {
            ((Spinner) viewGroup.findViewById(R.id.patient_form_activity_personal_data_gender_spinner)).setSelection(0);
        } else {
            ((Spinner) viewGroup.findViewById(R.id.patient_form_activity_personal_data_gender_spinner)).setSelection(1);
        }
    }


    public static void insertObservationsInViewGroup(EditText editText, Paciente paciente) {
        ViewsUtil.insertInEditText(editText, paciente.getObservacoes());
    }



    public static void SelectClinica(Spinner spinnerClinica, long clinicaId, List<Clinica> clinicas) {
        int i = 0;
        for (Clinica clinica : clinicas) {
            if (clinicaId == clinica.getId()) {
                break;
            }
            i++;
        }
        if (i < clinicas.size()) {
            spinnerClinica.setSelection(i);
        }
    }

    public static boolean checkViewGroupIsCorrectly(ViewGroup viewGroup){
        return  viewGroup != null && (viewGroup.getId() == R.id.patient_form_activity ||
                viewGroup.getId() == R.id.patient_form_activity_personal_data_layout);
    }
}
