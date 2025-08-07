package com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.pacienteTypes;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.agendaInflater.ScheduleInflater;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AntropometryScheduleInflater extends ScheduleInflater<Paciente> {

    protected final List<Antropometria> antropometriaList;

    protected AntropometryScheduleInflater(Context context, List<Antropometria> antropometriaList) {
        super(context);
        this.antropometriaList = antropometriaList;
    }

    @Override
    protected List<Paciente> filterElements(List<Paciente> elementsToAdd) {

        int firstelementTocompare = getCategoryValor(elementsToAdd.get(0));
        return elementsToAdd.stream().filter(paciente -> {
            int weight = getCategoryValor(paciente);
            return weight == firstelementTocompare;
        }).collect(Collectors.toList());
    }


    @Override
    protected String getCategoryTitle(Paciente firstElementOfCategory) {
        return String.valueOf(getCategoryValor(firstElementOfCategory));
    }


    protected int getCategoryValor(Paciente paciente) {
        Stream<Antropometria> antropometriaStream = getAntropometriaStream(paciente);
        return antropometriaStream.map(antropometria -> {
                    String categoryString = getCategoryString(antropometria);
                    int dotIndex = categoryString.indexOf('.');
            return Integer.parseInt(categoryString.substring(0, dotIndex));})
                .findFirst()
                .orElse(0);
    }

    @NonNull
    protected Stream<Antropometria> getAntropometriaStream(Paciente paciente) {
        return antropometriaList.stream()
                .filter(antropometria -> antropometria.getIdPaciente() == paciente.getId());

    }

    protected String getCategoryString(Antropometria antropometria){
        return null;
    }


}
