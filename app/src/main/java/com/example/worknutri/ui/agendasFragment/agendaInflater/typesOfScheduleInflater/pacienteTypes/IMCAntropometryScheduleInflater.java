package com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.pacienteTypes;

import android.content.Context;

import com.example.worknutri.calcular.ClassificacaoImc;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IMCAntropometryScheduleInflater extends AntropometryScheduleInflater {
    public IMCAntropometryScheduleInflater(Context context, List<Antropometria> antropometriaList) {
        super(context, antropometriaList);
    }

    @Override
    protected List<Paciente> filterElements(List<Paciente> elementsToAdd) {
        ClassificacaoImc classificacaoImcToCompare = getClassificacaoImc(elementsToAdd.get(0));
        return elementsToAdd.stream()
                .filter(paciente -> getClassificacaoImc(paciente) == classificacaoImcToCompare)
                .collect(Collectors.toList());
    }

    @Override
    protected String getCategoryTitle(Paciente firstElementOfCategory) {
        return getClassificacaoImc(firstElementOfCategory).toString();
    }

    private ClassificacaoImc getClassificacaoImc(Paciente paciente) {
        Stream<Antropometria> antropometriaStream = getAntropometriaStream(paciente);
        double imc = antropometriaStream
                .mapToDouble(antropometria -> Double.parseDouble(antropometria.getImc()))
                .findFirst()
                .orElse(0.0);

        return ClassificacaoImc.tipoImc(imc);
    }
}
