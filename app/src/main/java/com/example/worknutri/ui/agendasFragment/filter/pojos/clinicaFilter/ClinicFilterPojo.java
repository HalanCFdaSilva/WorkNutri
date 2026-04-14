package com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClinicFilterPojo implements Serializable {

    private List<Clinica> clinicsList;
    private List<Clinica> clinicsSelected;
    private List<DayOfWork> dayOfWorkList;

    private List<Paciente> patientList;
    private final ClinicFilterUiState uiState;

    public ClinicFilterPojo() {
        uiState = new ClinicFilterUiState();
        clinicsList = new ArrayList<>();
        clinicsSelected = new ArrayList<>();
        dayOfWorkList = new ArrayList<>();
    }


    public List<Clinica> getClinicsList() {
        return clinicsList;
    }

    public void setClinicsList(List<Clinica> clinicsList) {
        this.clinicsList = clinicsList;
    }

    public List<Clinica> getClinicsSelected() {
        return clinicsSelected;
    }

    public void setClinicsSelected(List<Clinica> clinicsSelected) {
        this.clinicsSelected = clinicsSelected;
    }

    public List<DayOfWork> getDayOfWorkList() {
        return dayOfWorkList;
    }

    public void setDayOfWorkList(List<DayOfWork> dayOfWorkList) {
        this.dayOfWorkList = dayOfWorkList;
    }

    public List<Paciente> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Paciente> patientList) {
        this.patientList = patientList;
    }

    public ClinicFilterUiState getUiState() {
        return uiState;
    }
}
