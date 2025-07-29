package com.example.worknutri.ui.agendasFragment.agendaOrdenators;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ClinicaOrder {

    private final List<Clinica> toOrder;
    protected ClinicaOrder(List<Clinica> clinicasToOrder) {
        this.toOrder = clinicasToOrder;
    }

    public List<Clinica> byName(boolean isReversed) {
        if (isReversed)
            return toOrder.stream().sorted(Comparator.comparing(Clinica::getNome).reversed())
                .collect(Collectors.toList());
        return toOrder.stream().sorted(Comparator.comparing(Clinica::getNome))
                .collect(Collectors.toList());
    }

    public List<Clinica> byBairro() {
        return toOrder.stream().sorted(Comparator.comparing(Clinica::getBairro))
                .collect(Collectors.toList());
    }

    public List<Clinica> byCidade() {
        return toOrder.stream().sorted(Comparator.comparing(Clinica::getCidade))
                .collect(Collectors.toList());
    }

    public List<Clinica> byDayOfWeek( List<String> dayOfWeek, List<DayOfWork> daysOfWork) {

        List<DayOfWork> daysOfWorrkInsideFilter = daysOfWork.stream()
                .filter(dayOfWork -> dayOfWeek.contains(dayOfWork.getDayOfWeek()))
                .collect(Collectors.toList());


        List<Clinica> clinicasToReturn = new ArrayList<>();
        if (!daysOfWorrkInsideFilter.isEmpty()){
            clinicasToReturn = toOrder.stream().filter(clinica -> daysOfWorrkInsideFilter.stream()
                            .anyMatch(dayOfWork -> dayOfWork.getIdClinica() == clinica.getId()))
                    .collect(Collectors.toList());
        }

        return clinicasToReturn;
    }

    public List<Clinica> byNumberPatients( List<Paciente> pacientes) {
        toOrder.sort((clinica1, clinica2) -> {
            if (pacientes.isEmpty())
                return 0;
            int numberOfPatientsOfClinica1 = (int) pacientes.stream()
                    .filter(paciente -> paciente.getClinicaId() == clinica1.getId())
                    .count();
            int numberOfPatientsOfClinica2 = (int) pacientes.stream()
                    .filter(paciente -> paciente.getClinicaId() == clinica2.getId())
                    .count();
            return Integer.compare(numberOfPatientsOfClinica1, numberOfPatientsOfClinica2);
        });
        return toOrder;
    }


}
