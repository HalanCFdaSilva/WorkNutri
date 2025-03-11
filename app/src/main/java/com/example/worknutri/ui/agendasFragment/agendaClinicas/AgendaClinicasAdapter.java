package com.example.worknutri.ui.agendasFragment.agendaClinicas;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.worknutri.sqlLite.dao.clinica.ClinicaDao;
import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.agendaClinicas.Inflaters.LetterClinicaFragment;

import java.util.List;

public class AgendaClinicasAdapter {

    private final ClinicaDao dao;
    private final Context context;

    public AgendaClinicasAdapter(Context context) {
        dao = AppDataBase.getInstance(context).clinicaDao();
        this.context = context;
    }

    public void inflateAgenda(LayoutInflater inflater, LinearLayout linearLayout) {
        List<Clinica> clinicaList = dao.getAllInOrder();
        linearLayout.removeAllViews();

        LetterClinicaFragment letterClinicaFragment = new LetterClinicaFragment(inflater, clinicaList);
        letterClinicaFragment.generateAgenda(linearLayout, context);

    }

    public void inflateAgenda(LayoutInflater inflater, LinearLayout linearLayout,List<Clinica> clinicaList) {

        linearLayout.removeAllViews();

        LetterClinicaFragment letterClinicaFragment = new LetterClinicaFragment(inflater, clinicaList);
        letterClinicaFragment.generateAgenda(linearLayout, context);

    }

    public Context getContext() {
        return context;
    }
    public List<Clinica> getClinicaList(){
        return dao.getAllInOrder();
    }
}
