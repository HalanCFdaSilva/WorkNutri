package com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.clinicsTypes;

import android.content.Context;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.agendaInflater.ScheduleInflater;

import java.util.List;
import java.util.stream.Collectors;

public class DistrictClinicScheduleInflater extends ScheduleInflater<Clinica> {
    public DistrictClinicScheduleInflater(Context context) {
        super(context);
    }

    @Override
    protected List<Clinica> filterElements(List<Clinica> elementsToAdd) {
        String divisor = getCategoryTitle(elementsToAdd.get(0));
        return elementsToAdd.stream().filter(clinica -> clinica.getBairro()
                .equalsIgnoreCase(divisor)).collect(Collectors.toList());
    }

    @Override
    protected String getCategoryTitle(Clinica firstElementOfCategory) {
        return firstElementOfCategory.getBairro();
    }
}
