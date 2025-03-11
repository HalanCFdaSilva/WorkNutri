package com.example.worknutri.ui.editTextKeysListener;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.agendaClinicas.AgendaClinicasAdapter;
import com.example.worknutri.ui.agendasFragment.agendaClinicas.AgendaClinicasFragment;
import com.example.worknutri.ui.agendasFragment.agendaPacientes.AgendaPacienteAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClinicaSearchKeyListener implements View.OnKeyListener {
    private final AgendaClinicasAdapter adapter;
    private LinearLayout layoutToInsert;

    public ClinicaSearchKeyListener(AgendaClinicasAdapter adapter, LinearLayout layoutToInsert) {
        this.adapter = adapter;
        this.layoutToInsert = layoutToInsert;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        String string = ((TextInputEditText) v).getText().toString();
        List<Clinica> clinicasFiltradas = isEquals(string);
        adapter.inflateAgenda(((Activity)adapter.getContext()).getLayoutInflater(),layoutToInsert,clinicasFiltradas);


        return false;
    }

    private List<Clinica> isEquals(String string){
        Stream<Clinica> pacienteStream = adapter.getClinicaList().stream().filter(clinica -> clinica.getNome().contains(string));
        return pacienteStream.collect(Collectors.toList());

    }
}
