package com.example.worknutri.ui.agendasFragment.agendaPacientes;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.LinearLayout;

import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextWatcherAgendaPaciente implements TextWatcher {
    private final AgendaPacienteAdapter adapter;
    private final LinearLayout layoutToInsert;

    public TextWatcherAgendaPaciente(AgendaPacienteAdapter adapter, LinearLayout layoutToInsert) {
        this.adapter = adapter;
        this.layoutToInsert = layoutToInsert;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


        List<Paciente> pacientesFiltrados = isEquals(s.toString());
        adapter.inflateAgenda(((Activity) adapter.getContext()).getLayoutInflater(), layoutToInsert, pacientesFiltrados);


    }

    private List<Paciente> isEquals(String string) {
        Stream<Paciente> pacienteStream = adapter.getPacientes().stream().filter(paciente -> paciente.getNomePaciente().contains(string));
        return pacienteStream.collect(Collectors.toList());

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
