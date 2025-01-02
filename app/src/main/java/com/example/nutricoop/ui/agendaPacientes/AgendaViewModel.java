package com.example.nutricoop.ui.agendaPacientes;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nutricoop.sqlLite.dao.PacienteDao;
import com.example.nutricoop.sqlLite.domain.paciente.Paciente;

import java.util.List;


public class AgendaViewModel extends ViewModel {

    private final LiveData<List<Paciente>> pacientes;

    public AgendaViewModel(PacienteDao dao) {
        this.pacientes = dao.getAllInOrderF();

    }

    public LiveData<List<Paciente>> getPacientes() {
        return pacientes;
    }
}