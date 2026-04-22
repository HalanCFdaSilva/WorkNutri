package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicFilterCategories;

import androidx.annotation.NonNull;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;

import java.util.List;
import java.util.stream.Collectors;

public class ClinicsSelector {

    private final List<Clinica> clinicList;

    public ClinicsSelector(List<Clinica> clinicList) {
        this.clinicList = clinicList;
    }

    @NonNull
    public List<Clinica> byDayOfWeek(String dayOfWeek, List<DayOfWork> dayOfWorkList) {
        List<DayOfWork> daysOfWorkFiltred = dayOfWorkList.stream()
                .filter(dayOfWork -> dayOfWork.getDayOfWeek().equals(dayOfWeek)).collect(Collectors.toList());

        return getClinicListFromDaysOfWork(daysOfWorkFiltred);
    }


    @NonNull
    public List<Clinica> byRangeOfHourWork(List<Float> hourRangeInMinutes, List<DayOfWork> dayOfWorkList) {


        List<DayOfWork> daysOfWorkInArrange = dayOfWorkList.stream().filter(dayOfWork -> {
                    float horaInicio = getTimeInMinutes(dayOfWork.getHoraInicio());
                    float horaFim = getTimeInMinutes(dayOfWork.getHoraFim());
                    return horaInicio >= hourRangeInMinutes.get(0) && horaFim <= hourRangeInMinutes.get(1);
                })
                .collect(Collectors.toList());

        return getClinicListFromDaysOfWork(daysOfWorkInArrange);
    }
    private float getTimeInMinutes(String horaInicio) {
        float hoursInMinute = Float.parseFloat(horaInicio.substring(0, 2))*60;
        float minutes = Float.parseFloat(horaInicio.substring(3, 5));
        return hoursInMinute + minutes;
    }

    @NonNull
    private List<Clinica> getClinicListFromDaysOfWork(List<DayOfWork> daysOfWorkFiltred) {
        return clinicList.stream()
                .filter(clinica -> {
                    for (DayOfWork dayOfWork : daysOfWorkFiltred) {
                        if (dayOfWork.getIdClinica() == clinica.getId())
                            return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }
}
