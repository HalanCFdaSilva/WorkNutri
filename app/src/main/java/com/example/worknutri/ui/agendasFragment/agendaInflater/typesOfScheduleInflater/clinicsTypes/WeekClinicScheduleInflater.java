package com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.clinicsTypes;

import android.content.Context;
import android.view.ViewGroup;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.ui.agendasFragment.agendaInflater.ScheduleInflater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WeekClinicScheduleInflater extends ScheduleInflater<Clinica> {

    public static final String DAY_NOT_FOUND = "SEM DIA CADASTRADO";
    private final String[] daysOfWeek;
    private String currentDay;
    private Map<String, List<DayOfWork>> daysOfWorkByDayOfWeek;

    public WeekClinicScheduleInflater(Context context, List<DayOfWork> daysOfWork) {
        super(context);
        daysOfWeek = context.getResources().getStringArray(R.array.dias_semana);
        generateDaysOfworkForDayOfWeek(daysOfWork);

    }

    private void generateDaysOfworkForDayOfWeek(List<DayOfWork> daysOfWork) {
        daysOfWorkByDayOfWeek = new HashMap<>();
        for (String dayOfWeek : daysOfWeek) {

            List<DayOfWork> daysOfWorkInsideDay = daysOfWork.stream()
                    .filter(dayOfWork -> dayOfWork.getDayOfWeek().equals(dayOfWeek))
                    .collect(Collectors.toList());

            if (!daysOfWorkInsideDay.isEmpty()) {
                daysOfWorkByDayOfWeek.put(dayOfWeek, daysOfWorkInsideDay);
            }
        }
    }

    @Override
    public void generateAgenda(ViewGroup viewGroup, List<Clinica> elementsToAdd) {
        List<Clinica> elementsToAddCopy = new ArrayList<>(elementsToAdd);
        List<Clinica> clinicsIfNotFoundDay = new ArrayList<>(elementsToAdd);

        int i = 0;
        while (i < daysOfWeek.length) {

            currentDay = daysOfWeek[i++];

            List<Clinica> clinicsToInsert = filterElements(elementsToAddCopy);
            if (daysOfWorkByDayOfWeek.containsKey(currentDay) && !clinicsToInsert.isEmpty()) {
                inflateCategory(viewGroup, clinicsToInsert);
                clinicsIfNotFoundDay.removeAll(clinicsToInsert);
            }

        }
        currentDay = DAY_NOT_FOUND;
        if (!clinicsIfNotFoundDay.isEmpty()){
            inflateCategory(viewGroup, clinicsIfNotFoundDay);
        }

    }

    private void inflateCategory(ViewGroup viewGroup, List<Clinica> clinicsFiltred) {
        ViewGroup categoryViewGroup = super.generateCategory(clinicsFiltred.get(0));
        viewGroup.addView(categoryViewGroup);
        inflateCard(categoryViewGroup.findViewById(R.id.agenda_letter_fragment_linear_layout), clinicsFiltred);
    }

    @Override
    protected List<Clinica> filterElements(List<Clinica> elementsToAdd) {
        List<Clinica> clinicas = new ArrayList<>();
        List<DayOfWork> dayOfWorkStream = daysOfWorkByDayOfWeek.get(currentDay);
       if (dayOfWorkStream != null){
           dayOfWorkStream.forEach(dayOfWork -> {
               List<Clinica> clinicsInDay = elementsToAdd.stream()
                       .filter(clinica -> {
                           if (!clinicas.contains(clinica))
                               return clinica.getId() == dayOfWork.getIdClinica();
                           return false;
                       }).collect(Collectors.toList());
               if (!clinicsInDay.isEmpty())
                   clinicas.addAll(clinicsInDay);
           });
       }

        return clinicas;
    }

    @Override
    protected String getCategoryTitle(Clinica firstElementOfCategory) {
        return currentDay;
    }


}
