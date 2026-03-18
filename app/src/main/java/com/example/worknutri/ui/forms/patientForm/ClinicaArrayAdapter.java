package com.example.worknutri.ui.forms.patientForm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.agendaInflater.cardsInflater.ClinicCardInflater;

import java.util.List;

public class ClinicaArrayAdapter extends BaseAdapter {
    private final Context context;
    private final List<Clinica> clinics;


    public ClinicaArrayAdapter(Context context, List<Clinica> clinics) {
        this.context = context;
        this.clinics = clinics;

    }

    @Override
    public int getCount() {
        return clinics != null ? clinics.size() : 0;
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

        Clinica clinic = clinics.get(position);

        return new ClinicCardInflater(context).inflateCard(clinic);

    }
}
