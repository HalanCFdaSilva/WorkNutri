package com.example.worknutri.ui.agendasFragment.agendaOrdenators;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ClinicOrdainer {

    private final List<Clinica> toOrder;
    protected ClinicOrdainer(List<Clinica> clinicsToOrder) {
        this.toOrder = clinicsToOrder;
    }

    public List<Clinica> byName(boolean isReversed) {
        Comparator<Clinica> comparator = Comparator.comparing(c -> c.getNome().toUpperCase());
        if (isReversed)
            return toOrder.stream().sorted(comparator.reversed())
                .collect(Collectors.toList());
        return toOrder.stream().sorted(comparator)
                .collect(Collectors.toList());
        }

    public List<Clinica> byDistrict() {
        return toOrder.stream().sorted(Comparator.comparing(Clinica::getBairro))
                .collect(Collectors.toList());
    }

    public List<Clinica> byCity() {
        return toOrder.stream().sorted(Comparator.comparing(Clinica::getCidade))
                .collect(Collectors.toList());
    }

    public List<Clinica> byDayOfWeek( List<String> dayOfWeek, List<DayOfWork> daysOfWork) {

        List<DayOfWork> daysOfWorrkInsideFilter = daysOfWork.stream()
                .filter(dayOfWork -> dayOfWeek.contains(dayOfWork.getDayOfWeek()))
                .collect(Collectors.toList());


        List<Clinica> clinicsToReturn = new ArrayList<>();
        if (!daysOfWorrkInsideFilter.isEmpty()){
            clinicsToReturn = toOrder.stream().filter(clinic -> daysOfWorrkInsideFilter.stream()
                            .anyMatch(dayOfWork -> dayOfWork.getIdClinica() == clinic.getId()))
                    .collect(Collectors.toList());
        }

        return clinicsToReturn;
    }

    public List<Clinica> byNumberPatients( List<Paciente> patients) {
        toOrder.sort((clinic1, clinic2) -> {
            if (patients.isEmpty())
                return 0;
            int numberOfPatientsOfClinica1 = (int) patients.stream()
                    .filter(paciente -> paciente.getClinicaId() == clinic1.getId())
                    .count();
            int numberOfPatientsOfClinica2 = (int) patients.stream()
                    .filter(paciente -> paciente.getClinicaId() == clinic2.getId())
                    .count();
            return Integer.compare(numberOfPatientsOfClinica1, numberOfPatientsOfClinica2);
        });
        return toOrder;
    }


}
