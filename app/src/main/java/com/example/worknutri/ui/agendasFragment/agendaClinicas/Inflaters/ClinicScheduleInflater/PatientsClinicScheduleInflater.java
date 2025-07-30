package com.example.worknutri.ui.agendasFragment.agendaClinicas.Inflaters.ClinicScheduleInflater;

import static java.util.stream.Collectors.toList;

import android.content.Context;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.agendaClinicas.Inflaters.ScheduleInflater;

import java.util.List;

public class PatientsClinicScheduleInflater extends ScheduleInflater<Clinica> {

    private final List<Paciente> pacientes;
    protected PatientsClinicScheduleInflater(Context context, List<Paciente> pacientes) {
        super(context);
        this.pacientes = pacientes;
    }

    @Override
    protected List<Clinica> filterElements(List<Clinica> elementsToAdd) {
        long divisor = numberOfPatientsInClinic(elementsToAdd.get(0));
        return elementsToAdd.stream().filter(clinica -> numberOfPatientsInClinic(clinica) == divisor).collect(toList());
    }

    @Override
    protected String getCategoryTitle(Clinica firstElementOfCategory) {
        return String.valueOf(numberOfPatientsInClinic(firstElementOfCategory));
    }

    private long numberOfPatientsInClinic(Clinica firstElementOfCategory) {
        return pacientes.stream().filter(paciente -> paciente.getClinicaId() == firstElementOfCategory.getId()).count();
    }
}
