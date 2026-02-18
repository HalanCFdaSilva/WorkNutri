package com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfScheduleInflater.pacienteTypes;

import android.content.Context;
import com.example.worknutri.calcular.AntropometricCalculator;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;

public class AgeScheduleInflater extends AntropometryScheduleInflater {
    public AgeScheduleInflater(Context context) {
        super(context);
    }

    @Override
    protected int getCategoryValor(Paciente paciente) {
        String nascimento = paciente.getNascimento();
        return AntropometricCalculator.getYearFromDate(nascimento);
    }

    @Override
    protected String getCategoryTitle(Paciente firstElementOfCategory) {
        String categoryTitle = super.getCategoryTitle(firstElementOfCategory);
        return categoryTitle.replace(",00","").replace(",99","");
    }

    @Override
    protected String getMeasureUnit() {
        return "anos";
    }
}
