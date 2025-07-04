package com.example.worknutri.ui.agendasFragment.filter.pacientesFilter;

import android.util.Log;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PacienteFilterPojo implements Serializable {

    private List<Paciente> pacientes;
    private List<Paciente> pacienteSelected;
    private List<Antropometria> antropometriaList;
    private List<Patologia> patologiaList;
    private List<Clinica> clinicas;
    private final UiState state;

    public PacienteFilterPojo(List<Paciente> pacientes, List<Antropometria> antropometriaList,
                              List<Patologia> patologiaList, List<Clinica> clinicas) {

        this.pacientes = pacientes;
        resetPacienteSelected();
        this.antropometriaList = antropometriaList;
        this.patologiaList = patologiaList;
        this.clinicas = clinicas;
        state = new UiState();
    }

    public void resetPacienteSelected() {
        setPacienteSelected(new ArrayList<Paciente>(pacientes));
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public List<Antropometria> getAntropometriaList() {
        return antropometriaList;
    }

    public void setAntropometriaList(List<Antropometria> antropometriaList) {
        this.antropometriaList = antropometriaList;
    }

    public List<Patologia> getPatologiaList() {
        return patologiaList;
    }

    public void setPatologiaList(List<Patologia> patologiaList) {
        this.patologiaList = patologiaList;
    }

    public List<Clinica> getClinicas() {
        return clinicas;
    }

    public void setClinicas(List<Clinica> clinicas) {
        this.clinicas = clinicas;
    }

    public List<Paciente> getPacienteSelected() {
        return pacienteSelected;
    }

    public void setPacienteSelected(List<Paciente> pacienteSelected) {
        this.pacienteSelected = pacienteSelected;
    }

    public UiState getState() {
        return state;
    }
}
