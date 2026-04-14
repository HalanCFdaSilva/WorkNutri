package com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


public class PatientFilterPojo implements Serializable {

    private List<Paciente> patientList;
    private List<Paciente> patientsSelected;
    private List<Antropometria> anthropometryList;
    private List<Clinica> clinicList;
    private final PatientFilterUiState state;

    public PatientFilterPojo(List<Paciente> patientList, List<Antropometria> anthropometryList,
                             List<Clinica> clinicList) {

        this.patientList = patientList;
        resetPatientsSelected();
        this.anthropometryList = anthropometryList;

        this.clinicList = clinicList;

        Optional<Paciente> max = patientList.stream().max(Comparator.comparing(Paciente::getIdade));
        int maxValueSelected = max.map(Paciente::getIdade).orElse(0);
        Optional<Paciente> min = patientList.stream().min(Comparator.comparing(Paciente::getIdade));
        int minValueSelected = min.map(Paciente::getIdade).orElse(0);
        state = new PatientFilterUiState(minValueSelected, maxValueSelected);
    }

    public void resetPatientsSelected() {
        setPatientsSelected(new ArrayList<>(patientList));
    }

    public List<Paciente> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Paciente> patientList) {
        this.patientList = patientList;
    }

    public List<Antropometria> getAnthropometryList() {
        return anthropometryList;
    }

    public void setAnthropometryList(List<Antropometria> anthropometryList) {
        this.anthropometryList = anthropometryList;
    }

    public List<Clinica> getClinicas() {
        return clinicList;
    }

    public void setClinicas(List<Clinica> clinicas) {
        this.clinicList = clinicas;
    }

    public List<Paciente> getPatientsSelected() {
        return patientsSelected;
    }

    public void setPatientsSelected(List<Paciente> patientsSelected) {
        this.patientsSelected = patientsSelected;
    }

    public PatientFilterUiState getState() {
        return state;
    }
}
