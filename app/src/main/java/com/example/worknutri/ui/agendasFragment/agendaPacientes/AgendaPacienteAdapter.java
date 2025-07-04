package com.example.worknutri.ui.agendasFragment.agendaPacientes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.worknutri.sqlLite.dao.clinica.ClinicaDao;
import com.example.worknutri.sqlLite.dao.paciente.AntropometriaDao;
import com.example.worknutri.sqlLite.dao.paciente.PacienteDao;
import com.example.worknutri.sqlLite.dao.paciente.PatologiaDao;
import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.agendaPacientes.Inflaters.LetterPacienteFragment;
import com.example.worknutri.ui.agendasFragment.filter.pacientesFilter.PacienteFilterPojo;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AgendaPacienteAdapter {
    private final Context context;
    private final PacienteDao pacienteDao;
    private final AntropometriaDao antropometriaDao;
    private final PatologiaDao patologiaDao;
    private final ClinicaDao clinicaDao;
    private PacienteFilterPojo pacienteFilterPojo;

    public AgendaPacienteAdapter(Context context) {
        this.context = context;
        AppDataBase appDB = AppDataBase.getInstance(context);
        pacienteDao = appDB.pacienteDao();
        antropometriaDao = appDB.antropometriaDao();
        patologiaDao = appDB.patologiaDao();
        clinicaDao = appDB.clinicaDao();
        pacienteFilterPojo =  new PacienteFilterPojo(getPacientes(), antropometriaDao.getAll(),
                patologiaDao.getAll(), clinicaDao.getAllInOrder());
    }

    public void inflateAgenda(LayoutInflater inflater, LinearLayout linearLayout) {
        if (!pacienteFilterPojo.getState().isInOrder()){
        }
        linearLayout.removeAllViews();
        LetterPacienteFragment letterPacienteFragment = new LetterPacienteFragment(inflater, pacienteFilterPojo.getPacienteSelected());
        letterPacienteFragment.generateAgenda(linearLayout);


    }

    public void inflateAgenda(LayoutInflater inflater, LinearLayout linearLayout, List<Paciente> pacientes) {
        linearLayout.removeAllViews();
        LetterPacienteFragment letterPacienteFragment = new LetterPacienteFragment(inflater, pacientes);
        letterPacienteFragment.generateAgenda(linearLayout);


    }


    public List<Paciente> getPacientes() {
        return pacienteDao.getAllInOrder();
    }

    public Context getContext() {
        return this.context;
    }

    public void setPacienteFilterPojo(PacienteFilterPojo pacienteFilterPojo) {
        this.pacienteFilterPojo = pacienteFilterPojo;
    }
    public PacienteFilterPojo getPacienteFilterPojo(){
        return pacienteFilterPojo;
    }
    public void updatePojo(){
        pacienteFilterPojo.setPacientes(getPacientes());
        pacienteFilterPojo.setAntropometriaList(antropometriaDao.getAll());
        pacienteFilterPojo.setPatologiaList(patologiaDao.getAll());
        pacienteFilterPojo.setClinicas(clinicaDao.getAllInOrder());
    }
}
