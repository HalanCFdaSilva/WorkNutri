package com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClinicaFilterPojo implements Serializable {

    private List<Clinica> clinicas;
    private List<Clinica> clinicasSelected;
    private List<DayOfWork> dayOfWorkList;

    private List<Paciente> pacientes;
    private final ClinicaFilterUiState uiState;

    public ClinicaFilterPojo() {
        uiState = new ClinicaFilterUiState();
        clinicas = new ArrayList<>();
        clinicasSelected = new ArrayList<>();
        dayOfWorkList = new ArrayList<>();
    }


    public List<Clinica> getClinicas() {
        return clinicas;
    }

    public void setClinicas(List<Clinica> clinicas) {
        this.clinicas = clinicas;
    }

    public List<Clinica> getClinicasSelected() {
        return clinicasSelected;
    }

    public void setClinicasSelected(List<Clinica> clinicasSelected) {
        this.clinicasSelected = clinicasSelected;
    }

    public List<DayOfWork> getDayOfWorkList() {
        return dayOfWorkList;
    }

    public void setDayOfWorkList(List<DayOfWork> dayOfWorkList) {
        this.dayOfWorkList = dayOfWorkList;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public ClinicaFilterUiState getUiState() {
        return uiState;
    }
}
