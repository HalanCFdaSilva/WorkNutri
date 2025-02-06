package com.example.nutricoop.ui.agendasFragment.agendaClinicas;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.nutricoop.sqlLite.dao.clinica.ClinicaDao;
import com.example.nutricoop.sqlLite.domain.clinica.Clinica;
import com.example.nutricoop.sqlLite.database.AppDataBase;
import com.example.nutricoop.ui.agendasFragment.agendaClinicas.Inflaters.LetterClinicaFragment;

import java.util.List;

public class AgendaClinicasAdapter {

    private ClinicaDao dao;
    private Context context;

    public AgendaClinicasAdapter(Context context) {
        dao = AppDataBase.getInstance(context).clinicaDao();
        this.context = context;
    }

    public void inflateAgenda(LayoutInflater inflater, LinearLayout linearLayout){
        List<Clinica> clinicaList = dao.getAllInOrder();
        linearLayout.removeAllViews();

        LetterClinicaFragment letterClinicaFragment = new LetterClinicaFragment(inflater,clinicaList);
        letterClinicaFragment.generateAgenda(linearLayout,context);

    }
}
