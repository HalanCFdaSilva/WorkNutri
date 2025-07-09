package com.example.worknutri.ui.agendasFragment.filter.pojos;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.ui.agendasFragment.filter.pojos.UiState.pacienteFilter.PacienteFilterUiState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


public class PacienteFilterPojo implements Serializable {

    private List<Paciente> pacientes;
    private List<Paciente> pacienteSelected;
    private List<Antropometria> antropometriaList;
    private List<Patologia> patologiaList;
    private List<Clinica> clinicas;
    private final PacienteFilterUiState state;

    public PacienteFilterPojo(List<Paciente> pacientes, List<Antropometria> antropometriaList,
                              List<Patologia> patologiaList, List<Clinica> clinicas) {

        this.pacientes = pacientes;
        resetPacienteSelected();
        this.antropometriaList = antropometriaList;
        this.patologiaList = patologiaList;
        this.clinicas = clinicas;

        Optional<Paciente> max = pacientes.stream().max(Comparator.comparing(Paciente::getIdade));
        int maxValueSelected = max.map(Paciente::getIdade).orElse(0);
        Optional<Paciente> min = pacientes.stream().min(Comparator.comparing(Paciente::getIdade));
        int minValueSelected = min.map(Paciente::getIdade).orElse(0);
        state = new PacienteFilterUiState(minValueSelected, maxValueSelected);
    }

    public void resetPacienteSelected() {
        setPacienteSelected(new ArrayList<>(pacientes));
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

    public PacienteFilterUiState getState() {
        return state;
    }
}
