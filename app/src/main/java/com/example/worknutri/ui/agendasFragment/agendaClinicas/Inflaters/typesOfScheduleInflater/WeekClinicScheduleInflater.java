package com.example.worknutri.ui.agendasFragment.agendaClinicas.Inflaters.typesOfScheduleInflater;

import android.content.Context;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.ui.agendasFragment.agendaClinicas.Inflaters.ScheduleInflater;

import java.util.ArrayList;
import java.util.List;

public class WeekClinicScheduleInflater extends ScheduleInflater<Clinica> {
    private final String[] daysOfWeek;
    private int currentDayIndex = 0;
    private final List<DayOfWork> daysOfWork;
    protected WeekClinicScheduleInflater(Context context, List<DayOfWork> daysOfWork) {
        super(context);
        daysOfWeek = context.getResources().getStringArray(R.array.dias_semana);
        this.daysOfWork = daysOfWork;
    }

    @Override
    protected List<Clinica> filterElements(List<Clinica> elementsToAdd) {
        List<Clinica> clinicas = new ArrayList<>(elementsToAdd);
        daysOfWork.stream().filter( dayOfWork -> dayOfWork.getDayOfWeek().equals(daysOfWeek[currentDayIndex]))
                .forEach(dayOfWork -> clinicas
                        .removeIf(clinica -> clinica.getId() != dayOfWork.getIdClinica()));

        return clinicas;
    }

    @Override
    protected String getCategoryTitle(Clinica firstElementOfCategory) {
        String dayOfWeek = daysOfWeek[currentDayIndex];
        currentDayIndex++;
        return dayOfWeek;
    }


}
