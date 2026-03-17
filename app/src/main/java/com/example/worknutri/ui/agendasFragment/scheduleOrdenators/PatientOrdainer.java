package com.example.worknutri.ui.agendasFragment.scheduleOrdenators;

import com.example.worknutri.calcular.ClassificacaoImc;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PatientOrdainer {

    private final List<Paciente> toOrder;
    protected PatientOrdainer(List<Paciente> patientsToOrder) {
        this.toOrder = patientsToOrder;
    }

    public List<Paciente> byName(boolean isReversed) {
        Comparator<Paciente> comparator = Comparator.comparing(p -> p.getNomePaciente().toUpperCase());

        if (isReversed)
            return toOrder.stream().sorted(comparator.reversed())
                    .collect(Collectors.toList());
        return toOrder.stream().sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<Paciente> byBMICategory(List<Antropometria> anthropometryList) {
        return toOrder.stream().sorted(Comparator
                .comparing(patient -> valueToBMICategory(anthropometryList, patient)))
                .collect(Collectors.toList());
    }

    private static float valueToBMICategory(List<Antropometria> anthropometryList, Paciente patient) {
        Antropometria anthropometry = anthropometryList.stream()
                .filter(a -> a.getIdPaciente() == patient.getId())
                .findFirst()
                .orElse(null);
        if (anthropometry != null){
            ClassificacaoImc imc = ClassificacaoImc.tipoImc(Double.parseDouble(anthropometry.getImc()));
            return imc != null ? imc.getPriority() : Float.MAX_VALUE;
        }
        return Float.MAX_VALUE;
    }

    public List<Paciente> byHeight(List<Antropometria> anthropometryList) {
        return toOrder.stream().sorted(Comparator.comparing(patient -> {
            Antropometria anthropometry = anthropometryList.stream()
                    .filter(a -> a.getIdPaciente() == patient.getId())
                    .findFirst()
                    .orElse(null);
            return anthropometry != null ? Float.parseFloat(anthropometry.getAltura()): Float.MAX_VALUE;

        })).collect(Collectors.toList());
    }

    public List<Paciente> byWeight(List<Antropometria> anthropometryList) {
        return toOrder.stream().sorted(Comparator.comparing(patient -> {
            Antropometria anthropometry = anthropometryList.stream()
                    .filter(a -> a.getIdPaciente() == patient.getId())
                    .findFirst()
                    .orElse(null);
            return anthropometry != null ? Float.parseFloat(anthropometry.getPeso()): Float.MAX_VALUE;

        })).collect(Collectors.toList());
    }

    public List<Paciente> byAge() {
        return toOrder.stream().sorted(Comparator.comparing(Paciente::getIdade))
                .collect(Collectors.toList());
    }
}
