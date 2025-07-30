package com.example.worknutri.ui.agendasFragment.agendaClinicas.Inflaters.ClinicScheduleInflater;

import android.content.Context;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.agendaClinicas.Inflaters.ScheduleInflater;

import java.util.List;
import java.util.stream.Collectors;

public class NameClinicScheduleInflater extends ScheduleInflater<Clinica> {

    protected NameClinicScheduleInflater(Context context) {
        super(context);
    }

    @Override
    protected List<Clinica> filterElements(List<Clinica> elementsToAdd) {
        String divisor = getCategoryTitle(elementsToAdd.get(0));
        return elementsToAdd.stream().filter(clinica -> clinica.getNome().substring(0,1)
                .equalsIgnoreCase(divisor)).collect(Collectors.toList());
    }

    @Override
    protected String getCategoryTitle(Clinica firstElementOfCategory) {
        return firstElementOfCategory.getNome().substring(0,1);
    }

}
