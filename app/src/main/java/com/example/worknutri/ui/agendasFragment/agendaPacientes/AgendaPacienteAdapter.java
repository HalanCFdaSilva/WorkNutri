package com.example.worknutri.ui.agendasFragment.agendaPacientes;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.dao.paciente.PacienteDao;
import com.example.worknutri.ui.agendasFragment.agendaPacientes.Inflaters.LetterPacienteFragment;

public class AgendaPacienteAdapter {
    private final Context context;
    private PacienteDao pacienteDao;

    public AgendaPacienteAdapter(Context context) {
        this.context = context;
        pacienteDao = AppDataBase.getInstance(context).pacienteDao();
    }

    public void inflateAgenda(LayoutInflater inflater, LinearLayout linearLayout){
        linearLayout.removeAllViews();
        LetterPacienteFragment letterPacienteFragment = new LetterPacienteFragment(inflater,pacienteDao.getAllInOrder());
        letterPacienteFragment.generateAgenda(linearLayout);



    }
}
