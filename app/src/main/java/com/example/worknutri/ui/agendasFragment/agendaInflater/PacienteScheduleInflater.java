package com.example.worknutri.ui.agendasFragment.agendaInflater;

import android.content.Context;
import android.view.ViewGroup;

import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.NameScheduleInflater;
import com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.pacienteTypes.AgeScheduleInflater;
import com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.pacienteTypes.HeightAntropometryScheduleInflater;
import com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.pacienteTypes.IMCAntropometryScheduleInflater;
import com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.pacienteTypes.WeightAntropometryScheduleInflater;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PacienteFilterPojo;

public class PacienteScheduleInflater {
    private final PacienteFilterPojo pacienteFilterPojo;

    public PacienteScheduleInflater(PacienteFilterPojo pacienteFilterPojo) {
        this.pacienteFilterPojo = pacienteFilterPojo;
    }

    public void inflateSchedule(ViewGroup viewGroup, Context context){
        ScheduleInflater<Paciente> scheduleInflater = new NameScheduleInflater<>(context);
        switch (pacienteFilterPojo.getState().getOrderBy()) {
            case IMC_CATEGORY: {
                scheduleInflater = new IMCAntropometryScheduleInflater(context, pacienteFilterPojo.getAntropometriaList());
                break;
            }
            case HEIGHT: {
                scheduleInflater = new HeightAntropometryScheduleInflater(context,pacienteFilterPojo.getAntropometriaList());
                break;
            }
            case WEIGHT: {
                scheduleInflater = new WeightAntropometryScheduleInflater(context, pacienteFilterPojo.getAntropometriaList());
                break;
            }
            case AGE: scheduleInflater = new AgeScheduleInflater(context);

        }
        scheduleInflater.generateAgenda(viewGroup, pacienteFilterPojo.getPacienteSelected());
    }
}
