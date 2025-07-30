package com.example.worknutri.ui.agendasFragment.agendaClinicas.Inflaters.ClinicScheduleInflater;

import android.content.Context;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.agendaClinicas.Inflaters.ScheduleInflater;

import java.util.List;
import java.util.stream.Collectors;

public class CityClinicScheduleInflater extends ScheduleInflater<Clinica> {

    public CityClinicScheduleInflater(Context context) {
        super(context);
    }


    @Override
    protected List<Clinica> filterElements(List<Clinica> elementsToAdd) {
        String currentCity = getCategoryTitle(elementsToAdd.get(0));
        return elementsToAdd.stream().filter(clinica -> clinica.getCidade()
                .equalsIgnoreCase(currentCity)).collect(Collectors.toList());
    }

    @Override
    protected String getCategoryTitle(Clinica firstElementOfCategory) {
        return firstElementOfCategory.getCidade();
    }


}
