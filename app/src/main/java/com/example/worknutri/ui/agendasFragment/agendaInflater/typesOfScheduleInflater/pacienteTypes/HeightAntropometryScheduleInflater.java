package com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.pacienteTypes;

import android.content.Context;

import com.example.worknutri.sqlLite.domain.paciente.Antropometria;

import java.util.List;



public class HeightAntropometryScheduleInflater extends AntropometryScheduleInflater {

    public HeightAntropometryScheduleInflater(Context context, List<Antropometria> antropometrias) {
        super(context,antropometrias);

    }


    @Override
    protected String getCategoryString(Antropometria antropometria) {
        return antropometria.getAltura();
    }

}
