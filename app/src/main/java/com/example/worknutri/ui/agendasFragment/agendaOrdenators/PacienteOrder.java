package com.example.worknutri.ui.agendasFragment.agendaOrdenators;

import com.example.worknutri.calcular.ClassificacaoImc;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PacienteOrder {

    private final List<Paciente> toOrder;
    protected PacienteOrder(List<Paciente> pacientesToOrder) {
        this.toOrder = pacientesToOrder;
    }

    public List<Paciente> byName(boolean isReversed) {
        Comparator<Paciente> comparator = Comparator.comparing(p -> p.getNomePaciente().toUpperCase());

        if (isReversed)
            return toOrder.stream().sorted(comparator.reversed())
                    .collect(Collectors.toList());
        return toOrder.stream().sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<Paciente> byIMCCategory(List<Antropometria> antropometriaList) {
        return toOrder.stream().sorted(Comparator
                .comparing(paciente -> valueToImcCategory(antropometriaList, paciente)))
                .collect(Collectors.toList());
    }

    private static float valueToImcCategory(List<Antropometria> antropometriaList, Paciente paciente) {
        Antropometria antropometria = antropometriaList.stream()
                .filter(a -> a.getIdPaciente() == paciente.getId())
                .findFirst()
                .orElse(null);
        if (antropometria != null){
            ClassificacaoImc imc = ClassificacaoImc.tipoImc(Double.parseDouble(antropometria.getImc()));
            return imc != null ? imc.getPriority() : Float.MAX_VALUE;
        }
        return Float.MAX_VALUE;
    }

    public List<Paciente> byHeight(List<Antropometria> antropometriaList) {
        return toOrder.stream().sorted(Comparator.comparing(paciente -> {
            Antropometria antropometria = antropometriaList.stream()
                    .filter(a -> a.getIdPaciente() == paciente.getId())
                    .findFirst()
                    .orElse(null);
            return antropometria != null ? Float.parseFloat(antropometria.getAltura()): Float.MAX_VALUE;

        })).collect(Collectors.toList());
    }

    public List<Paciente> byWeight(List<Antropometria> antropometriaList) {
        return toOrder.stream().sorted(Comparator.comparing(paciente -> {
            Antropometria antropometria = antropometriaList.stream()
                    .filter(a -> a.getIdPaciente() == paciente.getId())
                    .findFirst()
                    .orElse(null);
            return antropometria != null ? Float.parseFloat(antropometria.getPeso()): Float.MAX_VALUE;

        })).collect(Collectors.toList());
    }

    public List<Paciente> byAge() {
        return toOrder.stream().sorted(Comparator.comparing(Paciente::getIdade))
                .collect(Collectors.toList());
    }
}
