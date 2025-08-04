package com.example.worknutri.ui.agendasFragment.agendaClinicas.Inflaters.typesOfScheduleInflater;

import android.content.Context;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.agendaClinicas.Inflaters.ScheduleInflater;
import java.util.List;
import java.util.stream.Collectors;

/** @noinspection unchecked*/
public class NameClinicScheduleInflater<T> extends ScheduleInflater<T> {

    protected NameClinicScheduleInflater(Context context) {
        super(context);
    }

    @Override
    protected List<T> filterElements(List<T> elementsToAdd) {
        if (!elementsToAdd.isEmpty()){
            if (elementsToAdd.get(0) instanceof Clinica)
                return filterClinicaElements((List<Clinica>) elementsToAdd);
            else if (elementsToAdd.get(0) instanceof Paciente)
                return filterPacienteElements((List<Paciente>) elementsToAdd);
        }
        return elementsToAdd;
    }


    private List<T> filterClinicaElements(List<Clinica> elementsToAdd) {
        String divisor = getClinicaCategoryTitle(elementsToAdd.get(0));
        return (List<T>) elementsToAdd.stream().filter(clinica -> clinica.getNome().substring(0,1)
                .equalsIgnoreCase(divisor)).collect(Collectors.toList());
    }
    private String getClinicaCategoryTitle(Clinica firstElementOfCategory) {
        return firstElementOfCategory.getNome().substring(0, 1);
    }


    private List<T> filterPacienteElements(List<Paciente> elementsToAdd) {
        String divisor = getPacienteCategoryTitle(elementsToAdd.get(0));
        return (List<T>) elementsToAdd.stream().filter(paciente -> paciente.getNomePaciente().substring(0,1)
                .equalsIgnoreCase(divisor)).collect(Collectors.toList());
    }
    private String getPacienteCategoryTitle(Paciente firstElementOfCategory) {
        return firstElementOfCategory.getNomePaciente().substring(0, 1);
    }


    @Override
    protected String getCategoryTitle(T firstElementOfCategory) {
        if (firstElementOfCategory instanceof Clinica)
            return getClinicaCategoryTitle((Clinica) firstElementOfCategory);
        else if (firstElementOfCategory instanceof Paciente) {
            return getPacienteCategoryTitle((Paciente) firstElementOfCategory);
        }
        throw new IllegalArgumentException("Unsupported type: " + firstElementOfCategory.getClass().getName());
    }



}
