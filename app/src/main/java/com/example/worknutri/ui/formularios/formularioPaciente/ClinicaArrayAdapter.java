package com.example.worknutri.ui.formularios.formularioPaciente;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.agendaInflater.cardsInflater.ClinicaCardInflater;

import java.util.List;

public class ClinicaArrayAdapter extends BaseAdapter {
    private final Context context;
    private final List<Clinica> clinicas;


    public ClinicaArrayAdapter(Context context, List<Clinica> clinicas) {
        this.context = context;
        this.clinicas = clinicas;

    }

    @Override
    public int getCount() {
        return clinicas != null ? clinicas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Clinica clinica = clinicas.get(position);
        return ClinicaCardInflater.generateClinicaCardForSpinner(parent, clinica, context);
    }
}
