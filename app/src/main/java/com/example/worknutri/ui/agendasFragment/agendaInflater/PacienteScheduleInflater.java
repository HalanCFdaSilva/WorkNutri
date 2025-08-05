package com.example.worknutri.ui.agendasFragment.agendaInflater;

import android.content.Context;
import android.view.ViewGroup;

import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.NameScheduleInflater;
import com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.pacienteTypes.HeightScheduleInflater;
import com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.pacienteTypes.WeightScheduleInflater;
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
                break;
            }
            case HEIGHT: {
                scheduleInflater = new HeightScheduleInflater(context,pacienteFilterPojo.getAntropometriaList());
                break;
            }
            case WEIGHT: {
                scheduleInflater = new WeightScheduleInflater(context, pacienteFilterPojo.getAntropometriaList());
                break;
            }
            case AGE: {
            }
        }
        scheduleInflater.generateAgenda(viewGroup, pacienteFilterPojo.getPacienteSelected());
    }
}
