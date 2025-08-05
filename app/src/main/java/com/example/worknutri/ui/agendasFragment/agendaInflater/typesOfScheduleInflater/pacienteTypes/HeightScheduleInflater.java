package com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.pacienteTypes;

import android.content.Context;

import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import java.util.List;



public class HeightScheduleInflater extends AntropometriaScheduleInflater {

    public HeightScheduleInflater(Context context, List<Antropometria> antropometrias) {
        super(context,antropometrias);

    }


    @Override
    protected int getElementToCompare(Paciente paciente) {
        return antropometriaList.stream()
                .filter(antropometria -> antropometria.getIdPaciente() == paciente.getId())
                .map(antropometria -> Integer.parseInt(antropometria.getAltura()))
                .findFirst()
                .orElse(0);
    }

}
