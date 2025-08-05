package com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.pacienteTypes;

import android.content.Context;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import java.util.List;


public class WeightScheduleInflater extends AntropometriaScheduleInflater {


    public WeightScheduleInflater(Context context, List<Antropometria> antropometriaList) {
        super(context, antropometriaList);
    }


    @Override
    protected int getElementToCompare(Paciente paciente) {
        return antropometriaList.stream()
                .filter(antropometria -> antropometria.getIdPaciente() == paciente.getId())
                .map(antropometria -> Integer.parseInt(antropometria.getPeso()))
                .findFirst()
                .orElse(0);
    }
}
