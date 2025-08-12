package com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.pacienteTypes;

import android.content.Context;

import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import java.util.List;



public class HeightAntropometryScheduleInflater extends AntropometryScheduleInflater {

    public HeightAntropometryScheduleInflater(Context context, List<Antropometria> antropometrias) {
        super(context,antropometrias);

    }

    @Override
    protected String getCategoryTitle(Paciente firstElementOfCategory) {
        return super.getCategoryTitle(firstElementOfCategory)
                .replace("0,",",")
                .replace("9,",",");
    }

    @Override
    protected int getDecimalValue(int ageCompare) {
        return ageCompare;
    }

    @Override
    protected String getCategoryString(Antropometria antropometria) {
        return antropometria.getAltura();
    }

    @Override
    protected String getMeasureUnit() {
        return "metros";
    }
}
