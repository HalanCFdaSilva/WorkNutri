package com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.pacienteTypes;

import android.content.Context;

import com.example.worknutri.calcular.CalculadorAntropometrico;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.agendaInflater.ScheduleInflater;
import java.util.List;
import java.util.stream.Collectors;

public class AgeScheduleInflater extends ScheduleInflater<Paciente> {
    public AgeScheduleInflater(Context context) {
        super(context);
    }

    @Override
    protected List<Paciente> filterElements(List<Paciente> elementsToAdd) {
        int ageCompare = CalculadorAntropometrico.getYearFromDate(elementsToAdd.get(0).getNascimento());
        return elementsToAdd.stream().filter(paciente -> {
            int pacienteAge = CalculadorAntropometrico.getYearFromDate(paciente.getNascimento());
            return insideFilter(pacienteAge, ageCompare);
        }).collect(Collectors.toList());

    }

    private boolean insideFilter(int pacienteAge, int ageCompare) {
        int decimalToCompare = getDecimalValue(ageCompare);
        int anotherDecimal = getDecimalValue(pacienteAge);
        return decimalToCompare == anotherDecimal;

    }

    private int getDecimalValue(int ageCompare) {
        String compareString = String.valueOf(ageCompare);
        int decimal = 0;
        if (compareString.length() > 1)
            decimal = Integer.parseInt(compareString.substring(1));
        return decimal;
    }

    @Override
    protected String getCategoryTitle(Paciente firstElementOfCategory) {
        String nascimento = firstElementOfCategory.getNascimento();
        int yearFromDate = CalculadorAntropometrico.getYearFromDate(nascimento);
        String decimalValue = String.valueOf(getDecimalValue(yearFromDate));
        if (decimalValue.equals("0"))
            decimalValue = "";
        return String.format("%s0 - %s9 anos", decimalValue,decimalValue);
    }
}
