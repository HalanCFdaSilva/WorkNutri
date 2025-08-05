package com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.pacienteTypes;

import android.content.Context;

import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.agendaInflater.ScheduleInflater;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AntropometriaScheduleInflater extends ScheduleInflater<Paciente> {

    protected final List<Antropometria> antropometriaList;

    protected AntropometriaScheduleInflater(Context context, List<Antropometria> antropometriaList) {
        super(context);
        this.antropometriaList = antropometriaList;
    }

    @Override
    protected List<Paciente> filterElements(List<Paciente> elementsToAdd) {
        int weightToCompare = getElementToCompare(elementsToAdd.get(0));
        return elementsToAdd.stream().filter(paciente -> {
            int weight = getElementToCompare(paciente);
            return weight == weightToCompare;
        }).collect(Collectors.toList());
    }

    @Override
    protected String getCategoryTitle(Paciente firstElementOfCategory) {
        return String.valueOf(getElementToCompare(firstElementOfCategory));
    }

    protected abstract int getElementToCompare(Paciente paciente);
}
