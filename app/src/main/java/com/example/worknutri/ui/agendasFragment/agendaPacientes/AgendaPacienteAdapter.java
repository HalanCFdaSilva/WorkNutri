package com.example.worknutri.ui.agendasFragment.agendaPacientes;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.worknutri.sqlLite.dao.paciente.PacienteDao;
import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.agendaPacientes.Inflaters.LetterPacienteFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.security.PublicKey;
import java.util.List;

public class AgendaPacienteAdapter {
    private final Context context;
    private final PacienteDao pacienteDao;

    public AgendaPacienteAdapter(Context context) {
        this.context = context;
        pacienteDao = AppDataBase.getInstance(context).pacienteDao();
    }

    public void inflateAgenda(LayoutInflater inflater, LinearLayout linearLayout) {
        linearLayout.removeAllViews();
        LetterPacienteFragment letterPacienteFragment = new LetterPacienteFragment(inflater, pacienteDao.getAllInOrder());
        letterPacienteFragment.generateAgenda(linearLayout);


    }
    public void inflateAgenda(LayoutInflater inflater, LinearLayout linearLayout,List<Paciente> pacientes) {
        linearLayout.removeAllViews();
        LetterPacienteFragment letterPacienteFragment = new LetterPacienteFragment(inflater, pacientes);
        letterPacienteFragment.generateAgenda(linearLayout);


    }

    public List<Paciente> getPacientes(){
        return pacienteDao.getAllInOrder();
    }

    public Context getContext(){
        return this.context;
    }
}
