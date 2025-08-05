package com.example.worknutri.ui.agendasFragment.fragments.agendaClinicas;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.LinearLayout;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextWatcherAgendaClinica implements TextWatcher {
    private final AgendaClinicasAdapter adapter;
    private final LinearLayout layoutToInsert;

    public TextWatcherAgendaClinica(AgendaClinicasAdapter adapter, LinearLayout layoutToInsert) {
        this.adapter = adapter;
        this.layoutToInsert = layoutToInsert;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


        List<Clinica> clinicasFiltradas = isEquals(s.toString());
        adapter.inflateAgenda(layoutToInsert, clinicasFiltradas);

    }

    private List<Clinica> isEquals(String string) {
        Stream<Clinica> pacienteStream = adapter.getClinicaList().stream().filter(clinica -> clinica.getNome().contains(string));
        return pacienteStream.collect(Collectors.toList());

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
