package com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.pacienteTypes;

import android.content.Context;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import java.util.List;


public class WeightAntropometryScheduleInflater extends AntropometryScheduleInflater {


    public WeightAntropometryScheduleInflater(Context context, List<Antropometria> antropometriaList) {
        super(context, antropometriaList);
    }


    @Override
    protected String getCategoryString(Antropometria antropometria) {
        return antropometria.getPeso();
    }

    @Override
    protected String getMeasureUnit() {
        return "Kg";
    }
}
