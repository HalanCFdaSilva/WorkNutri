package com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.pacienteTypes;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.agendaInflater.ScheduleInflater;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AntropometryScheduleInflater extends ScheduleInflater<Paciente> {

    protected final List<Antropometria> antropometriaList;

    protected AntropometryScheduleInflater(Context context, List<Antropometria> antropometriaList) {
        super(context);
        this.antropometriaList = antropometriaList;
    }
    protected AntropometryScheduleInflater(Context context) {
        this(context, new ArrayList<>());
    }

    @Override
    protected List<Paciente> filterElements(List<Paciente> elementsToAdd) {

        int comparator = getCategoryValor(elementsToAdd.get(0));
        return elementsToAdd.stream().filter(paciente -> {
            int compared = getCategoryValor(paciente);
            return insideFilter( comparator, compared);
        }).collect(Collectors.toList());
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

    private boolean insideFilter(int comparator, int compared) {
        int decimalComparator = getDecimalValue(comparator);
        int decimalCompared = getDecimalValue(compared);
        return decimalComparator == decimalCompared;

    }

    protected int getDecimalValue(int ageCompare) {
        String compareString = String.valueOf(ageCompare);
        int decimal = 0;
        if (compareString.length() > 1)
            decimal = Integer.parseInt(compareString.substring(0,1));
        return decimal;
    }

    @Override
    protected String getCategoryTitle(Paciente firstElementOfCategory) {
        String decimalValue = String.valueOf(getDecimalValue(getCategoryValor(firstElementOfCategory)));
        if (decimalValue.equals("0"))
            decimalValue = "";
        return String.format("%s0,00 - %s9,99 %s",decimalValue,decimalValue, getMeasureUnit());
    }

    protected String getMeasureUnit(){
        return null;
    }


}
